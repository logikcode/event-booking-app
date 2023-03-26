package com.bw.reference;

import com.bw.commons.springstarter.conf.ServiceLayerConfiguration;
import com.bw.reference.configuration.CustomConfiguration;
import com.bw.reference.configuration.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {
        ValidationAutoConfiguration.class
})
@EnableScheduling
@Import({WebConfiguration.class, CustomConfiguration.class, ServiceLayerConfiguration.class})
public class BwFullstackReferenceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BwFullstackReferenceApiApplication.class, args);
    }

}
