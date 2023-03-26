package com.bw.reference.dao;

import com.bw.reference.entity.City;
import com.bw.reference.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c join fetch c.state as st join fetch st.country where c.code = ?1 and c.status ='ACTIVE'")
    Optional<City> findActiveByCode(String code);

    @Query("select c from City c  inner join c.state s where c.status='ACTIVE' and lower(c.name) = lower(?1) " +
            "and lower(s.code) = lower(?2) ")
    Optional<City> findActiveByNameAndStateCode(String name, String stateCode);

    @Query("select c from City c where c.state = ?1 and c.status ='ACTIVE'")
    List<City> findActiveByState(State state);

    @Query("select c from City c where c.code = ?1")
    Optional<City> findByCode(String code);
}
