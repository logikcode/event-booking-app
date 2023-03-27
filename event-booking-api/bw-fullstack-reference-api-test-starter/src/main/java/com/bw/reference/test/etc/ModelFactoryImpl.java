package com.bw.reference.test.etc;

import com.github.heywhy.springentityfactory.contracts.EntityFactoryBuilder;
import com.github.heywhy.springentityfactory.contracts.FactoryFunction;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ModelFactoryImpl implements ModelFactory {

    private TransactionTemplate transactionTemplate;
    private EntityManager entityManager;
    private final Faker faker;
    private final Map<String, FactoryFunction> states = new HashMap<>();
    private final Map<Class, FactoryFunction> definitions = new HashMap<>();

    public ModelFactoryImpl(Faker faker) {
        this.faker = faker;
    }

    public ModelFactoryImpl(Faker faker, TransactionTemplate transactionTemplate, EntityManager entityManager) {
        this(faker);
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    @Override
    public <T> void register(FactoryHelper<T> factoryHelper) {
        define(factoryHelper.getEntity(), faker1 -> factoryHelper.apply(faker1, this));
    }

    @Override
    public <T> void register(Class<? extends FactoryHelper<T>> factoryHelper) {
        try {
            register(factoryHelper.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            String msg = "Unable to create instance of [" + factoryHelper.getSimpleName() + "].";
            throw new IllegalArgumentException(msg, e);
        }
    }

    @Override
    public ModelFactory define(Class model, FactoryFunction builder) {
        definitions.put(model, builder);
        return this;
    }

    @Override
    public <T> T create(Class<T> model) {
        return of(model).create();
    }

    @Override
    public <T> List<T> create(Class<T> model, int count) {
        return of(model).create(count);
    }

    @Override
    public <T> T make(Class<T> model) {
        return of(model).make();
    }

    @Override
    public <T> List<T> make(Class<T> model, int count) {
        return of(model).make(count);
    }

    @Override
    public <T> EntityFactoryBuilder<T> pipe(Class<T> model) {
        return of(model);
    }

    private String getStateName(Class model, String name) {
        return model.getSimpleName() + '@' + name;
    }

    private <T> EntityFactoryBuilder<T> of(Class<T> model) {
        return of(model, null);
    }

    private <T> EntityFactoryBuilder<T> of(Class<T> model, Function<T, T> operator) {
        return new CustomEntityFactoryBuilderImpl<>(model, definitions, faker, entityManager, operator, transactionTemplate);
    }
}
