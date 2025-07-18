package com.ssafy.social.transaction.entity;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor //기본 생성자
public class GroupTransaction {
    private int id;
    private int groupId;
    private Long userId;
    private String type;  // 'INCOME' or 'EXPENSE'
    private BigDecimal amount;
    private int categoryId;
    private String description;
    private Date date;
}

