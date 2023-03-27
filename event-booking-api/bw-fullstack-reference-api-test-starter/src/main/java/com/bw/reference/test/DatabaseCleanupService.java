package com.bw.reference.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * August, 2020
 **/
@Service
@TestProfile
public class DatabaseCleanupService implements InitializingBean {

    private final EntityManager entityManager;
    private List<String> tableNames;

    public DatabaseCleanupService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Utility method that truncates all identified tables
     */
    @Transactional
    public void truncate() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Object[]> tableTuple = entityManager.createNativeQuery("SHOW TABLES FROM PUBLIC").getResultList();

        tableNames = tableTuple.stream().map(o -> (String) o[0]).collect(Collectors.toList());
    }
}
