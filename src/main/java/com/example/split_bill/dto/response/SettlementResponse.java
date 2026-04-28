package com.example.split_bill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementResponse {

    private BigDecimal totalExpenses;
    private Integer serviceChargePct;
    private BigDecimal serviceChargeAmount;
    private List<TransactionResponse> transactions;

}
