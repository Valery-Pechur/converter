package com.valeriashtroo.converterTest.converter.specification;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HistorySpecification implements Specification<HistoryRate> {
    private final HistoryRate filter;

    @Override
    public Predicate toPredicate(Root<HistoryRate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.greaterThanOrEqualTo(root.get("date"), LocalDate.now().minusDays(7)));

        if (filter.getDate() != null) {
            predicates.add(cb.equal(root.get("date"), filter.getDate()));
        }

        if (filter.getCharCodeFrom() != null && !filter.getCharCodeFrom().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("charCodeFrom")), filter.getCharCodeFrom().toLowerCase() + "%"));
        }

        if (filter.getCharCodeTo() != null && !filter.getCharCodeTo().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("charCodeTo")), filter.getCharCodeTo().toLowerCase() + "%"));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true).getRestriction();
    }
}
