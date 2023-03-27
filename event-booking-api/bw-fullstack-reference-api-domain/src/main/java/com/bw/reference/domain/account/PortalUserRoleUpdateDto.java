package com.bw.reference.domain.account;

import com.bw.reference.constraint.ExistsColumnValue;
import com.bw.reference.entity.PortalAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class PortalUserRoleUpdateDto {
    @NotBlank
    @ExistsColumnValue(value = PortalAccount.class, columnName = "code")
    private String accountCode;
    @NotNull
    @Size(min = 1)
    private Set<String> roles;
}
