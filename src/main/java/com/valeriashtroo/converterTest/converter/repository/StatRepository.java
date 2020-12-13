package com.valeriashtroo.converterTest.converter.repository;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.Stat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface StatRepository extends JpaRepository<Stat, Stat.StatPK> {
    Page <Stat> findAll(Specification<Stat> spec, Pageable pageable);

}
