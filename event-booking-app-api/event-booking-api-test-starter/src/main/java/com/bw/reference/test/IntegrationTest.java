package com.bw.reference.test;

import com.bw.commons.authclient.BwAuthApiClient;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.integration.sms.SMSNotificationService;
import com.bw.reference.modelfactory.ModelFactoryRegistrar;
import com.bw.reference.principal.ActivityLogBuilder;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.test.etc.ModelFactoryImpl;
import com.bw.reference.test.etc.PojoFactoryImpl;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = com.bw.reference.test.IntegrationTestApplication.class)
//@Import(com.bw.reference.test.IntegrationTest.$Configuration.class)
public abstract class IntegrationTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected RequestPrincipal requestPrincipal;

    @Autowired
    private SMSNotificationService smsNotificationService;

    protected ModelFactory modelFactory;

    @Autowired
    private DatabaseCleanupService databaseCleanupService;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected Faker faker;
    @Autowired
    protected Gson gson;

    @Autowired
    protected ApplicationContext applicationContext;
    static Long startTime;
    static Long endTime;

    @PostConstruct
    public void postConstruct() {
        modelFactory = new ModelFactoryImpl(new Faker(), transactionTemplate, entityManager);
        ModelFactoryRegistrar.register(modelFactory);
    }

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    public void before() {
        startTime = System.nanoTime();
    }

    @AfterEach
    public void after() {
        transactionTemplate.executeWithoutResult(status -> {
            entityManager.flush();
        });
        databaseCleanupService.truncate();
        Mockito.reset(requestPrincipal);
        //Mockito.reset(notificationSenderService);
        //Mockito.reset(smsNotificationBuilder);

        endTime = System.nanoTime();
        System.out.println("----------------- STATS -----------------");
        System.out.printf("Test start time : %dns%n", startTime);
        System.out.printf("Test end time   : %dns%n", endTime);
        System.out.printf("Execution time  : %dns%n", endTime - startTime);
        System.out.printf("Execution time in milliseconds : %dms%n", TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        System.out.println("------------------------------------------");
    }

    protected <T> T jsonToClass(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }

    protected WorkspaceUser loggedInUser(WorkspaceUser workspaceUser) {
        workspaceUser = workspaceUser != null ? workspaceUser : modelFactory.create(WorkspaceUser.class);
        Mockito.when(requestPrincipal.getWorkspaceUser())
                .thenReturn(workspaceUser);


        ActivityLogBuilder activityLogBuilder = Mockito.mock(ActivityLogBuilder.class);

        Mockito.when(activityLogBuilder.setEntityId(Mockito.any()))
                .thenReturn(activityLogBuilder);
        return workspaceUser;
    }

    protected WorkspaceUser loggedInUser() {
        return loggedInUser(null);
    }
//
//    @Configuration
//    @ComponentScan({
//            "com.bw.reference",
//            "com.bw.reference.dao",
//            "com.bw.reference.validator",
//            "com.bw.reference.service",
//            "com.bw.reference.sequence",
//            "com.bw.reference.integration",
//            "com.bw.commons.authclient"
//    })
//    @EnableJpaRepositories({"com.bw.reference.dao"})
    public static class $Configuration implements WebMvcConfigurer {

        @Bean
        public BwAuthApiClient bwAuthAdminApiClient() {
            return Mockito.mock(BwAuthApiClient.class);
        }

        @Bean(name = "pojoFactory")
        public ModelFactory getPojoFactory() {
            return new PojoFactoryImpl(new Faker());
        }
    }
}
