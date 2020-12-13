package com.valeriashtroo.converterTest.converter.specification;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.Stat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StatSpecification implements Specification<Stat> {
    private final Stat filter;

    @Override
    public Predicate toPredicate(Root<Stat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getChar_code_from() != null && !filter.getChar_code_from().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("char_code_from")), filter.getChar_code_from().toLowerCase() + "%"));
        }

        if (filter.getChar_code_to() != null && !filter.getChar_code_to().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("char_code_to")), filter.getChar_code_to().toLowerCase() + "%"));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true).getRestriction();
    }
}
