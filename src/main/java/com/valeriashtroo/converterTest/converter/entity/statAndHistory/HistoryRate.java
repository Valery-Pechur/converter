package com.valeriashtroo.converterTest.converter.entity.statAndHistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stat_rate")
public class HistoryRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "char_code_from")
    private String charCodeFrom;

    @Column(name = "char_code_to")
    private String charCodeTo;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "conversion_rate")
    private BigDecimal conversionRate;

    public HistoryRate(String charCodeFrom, String charCodeTo, LocalDate date, BigDecimal conversionRate) {
        this.charCodeFrom = charCodeFrom;
        this.charCodeTo = charCodeTo;
        this.date = date;
        this.conversionRate = conversionRate;
    }
}
