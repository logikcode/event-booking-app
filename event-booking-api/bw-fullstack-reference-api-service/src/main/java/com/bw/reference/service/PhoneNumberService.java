package com.bw.reference.service;

public interface PhoneNumberService {

    String formatPhoneNumber(String phoneNumber);

    boolean isValid(String value);

    boolean isValid(String value, String region);
}
