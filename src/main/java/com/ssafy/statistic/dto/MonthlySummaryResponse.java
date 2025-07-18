package com.ssafy.statistic.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MonthlySummaryResponse {
    private String month;
    private Double income;
    private Double expense;
    private Double net;
}
