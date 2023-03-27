package com.bw.reference.service;

import com.bw.entity.BwFile;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.constant.ActivityLogActionConstant;
import com.bw.reference.dao.AddressRepository;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.eventbooking.BookedEventRepository;
import com.bw.reference.dao.eventbooking.EventApplierRepository;
import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.*;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.util.ApplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.awt.print.Book;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EventApplierServiceImpl implements  EventApplierService{
    private static final String ALREADY_BOOKED = "Event Already Booked" ;
    private static final String SUCCESS = "You have successfully booked this event";
    private static final String FAILED = "Event could not be created";
    private static final String UPDATED = "Successfully updated";
    private final EventApplierRepository applierRepository;
    private  final BookedEventRepository bookedEventRepository;
    private final EventRepository eventRepository;
    private final Provider<RequestPrincipal> requestPrincipalProvider;
    private final AppRepository appRepository;
    private final FileService fileService;
    private final AuditTrailService auditTrailService;
    private final AddressRepository addressRepository;
    @Override
    public EventApplier bookEvent(EventApplierDto applierInfo) {

      WorkspaceUser workspaceUser = requestPrincipalProvider.get().getWorkspaceUser();

      Event event = eventRepository.findById(applierInfo.getEventId()).orElseThrow(() -> new RuntimeException("Error finding events."));
      ApplicantDocument applicantDocuments = new ApplicantDocument();
      EventApplier eventApplier = new EventApplier();
      eventApplier.setFirstName(applierInfo.getFirstName());
      eventApplier.setMiddleName(applierInfo.getMiddleName());
      eventApplier.setLastName(applierInfo.getLastName());
      eventApplier.setEmailAddress(applierInfo.getEmailAddress());
      eventApplier.setPhoneNumber(applierInfo.getPhoneNumber());
      eventApplier.setStatus(GenericStatusConstant.ACTIVE);
      Date now = new Date();
      eventApplier.setCreatedAt(now);
      eventApplier.setWorkspaceUser(workspaceUser);
      eventApplier.setUpdatedAt(now);

      EventApplier persistedApplicant = appRepository.persist(eventApplier);

      applierInfo.getUploadedFilesList().forEach(v -> {
          try {
              BwFile uploadedFile = fileService.uploadFile(v.getFileName(), v.getFileType(), v.getFileData64(), v.getFileSize());
              applicantDocuments.setEventApplier(persistedApplicant);
              applicantDocuments.setCreatedAt(now);
              applicantDocuments.setStatus(GenericStatusConstant.ACTIVE);
              applicantDocuments.setUpdatedAt(now);
              applicantDocuments.setBwFile(uploadedFile);
              appRepository.persist(applicantDocuments);

          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      });

      BookedEvent bookedEvent = new BookedEvent();
      bookedEvent.setEventApplier(persistedApplicant);
      bookedEvent.setCreatedAt(now);
      bookedEvent.setUpdatedAt(now);
      bookedEvent.setEvent(event);
      bookedEvent.setStatus(GenericStatusConstant.ACTIVE);

      BookedEvent bookedEvent1 = appRepository.persist(bookedEvent);

      auditTrailService.createActivityLog(ActivityLogActionConstant.CREATE_EVENTS, requestPrincipalProvider.get().getIpAddress(),
              BookedEvent.class.getSimpleName(), String.valueOf(bookedEvent1.getId()),
              event.getName() + " was booked by " + persistedApplicant.getFirstName(), requestPrincipalProvider.get().getWorkspaceUser());

      return persistedApplicant;
    }




    private void handleSetEvent(Event event, BookedEvent booking, EventApplier eventApplier) {
        booking.setEvent(event);
        booking.setEventApplier(eventApplier);
        booking.setStatus(GenericStatusConstant.ACTIVE);
        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());

        applierRepository.save(eventApplier);
        bookedEventRepository.save(booking);
    }


}
