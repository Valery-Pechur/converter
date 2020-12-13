package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import com.valeriashtroo.converterTest.converter.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public Page<HistoryRate> getAllBySpecification(Specification<HistoryRate> spec, Pageable pageable) {
        return historyRepository.findAll(spec, pageable);
    }

    @Override
    public void save(HistoryRate historyRate) {
        historyRepository.save(historyRate);
    }

    @Override
    public void clearHistoryWhereDateGreaterThanWeek() {

        historyRepository.clearHistoryWhereDateGreaterThenOneWeek(LocalDate.now().minusWeeks(1l));
    }


}
