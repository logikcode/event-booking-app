package com.bw.reference.service;

import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.CityRepository;
import com.bw.reference.domain.address.AddressDto;
import com.bw.reference.domain.address.GpsCoordinateDto;
import com.bw.reference.entity.Address;
import com.bw.reference.entity.City;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.GpsCoordinate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@RequiredArgsConstructor
@Named
public class AddressServiceImpl implements AddressService {

    private final AppRepository appRepository;
    private final CityRepository cityRepository;


    @Transactional
    @Override
    public Address createAddress(AddressDto dto) {
        Address address = new Address();
        address.setPostalCode(StringUtils.normalizeSpace(dto.getZipCode()));
        address.setStreet(StringUtils.normalizeSpace(dto.getStreetAddress()));
        address.setHouseNumber(StringUtils.normalizeSpace(dto.getHouseNumber()));
        address.setDateCreated(new Date());
        City city = cityRepository.findActiveByCode(dto.getCity())
                .orElseThrow(() -> new IllegalArgumentException(String.format("City with code %s not found", dto.getCity())));
        address.setCity(city);
        GpsCoordinateDto gpsCoordinate = dto.getGpsCoordinate();
        if (gpsCoordinate != null) {
            address.setGpsCoordinate(addGps(gpsCoordinate));
        }
        appRepository.persist(address);
        return address;
    }

    private GpsCoordinate addGps(GpsCoordinateDto dto) {
        GpsCoordinate gpsCoordinate = new GpsCoordinate();
        gpsCoordinate.setLongitude(dto.getLongitude());
        gpsCoordinate.setLatitude(dto.getLatitude());
        gpsCoordinate.setDateCreated(new Date());
        appRepository.persist(gpsCoordinate);
        return gpsCoordinate;
    }
}