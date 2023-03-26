package com.bw.reference.validator;
/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

import com.bw.reference.constraint.AccountValidator;
import com.bw.reference.dao.account.PortalAccountRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

@Named
public class AccountValidatorConstraintImpl implements AccountValidator.AccountValidatorConstraint {

    @Inject
    private PortalAccountRepository portalAccountRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;

        }
        return portalAccountRepository.findActiveByCode(value.trim()).isPresent();
    }
}
