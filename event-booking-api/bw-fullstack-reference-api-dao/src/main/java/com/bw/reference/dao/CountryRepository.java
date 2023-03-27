package com.bw.reference.dao;

import com.bw.reference.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c where lower(c.name) = lower(?1) ")
    Optional<Country> findByName(String name);

    @Query("select c from Country c where lower(c.alpha2) = lower(?1) ")
    Optional<Country> findByAlpha2(String alpha2);

    @Query("select c from Country c where c.id in ?1 ")
    List<Country> findByIds(List<Long> ids);

    @Query("select c from Country c where c.id = ?1")
    Optional<Country> findById(Long id);
}
