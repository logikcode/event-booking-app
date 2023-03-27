package com.bw.reference.integration.sms;

import java.util.List;

public interface SMSNotificationService {
    void sendSms(List<String> to, String from, String message);
}
