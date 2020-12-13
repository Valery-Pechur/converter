package com.valeriashtroo.converterTest.converter.service;

import com.valeriashtroo.converterTest.converter.repository.CurrencyRepository;
import com.valeriashtroo.converterTest.converter.entity.xmlCurrency.CurrencyRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyServise {
    private final CurrencyRepository currencyRepository;

    @Override
    public List<CurrencyRate.Currency> getAll() {
        return currencyRepository.findAll();
    }

    @Override
    public CurrencyRate.Currency getOne(Integer id) {
        return currencyRepository.getOne(id);
    }


    @Override
    public void save(CurrencyRate.Currency object) {
        currencyRepository.save(object);
    }

    @Override
    public void delete(CurrencyRate.Currency object) {
        currencyRepository.delete(object);
    }

    @Override
    public String convert(String from, String to, String sum) {

        BigDecimal valueFrom = new BigDecimal(currencyRepository.getValueByCharCode(from).replace(',','.'));
        BigDecimal nominalFrom = new BigDecimal(String.valueOf(currencyRepository.getNominalByCharCode(from)));
        BigDecimal valueTo = new BigDecimal(currencyRepository.getValueByCharCode(to).replace(',','.'));
        BigDecimal nominalTo = new BigDecimal(String.valueOf(currencyRepository.getNominalByCharCode(to)));

        return valueFrom.multiply(nominalTo).multiply(new BigDecimal(sum))
                .divide(valueTo.multiply(nominalFrom),4, BigDecimal.ROUND_HALF_UP).toString();
    }
    @Override
    public void readXmlWithCurrencyRateAndUpdateAll()  {
        CurrencyRate currencyRate = JAXB.unmarshal("http://www.cbr.ru/scripts/XML_daily.asp", CurrencyRate.class);
       List <CurrencyRate.Currency> currencyList = currencyRate.getCurrency();
               currencyRepository.truncateTableCurrency();
               for (CurrencyRate.Currency i: currencyList) {
           currencyRepository.save(i);
       }
       currencyRepository.save(new CurrencyRate.Currency(643,"RUR",1, "Российский рубль","1"));

    }
}
