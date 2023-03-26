package com.bw.reference.domain.account;

import com.bw.reference.constraint.ExistsColumnValue;
import com.bw.reference.constraint.ValidPhoneNumber;
import com.bw.reference.domain.enumeration.UserTypeConstant;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.PortalAccount;
import com.bw.enums.GenderConstant;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.*;
import java.util.Map;
import java.util.Set;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public class NewUserDto {

    @NotBlank
    @ExistsColumnValue(value = PortalAccount.class, columnName = "code")
    private String accountCode;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @ValidPhoneNumber
    private String PhoneNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private GenderConstant gender;

    private UserTypeConstant userType;

    @ExistsColumnValue(value = Country.class)
    private Long country;

    // TODO add state code

    private boolean fromSocialProvider;

    private String password;

    private String defaultPassword;

    private boolean adminCreated;



    public String getAccountCode() {
        return accountCode;
    }

    public com.bw.reference.domain.account.NewUserDto setAccountCode(String accountCode) {
        this.accountCode = accountCode;
        return this;
    }

    @NotNull
    @NotEmpty
    @Size(max = 1)
    private Map<@NotBlank String, @NotEmpty Set<@NotBlank String>> roles;

    public String getUsername() {
        return username;
    }

    public com.bw.reference.domain.account.NewUserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public com.bw.reference.domain.account.NewUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public com.bw.reference.domain.account.NewUserDto setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public com.bw.reference.domain.account.NewUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public com.bw.reference.domain.account.NewUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public GenderConstant getGender() {
        return gender;
    }

    public com.bw.reference.domain.account.NewUserDto setGender(GenderConstant genderConstant) {
        this.gender = genderConstant;
        return this;
    }

    public UserTypeConstant getUserType() {
        return userType;
    }

    public com.bw.reference.domain.account.NewUserDto setUserType(UserTypeConstant userTypeConstant) {
        this.userType = userTypeConstant;
        return this;
    }

    public Long getCountry() {
        return country;
    }

    public com.bw.reference.domain.account.NewUserDto setCountry(Long country) {
        this.country = country;
        return this;
    }

    public Map<String, Set<String>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Set<String>> roles) {
        this.roles = roles;
    }

    public boolean isFromSocialProvider() {
        return fromSocialProvider;
    }

    public com.bw.reference.domain.account.NewUserDto setFromSocialProvider(boolean fromSocialProvider) {
        this.fromSocialProvider = fromSocialProvider;
        return this;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public boolean isAdminCreated() {
        return adminCreated;
    }

    public void setAdminCreated(boolean adminCreated) {
        this.adminCreated = adminCreated;
    }







    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("email", email)
                .append("PhoneNumber", PhoneNumber)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .toString();
    }
}
