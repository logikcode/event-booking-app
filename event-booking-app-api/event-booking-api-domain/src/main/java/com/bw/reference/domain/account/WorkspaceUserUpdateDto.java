package com.bw.reference.domain.account;

import com.bw.enums.GenderConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Setter
@Getter
public class WorkspaceUserUpdateDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private GenderConstant gender;
    @Past
    private LocalDate dateOfBirth;
    private Boolean isInstructor;

}
