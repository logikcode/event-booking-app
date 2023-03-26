package com.bw.reference.validator;

import com.bw.reference.constraint.ValidPhoneNumber;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 */
@RequiredArgsConstructor
@Named
public class ValidPhoneNumberValidator implements ValidPhoneNumber.Validator {

    //private final PhoneNumberService phoneNumberService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return StringUtils.isBlank(value);//phoneNumberService.isValid(value);
    }

}
