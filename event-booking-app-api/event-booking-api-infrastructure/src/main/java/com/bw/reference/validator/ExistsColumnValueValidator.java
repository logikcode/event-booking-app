/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.reference.validator;


import com.bw.reference.constraint.ExistsColumnValue;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.service.PhoneNumberService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExistsColumnValueValidator implements ExistsColumnValue.Validator {

    @Inject
    private PhoneNumberService phoneNumberService;
    @Inject
    private AppRepository appRepository;
    private Class<?> entityType;
    private String columnName;

    @Override
    public void initialize(ExistsColumnValue constraintAnnotation) {
        this.entityType = constraintAnnotation.value();
        this.columnName = constraintAnnotation.columnName();
    }


    @Transactional
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof String && phoneNumberService.isValid((String) value)) {
            return appRepository.findFirstByField(entityType, columnName, phoneNumberService.formatPhoneNumber((String) value)).isPresent();
        }
        return appRepository.findFirstByField(entityType, columnName, value).isPresent();
    }
}
