package com.bw.reference.startup;

import com.bw.commons.starter.SettingService;
import com.bw.reference.adapter.CountryTypeAdapter;
import com.bw.reference.adapter.HibernateProxyTypeAdapter;
import com.bw.reference.dao.AddressRepository;
import com.bw.reference.dao.CountryRepository;
import com.bw.reference.dao.StateRepository;
import com.bw.reference.dao.account.WorkspaceUserRepository;
import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.entity.*;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.service.UserRegistrationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Component
@Profile("!test")
public class MasterRecordsLoader {
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final TransactionTemplate transactionTemplate;
    private final Gson gson;
    private final AutowireCapableBeanFactory beanFactory;
    private final SettingService settingService;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final UserRegistrationService userRegistrationService;
    private final EventRepository eventRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AddressRepository addressRepository;
    public MasterRecordsLoader(CountryRepository countryRepository, StateRepository stateRepository,
                               TransactionTemplate transactionTemplate,
                               Gson gson, AutowireCapableBeanFactory beanFactory, SettingService settingService, WorkspaceUserRepository workspaceUserRepository,
                               UserRegistrationService userRegistrationService, EventRepository eventRepository, AddressRepository addressRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.transactionTemplate = transactionTemplate;
        this.gson = gson;
        this.beanFactory = beanFactory;
        this.settingService = settingService;
        this.workspaceUserRepository = workspaceUserRepository;
        this.userRegistrationService = userRegistrationService;
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        transactionTemplate.execute(tx -> {
            try {
                loadData();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
            return null;
        });
    }

    private void loadData() throws IOException {
        if (countryRepository.count() < 2) {
            loadCountries();
        }
        logger.info("countries: " + countryRepository.count());

        if (stateRepository.count() == 0) {
            loadStates();
        }
        logger.info("states: " + stateRepository.count());
        loadDefaultAccount();
        loadAddressAndEvents();
    }

    private void loadCountries() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/master_records/country.json"))) {
            Country[] dtoList = gson.fromJson(gson.newJsonReader(reader), Country[].class);
            List<Country> countries = new ArrayList<>();
            for (Country country : dtoList) {
                if (!countryRepository.findByName(country.getName()).isPresent()) {
                    country.setId(null);
                    country.setStatus(GenericStatusConstant.ACTIVE);
                    countries.add(country);
                }
            }
            countryRepository.saveAll(countries);
        }
    }


    private void loadStates() throws IOException {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        b.registerTypeAdapterFactory(new TypeAdapterFactory() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                return (Country.class.isAssignableFrom(type.getRawType())
                        ? (TypeAdapter<T>) beanFactory.createBean(CountryTypeAdapter.class)
                        : null);
            }
        });
        Gson gson = b.create();

        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/master_records/state.json"))) {
            State[] dtoList = gson.fromJson(gson.newJsonReader(reader), State[].class);
            List<State> newStates = new ArrayList<>();
            for (State state : dtoList) {
                if (!stateRepository.findByCode(state.getCode()).isPresent()) {
                    state.setId(null);
                    state.setStatus(GenericStatusConstant.ACTIVE);
                    state.setDisplayCode(state.getCode());
                    newStates.add(state);
                }
            }
            stateRepository.saveAll(newStates);
        }
    }


    private void loadDefaultAccount() {
        String username = settingService.getString("DEF_ADMIN_USERNAME", "admin1@bwreference.com");
        workspaceUserRepository.findByEmailOrUsername(username, username)
                .orElseGet(() -> {
                    try {
                        logger.info("creating admin --->{}", username);
                        WorkspaceUser portalUser = userRegistrationService.createDefaultUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    private void loadAddressAndEvents() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/master_records/Event.json")));
             InputStreamReader addressReader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/master_records/address.json")))) {

            Event[] dtoList = gson.fromJson(gson.newJsonReader(reader), Event[].class);
            Address[] addressDtoList = gson.fromJson(gson.newJsonReader(addressReader), Address[].class);

            List<Event> eventsList = new ArrayList<>();
            List<Address> addressList = new ArrayList<>();

            for (Event event : dtoList) {
                Event persistedEvent = eventRepository.findEventByNameIgnoreCase(event.getName());
                if (persistedEvent == null) {
                    event.setId(null);
                    event.setStatus(GenericStatusConstant.ACTIVE);
                    event.setStartDate(new Date());
                    event.setUpdatedAt(new Date());
                    event.setCreatedAt(new Date());
                    event.setEndDate(addHoursToJavaUtilDate(new Date(), new Random().nextInt(6)));

                    for(Address addressSingle: addressDtoList){
                        if(!addressRepository.findAddressByStreet(addressSingle.getStreet()).isPresent()){
                            addressSingle.setId(null);
                            addressSingle.setDateCreated(new Date());
                            addressList.add(addressSingle);
                            event.setAddress(addressSingle);
                        }
                    }
                    eventsList.add(event);
                }
            }


            addressRepository.saveAll(addressList);
            eventRepository.saveAll(eventsList);

        }
    }
}
