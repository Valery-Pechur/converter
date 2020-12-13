package com.valeriashtroo.converterTest.converter.scheduler;

import com.valeriashtroo.converterTest.converter.service.CurrencyServise;
import com.valeriashtroo.converterTest.converter.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class UpdateCurrencyRateScheduler {

    private final CurrencyServise currencyServise;
    private final HistoryService historyService;

    /**
     * обновление курса валют на текущую дату и актуализации истории конвертаций
     * при развертывании программы
     */
    @PostConstruct
    public void init() {
        currencyServise.readXmlWithCurrencyRateAndUpdateAll();
        historyService.clearHistoryWhereDateGreaterThanWeek();
    }

    /**
     * обновление курса валют на текущую дату в 15-00 по московскому времени каждый день
     */
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Moscow")
    public void getAndSendNomenclatureOrders() {
        currencyServise.readXmlWithCurrencyRateAndUpdateAll();
    }

    /**
     * актуализация истории конвертаций в 15-00 по московскому времени каждый день
     */
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Moscow")
    public void clearHistory() {
        historyService.clearHistoryWhereDateGreaterThanWeek();
    }

}
