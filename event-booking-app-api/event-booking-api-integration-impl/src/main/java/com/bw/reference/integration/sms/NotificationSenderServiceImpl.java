package com.bw.reference.integration.sms;


/*import com.bw.reference.integration.vanso.SMSSenderImpl;
import com.bw.dentaldoor.integration.vanso.VansoSMSProvider;
import com.bw.integration.notification.NotificationSenderService;
import com.bw.integration.notification.SmsNotificationBuilder;*/

import com.bw.utils.sms.SMSSender;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

public class NotificationSenderServiceImpl /*implements NotificationSenderService*/ {

    private final Environment environment;

    private SMSSender smsSender;

    @Inject
    public NotificationSenderServiceImpl(Environment environment) {
        this.environment = environment;
    }

   /*

    @Override
    public SmsNotificationBuilder sms(String s) {
        return new SmsNotificationBuilder() {

            String text = s;
            String[] recipients;
            String from;

            @Override
            public SmsNotificationBuilder text(String s) {
                text = s;
                return this;
            }

            @Override
            public SmsNotificationBuilder to(String... phoneNumbers) {
                recipients = new String[phoneNumbers.length];
                try {
                    for (int i = 0; i < recipients.length; i++) {
                        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
                        Phonenumber.PhoneNumber number;
                        number = numberUtil.parse(phoneNumbers[i], "NG");
                        recipients[i] = numberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
                    }
                } catch (NumberParseException e) {
                    e.printStackTrace();
                }
                return this;
            }

            @Override
            public SmsNotificationBuilder from(String s) {
                from = s;
                return this;
            }

            @Override
            public void send() {
                if (smsSender == null) {
                    smsSender = getSmsSender();
                }
                smsSender.sendSms(recipients, text, from);
            }
        };
    }*/

}

