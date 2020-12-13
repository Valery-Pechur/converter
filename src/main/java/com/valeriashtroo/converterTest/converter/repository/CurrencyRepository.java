package com.valeriashtroo.converterTest.converter.repository;

import com.valeriashtroo.converterTest.converter.entity.xmlCurrency.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.transaction.Transactional;


public interface CurrencyRepository extends JpaRepository<CurrencyRate.Currency, Integer>,
        QueryByExampleExecutor<CurrencyRate.Currency> {

    @Modifying
    @Transactional
    @Query(
            value = "truncate table currency",
            nativeQuery = true
    )
    void truncateTableCurrency();

    @Query(value = "select currency.value from currency  where currency.char_code=:charCode", nativeQuery = true)
    String getValueByCharCode(String charCode);

    @Query(value = "select currency.nominal from currency  where currency.char_code=:charCode", nativeQuery = true)
    Long getNominalByCharCode(String charCode);
}
