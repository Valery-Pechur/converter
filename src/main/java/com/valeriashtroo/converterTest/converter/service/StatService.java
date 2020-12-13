package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.Stat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface StatService {
    Page <Stat> getAll(Pageable p, Specification<Stat> spec);
}
