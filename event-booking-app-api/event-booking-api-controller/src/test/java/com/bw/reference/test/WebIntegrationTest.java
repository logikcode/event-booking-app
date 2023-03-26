package com.bw.reference.test;

import com.bw.commons.authclient.dto.ApiResourcePortalUser;
import com.bw.reference.converter.DateConverter;
import com.bw.reference.converter.OffsetTimeConverter;
import com.bw.reference.interceptors.AccessConstraintHandlerInterceptor;
import com.bw.reference.util.PredicateExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@AutoConfigureMockMvc
@Import({WebIntegrationTest.$Configuration.class})
@TestProfile
public class WebIntegrationTest extends IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Inject
    protected PredicateExtractor predicateExtractor;

    @Inject
    protected ObjectMapper objectMapper;

    @Configuration
    @ComponentScan({
            "com.bw.util",
            "com.bw.security",
            "com.bw.excel",
            "com.bw.reference.dao",
            "com.bw.reference.controller",
            "com.bw.reference.controlleradvice",
            "com.bw.reference.handler",
            "com.bw.reference.response.handler"
    })
    public static class $Configuration implements WebMvcConfigurer {
        @Inject
        private ApplicationContext applicationContext;

        final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new HandlerInterceptorAdapter() {
                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                    logger.info(Thread.currentThread().getName());
                    return super.preHandle(request, response, handler);
                }
            });
            registry.addInterceptor(applicationContext.getAutowireCapableBeanFactory().createBean(AccessConstraintHandlerInterceptor.class));
        }

        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new DateConverter("yyyy-MM-dd"));
            registry.addConverter(new OffsetTimeConverter());
        }

        @Bean
        public ApiResourcePortalUser apiResourcePortalUser() {
            return Mockito.mock(ApiResourcePortalUser.class);
        }

//        @Bean
//        public FacebookConnectionFactory facebookConnectionFactory() {
//            return Mockito.mock(FacebookConnectionFactory.class);
//        }
//
//        @Bean
//        public FacebookTemplate facebookTemplate() {
//            return Mockito.mock(FacebookTemplate.class);
//        }

        class LocalDateAdapter implements JsonSerializer<LocalDate> {

            public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
            }
        }
    }
}
