package com.bw.reference.configuration;

import com.bw.commons.authclient.BwAuthApiClient;
import com.bw.commons.crypto.cipher.DefaultHashGenerator;
import com.bw.commons.crypto.cipher.HashGenerator;
import com.bw.commons.springstarter.auth.BwAuthAdmin;
import com.bw.commons.springstarter.auth.BwAuthApiClientImpl;
import com.bw.commons.starter.DataSourceUtil;
import com.bw.commons.starter.SettingService;
import com.bw.commons.starter.TimeUtil;
import com.bw.reference.advice.AuditTrailAdvice;
import com.bw.reference.constant.TimeFormatConstants;
import com.bw.reference.integration.PhoneNumberServiceImpl;
import com.bw.reference.service.PhoneNumberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

//import com.bw.reference.interceptors.RequestPrincipalHandlerInterceptor;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Configuration
@EnableJpaRepositories({"com.bw.reference.dao"})
@ComponentScan("com.bw.util")
@EnableAsync
@EnableJpaAuditing
@Slf4j
public class CustomConfiguration {

    @Value("${BW_AUTH_API_BASE_URL:}")
    private String bwAuthApiBaseUrl;

    private Environment env;

    @Bean
    @Profile("!test")
    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat(TimeFormatConstants.DEFAULT_DATE_TIME_FORMAT)
                .create();
    }

    @Bean
    @Profile("!test")
    public DataSourceUtil dataSourceUtil() {
        return new DataSourceUtil(gson());
    }

    @Bean
    public HashGenerator hashGenerator() {
        return new DefaultHashGenerator();
    }

    @Bean
    public PhoneNumberService phoneNumberService() {
        return new PhoneNumberServiceImpl();
    }


    @Bean
    public OkHttpClient httpClient() {
        return new OkHttpClient();
    }

    @Bean
    public TimeUtil timeUtil() {
        return new TimeUtil();
    }


    @Bean
    public AuditTrailAdvice auditTrailAdvice() {
        return new AuditTrailAdvice();
    }

//    @Bean
//    public FactoryBean<ApiResourcePortalUser> apiResourcePortalUser() {
//        return RequestPrincipalHandlerInterceptor.apiResourcePortalUser();
//    }

    @Bean
    public BwAuthApiClient bwAuthApiClient(@Lazy BwAuthAdmin bwAuthAdmin, Gson gson) {
        return new BwAuthApiClientImpl(bwAuthApiBaseUrl, new Gson()) {
            @Override
            public String getAdminToken() {
                return bwAuthAdmin.getToken();
            }
        };
    }

    @Bean
    public BwAuthAdmin bwAuthAdmin(BwAuthApiClient bwAuthApiClient, SettingService settingService) {
        return new BwAuthAdmin(bwAuthApiClient, settingService);
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger getLogger(InjectionPoint injectionPoint) {
        Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(classOnWired);
    }

}

