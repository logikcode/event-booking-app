package com.bw.reference.dao;

import com.bw.reference.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long>, QuerydslPredicateExecutor<State> {
    @Query("select s from State s where s.status='ACTIVE' and lower(s.name) = lower(?1) ")
    Optional<State> findActiveByName(String name);

    @Query("select s from State s  inner join s.country c where s.status='ACTIVE' and lower(s.name) = lower(?1) " +
            "and lower(c.alpha2) = lower(?2) ")
    Optional<State> findActiveByNameAndCountryCode(String name, String countryCode);

    @Query("SELECT s from State s where s.status = 'ACTIVE' and lower(s.code) = lower(?1) ")
    Optional<State> findByCode(String code);
}
