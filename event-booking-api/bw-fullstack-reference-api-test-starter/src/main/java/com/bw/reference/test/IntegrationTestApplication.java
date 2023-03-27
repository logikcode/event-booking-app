package com.bw.reference.test;


import com.bw.commons.springstarter.conf.ServiceLayerConfiguration;
import com.bw.commons.starter.DataSourceUtil;
import com.bw.reference.configuration.CustomConfiguration;
import com.bw.reference.configuration.WebConfiguration;
import com.bw.reference.constant.TimeFormatConstants;
import com.bw.reference.integration.TemplateEngine;
import com.bw.reference.integration.sms.SMSNotificationService;
import com.bw.reference.principal.RequestPrincipal;
import com.github.heywhy.springentityfactory.FactoryConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;


@SpringBootApplication(exclude = {
        ValidationAutoConfiguration.class
})
@Import({FactoryConfiguration.class,ServiceLayerConfiguration.class, CustomConfiguration.class})
//@EntityScan("com.bw.reference")
@ComponentScan("com.bw.reference")
public class IntegrationTestApplication {

    @Bean
    @TestProfile
    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat(TimeFormatConstants.DEFAULT_DATE_TIME_FORMAT)
                .create();
    }

    @Bean
    @TestProfile
    public DataSourceUtil dataSourceUtil() {
        return new DataSourceUtil(gson());
    }

    @Bean
    @TestProfile
    public RequestPrincipal requestPrincipal() {
        return Mockito.mock(RequestPrincipal.class);
    }

    @Bean
    public TemplateEngine templateEngine() {
        return Mockito.mock(TemplateEngine.class);
    }


    @Bean
    public SMSNotificationService notificationSenderService() {
        return Mockito.mock(SMSNotificationService.class);
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return Mockito.mock(RealmResource.class);
    }
    @Bean
    public Keycloak keycloak() {
        return Mockito.mock(Keycloak.class);
    }
    @Bean
    public ClientResource clientResource(RealmResource realmResource) {
        return Mockito.mock(ClientResource.class);
    }


}

class MockInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        return new Response.Builder()
                .code(200)
                .message("")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), "{}".getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }
}
