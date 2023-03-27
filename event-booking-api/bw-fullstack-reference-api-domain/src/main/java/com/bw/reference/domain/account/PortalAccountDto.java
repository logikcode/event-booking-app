package com.bw.reference.domain.account;

import com.bw.reference.enums.PortalAccountTypeConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public class PortalAccountDto {

    @NotBlank
    private String name;

    @NotNull
    private PortalAccountTypeConstant type;

    private String email;

    private String phoneNumber;

    private String altPhoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PortalAccountTypeConstant getType() {
        return type;
    }

    public void setType(PortalAccountTypeConstant type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public com.bw.reference.domain.account.PortalAccountDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAltPhoneNumber() {
        return altPhoneNumber;
    }

    public com.bw.reference.domain.account.PortalAccountDto setAltPhoneNumber(String altPhoneNumber) {
        this.altPhoneNumber = altPhoneNumber;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
