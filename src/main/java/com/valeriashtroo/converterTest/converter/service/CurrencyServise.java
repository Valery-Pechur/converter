package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.entity.xmlCurrency.CurrencyRate;

import java.util.List;

public interface CurrencyServise {
    List<CurrencyRate.Currency> getAll();

    CurrencyRate.Currency getOne(Integer id);

    void save(CurrencyRate.Currency object);

    void delete(CurrencyRate.Currency object);

    String convert(String from, String to, String sum);

    void readXmlWithCurrencyRateAndUpdateAll();

}
