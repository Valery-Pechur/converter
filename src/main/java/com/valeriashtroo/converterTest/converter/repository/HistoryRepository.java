package com.valeriashtroo.converterTest.converter.repository;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.transaction.Transactional;
import java.time.LocalDate;

public interface HistoryRepository extends
        JpaRepository<HistoryRate, Long>,
        JpaSpecificationExecutor<HistoryRate>,
        QueryByExampleExecutor<HistoryRate> {

    Page<HistoryRate> findAll(Specification<HistoryRate> spec, Pageable pageable);

    @Modifying
    @Transactional
    @Query(
            value = "delete from HistoryRate where date < ?1"

    )
    void clearHistoryWhereDateGreaterThenOneWeek(LocalDate date);


}
