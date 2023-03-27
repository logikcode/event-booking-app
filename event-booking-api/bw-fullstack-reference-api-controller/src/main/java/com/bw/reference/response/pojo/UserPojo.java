package com.bw.reference.response.pojo;


import com.bw.commons.authclient.dto.ApiResourcePortalUser;
import com.bw.commons.starter.TimeUtil;
import com.bw.enums.GenderConstant;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.domain.NameAndCodeDto;
import com.bw.reference.domain.account.AccountMembershipPojo;
import com.bw.reference.entity.WorkspaceUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@Data
@NoArgsConstructor
public class UserPojo {

    private static TimeUtil timeUtil = new TimeUtil();

    private NameAndCodeDto title;
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String username;
    private String userId;
    private GenericStatusConstant status;
    private Date dateCreated;
    private Date lastUpdated;
    private String accountCode;
    private Boolean emailVerified;
    private Boolean phoneNumberVerified;
    private String createdBy;
    private String lastUpdatedBy;

    private Long displayPictureId;

    private boolean requiresPasswordUpdate;
    private String authToken;
    private Long id;
    private GenderConstant gender;

    private List<AccountMembershipPojo> accounts;

    static {
        timeUtil.init();
    }

    public UserPojo(WorkspaceUser portalUser, ApiResourcePortalUser apiResourcePortalUser) {
        this(portalUser);
        requiresPasswordUpdate = apiResourcePortalUser.isPasswordResetRequired();
        accountCode = apiResourcePortalUser.getAccountCode();
    }

    public UserPojo(WorkspaceUser portalUser) {
        firstName = portalUser.getFirstName();
        lastName = portalUser.getLastName();
        status = portalUser.getStatus();
        email = portalUser.getEmail();
        username = portalUser.getUsername();
        userId = portalUser.getUserId();
        mobileNumber = portalUser.getPhoneNumber();
        dateCreated = portalUser.getDateCreated();
        lastUpdated = portalUser.getLastUpdated();
        //emailVerified = portalUser.getEmailVerified();
        //phoneNumberVerified = portalUser.getPhoneNumberVerified();
        gender = portalUser.getGender();
        id = portalUser.getId();
    }
}
