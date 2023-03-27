package com.bw.reference.util;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.EventApplier;
import com.bw.reference.entity.WorkspaceUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
public class ApplierMapper {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private  String phoneNumber;
    private String middleName;
    private String upload;
    private Date createdDate;
    private Date updatedDate;

    public EventApplier mapToApplier(EventApplierDto applierInfo){


        EventApplier applier = new EventApplier();
        firstName = applierInfo.getFirstName();
        lastName = applierInfo.getLastName();
        middleName = applierInfo.getMiddleName();
        phoneNumber= applierInfo.getPhoneNumber();
        emailAddress = applierInfo.getEmailAddress();

        createdDate = new Date();
        updatedDate =new Date() ;

        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmailAddress(emailAddress);
        setMiddleName(middleName);
        setUpload(upload);
        setCreatedDate(createdDate);
        setUpdatedDate(updatedDate);

        applier.setWorkspaceUser(createWorkSpaceUser());

        return applier;
    }


    private WorkspaceUser createWorkSpaceUser(){
        WorkspaceUser user = new WorkspaceUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(GenericStatusConstant.ACTIVE);
        return user;

    }


}
