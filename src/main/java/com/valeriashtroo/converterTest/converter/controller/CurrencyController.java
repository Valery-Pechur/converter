package com.valeriashtroo.converterTest.converter.controller;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import com.valeriashtroo.converterTest.converter.entity.statAndHistory.Stat;
import com.valeriashtroo.converterTest.converter.payload.HistoryListResponse;
import com.valeriashtroo.converterTest.converter.service.CurrencyServise;
import com.valeriashtroo.converterTest.converter.service.HistoryService;
import com.valeriashtroo.converterTest.converter.service.StatService;
import com.valeriashtroo.converterTest.converter.specification.HistorySpecification;
import com.valeriashtroo.converterTest.converter.specification.StatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CurrencyController {
    private final CurrencyServise currencyServise;
    private final HistoryService historyService;
    private final StatService statService;

    /**
     *
     * @param from из какой валюты будет конвертация
     * @param to в какую валюту будет конвертация
     * @param sum сумма валюты, которую нужно конвертировать
     * @return результат конвертации
     */
    @GetMapping("/api/currency-converter")
    public ResponseEntity<?> convertCurrency(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("sum") String sum
    ) {
        String result = currencyServise.convert(from, to, sum);//конвертация

        //сохранение операции в историю конвертаций:
        historyService.save(new HistoryRate(from, to, LocalDate.now()
                , new BigDecimal(result).divide(new BigDecimal(sum), 4, BigDecimal.ROUND_HALF_UP)));

        return ResponseEntity.ok(result);

    }

    /**
     *
     * @param dateFilter дата выпонения конвертаций - параметр фильтрации
     * @param charCodeFromFilter - из какой валюты была конвертация - параметр фильтрации
     * @param charCodeToFilter- в какую валюту была конвертация - параметр фильтрации
     * @param pageCount - номер страницы
     * @param pageSize - количество элементов на странице
     * @return история конвертаций
     */
    @GetMapping("/api/currency-converter/history")
    public ResponseEntity<?> statConvertCurrency(
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFilter,
            @RequestParam(name = "charCodeFrom", required = false, defaultValue = "") String charCodeFromFilter,
            @RequestParam(name = "charCodeTo", required = false, defaultValue = "") String charCodeToFilter,
            @RequestParam(name = "pageCount", required = false, defaultValue = "0") int pageCount,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {

        HistoryRate filter = new HistoryRate();
        if (dateFilter != null) {
            filter.setDate(dateFilter);
        }
        filter.setCharCodeFrom(charCodeFromFilter);
        filter.setCharCodeTo(charCodeToFilter);

        Pageable pageable = PageRequest.of(pageCount, pageSize, Sort.by("date").descending().and(Sort.by("charCodeFrom")).and(Sort.by("charCodeTo")));
        Page<HistoryRate> page = historyService.getAllBySpecification(new HistorySpecification(filter), pageable);

        return ResponseEntity.ok(new HistoryListResponse(page.getTotalElements(), page.get().collect(Collectors.toList())));

    }

    /**
     *
     * @param charCodeFromFilter - из какой валюты была конвертация - параметр фильтрации
     * @param charCodeToFilter- в какую валюту была конвертация - параметр фильтрации
     * @param pageable - характеристики страницы по умолчанию
     * @return статистические данные
     */
    @GetMapping("/api/currency-converter/stat")
    public ResponseEntity<?> statConvertCurrency(
            @RequestParam(name = "charCodeFrom", required = false, defaultValue = "") String charCodeFromFilter,
            @RequestParam(name = "charCodeTo", required = false, defaultValue = "") String charCodeToFilter,
            @PageableDefault(page = 0, size = 15) Pageable pageable //параметры страницы по умолчанию
    ) {
        Stat filter = new Stat();
        filter.setChar_code_from(charCodeFromFilter);
        filter.setChar_code_to(charCodeToFilter);

        Page<Stat> page = statService.getAll(pageable, new StatSpecification(filter));
        return ResponseEntity.ok(page.get().collect(Collectors.toList()));
    }
}
