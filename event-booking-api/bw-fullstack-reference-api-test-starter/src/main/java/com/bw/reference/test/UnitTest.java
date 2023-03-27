package com.bw.reference.test;

import com.bw.reference.modelfactory.ModelFactoryRegistrar;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.heywhy.springentityfactory.impl.ModelFactoryImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.util.Random;

public abstract class UnitTest {
    protected ModelFactory modelFactory;
    protected Faker faker;
    protected EntityManager entityManager;


    @BeforeEach
    void setUpModelFactory() {
        entityManager = Mockito.mock(EntityManager.class);
        faker = new Faker(new Random());
        modelFactory = new ModelFactoryImpl(faker, entityManager);
        ModelFactoryRegistrar.register(modelFactory);
    }

    @AfterEach
    void destroyModelfactory() {
        modelFactory = null;
        Mockito.reset(entityManager);
    }
}
