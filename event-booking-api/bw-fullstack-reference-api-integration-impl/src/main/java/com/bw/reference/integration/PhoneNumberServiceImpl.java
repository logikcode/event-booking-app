package com.bw.reference.integration;

import com.bw.reference.service.PhoneNumberService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private Environment env;

    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public String formatPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return null;
        }
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+" + phoneNumber.trim();
        }
        return phoneNumber.trim();
    }

    @Override
    public boolean isValid(String value, String region) {

        if (value == null) {
            return true;
        }

        if (StringUtils.isBlank(region)) {
            region = env.getProperty("default.phonenumber.region");
        }

        Phonenumber.PhoneNumber swissNumberProto;
        try {
            swissNumberProto = phoneNumberUtil.parse(value.trim(), region);
        } catch (NumberParseException e) {
            return false;
        }

        return phoneNumberUtil.isValidNumber(swissNumberProto);
    }

    @Override
    public boolean isValid(String value) {
        return isValid(value, env.getProperty("default.phonenumber.region"));
    }
}
