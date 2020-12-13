package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.Stat;
import com.valeriashtroo.converterTest.converter.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public Page<Stat> getAll(Pageable p, Specification<Stat> spec) {
        return statRepository.findAll(spec, p);
    }
}

