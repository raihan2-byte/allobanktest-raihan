package com.example.split_bill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequest {

    private String description;
    private BigDecimal amount;
    private Long paidByParticipantId;
    private List<Long> participantIds;

}