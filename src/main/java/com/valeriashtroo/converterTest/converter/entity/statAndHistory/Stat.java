package com.valeriashtroo.converterTest.converter.entity.statAndHistory;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Immutable
@Subselect("SELECT char_code_from, char_code_to, COUNT(*) as number_of_conversions, ROUND(AVG(conversion_rate),2) as avg_conversion_rate " +
        "FROM stat_rate " +
        "GROUP BY char_code_from, char_code_to ORDER BY char_code_from, char_code_to")
@Synchronize("stat_rate")
@Getter
@Setter
@NoArgsConstructor
@IdClass(Stat.StatPK.class)
public class Stat implements Serializable {

    @Id
    private String char_code_from;

    @Id
    private String char_code_to;

    private int number_of_conversions;

    private BigDecimal avg_conversion_rate;

    @AllArgsConstructor
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class StatPK implements Serializable{
        String char_code_from;
        String char_code_to;

    }

}
