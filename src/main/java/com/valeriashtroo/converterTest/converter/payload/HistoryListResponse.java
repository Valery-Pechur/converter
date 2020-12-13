package com.valeriashtroo.converterTest.converter.payload;

import com.valeriashtroo.converterTest.converter.entity.statAndHistory.HistoryRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HistoryListResponse {
    private long totalStatElemCount;
    private List<HistoryRate> historyRateList;

}

