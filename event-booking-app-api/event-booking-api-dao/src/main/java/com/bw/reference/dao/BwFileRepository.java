package com.bw.reference.dao;

import com.bw.entity.BwFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BwFileRepository extends JpaRepository<BwFile, Long>, QuerydslPredicateExecutor<BwFile> {
}
