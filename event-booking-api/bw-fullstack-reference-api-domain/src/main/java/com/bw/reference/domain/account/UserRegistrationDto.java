package com.bw.reference.domain.account;

import com.bw.reference.constraint.ExistsColumnValue;
import com.bw.reference.domain.enumeration.UserTypeConstant;
import com.bw.reference.entity.Country;
import com.bw.enums.GenderConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRegistrationDto {
    @ExistsColumnValue(value = Country.class)
    private Long country;

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String mobileNumber;

    @NotNull
    private UserTypeConstant userType;

    private GenderConstant gender;

    @NotBlank
    private String password;

    @NotBlank
    private String otpPin;

    private Long workspaceUserId;

}
