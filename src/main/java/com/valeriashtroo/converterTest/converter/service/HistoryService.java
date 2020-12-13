package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface HistoryService {
    Page<HistoryRate> getAllBySpecification(Specification<HistoryRate> spec, Pageable pageable);

    void save(HistoryRate historyRate);

    void clearHistoryWhereDateGreaterThanWeek();
}
