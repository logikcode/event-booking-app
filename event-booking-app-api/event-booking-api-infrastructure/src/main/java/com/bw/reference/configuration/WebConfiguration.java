/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.reference.configuration;

import com.bw.entity.BwFile;
import com.bw.reference.constant.TimeFormatConstants;
import com.bw.reference.converter.DateConverter;
import com.bw.reference.converter.LocalDateConverter;
import com.bw.reference.interceptors.AccessConstraintHandlerInterceptor;
import com.bw.reference.interceptors.RemoteAddressConstraintHandlerInterceptor;
import com.bw.reference.resolvers.WorkspaceUserArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.validation.ConstraintValidatorFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;

//import com.bw.reference.interceptors.RequestPrincipalHandlerInterceptor;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Configuration
@ComponentScan({
        "com.bw.security",
        "com.bw.service",
        "com.bw.integration",
        "com.bw.reference.etc",
        "com.bw.reference.dao",
        "com.bw.reference.service",
        "com.bw.reference.integration"
})
public class WebConfiguration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    private final ConstraintValidatorFactory constraintValidatorFactory;
    private final Environment environment;

    public WebConfiguration(ApplicationContext applicationContext, ConstraintValidatorFactory constraintValidatorFactory, Environment environment) {
        this.applicationContext = applicationContext;
        this.constraintValidatorFactory = constraintValidatorFactory;
        this.environment = environment;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RequestPrincipalHandlerInterceptor(applicationContext));
        registry.addInterceptor(new AccessConstraintHandlerInterceptor(applicationContext));
        registry.addInterceptor(new RemoteAddressConstraintHandlerInterceptor(applicationContext));
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new WorkspaceUserArgumentResolver(applicationContext));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter(TimeFormatConstants.DEFAULT_DATE_FORMAT));
        registry.addConverter(new DateConverter(TimeFormatConstants.DEFAULT_DATE_FORMAT));
    }

    @Bean
    @Override
    public LocalValidatorFactoryBean getValidator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean
                .setConstraintValidatorFactory(constraintValidatorFactory);
        return localValidatorFactoryBean;
    }


//    @Bean
//    public FactoryBean<RequestPrincipal> requestPrincipal() {
//        return RequestPrincipalHandlerInterceptor.requestPrincipal();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new StdSerializer<JavassistLazyInitializer>(JavassistLazyInitializer.class) {

            @Override
            public void serialize(JavassistLazyInitializer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeNull();
            }
        });
        simpleModule.addSerializer(new StdSerializer<HibernateProxy>(HibernateProxy.class) {

            @Override
            public void serialize(HibernateProxy value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeObject(Collections.singletonMap("id", value.getHibernateLazyInitializer().getIdentifier()));
            }
        });
        simpleModule.addSerializer(new StdSerializer<BwFile>(BwFile.class) {
            @Override
            public void serialize(BwFile value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (value == null) {
                    gen.writeNull();
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                for (Field field : BwFile.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (byte[].class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    try {
                        Object fieldValue = field.get(value);
                        map.put(field.getName(), fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                gen.writeObject(map);
            }
        });
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(TimeFormatConstants.DEFAULT_DATE_TIME_FORMAT));
        objectMapper.registerModule(simpleModule);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());

        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
