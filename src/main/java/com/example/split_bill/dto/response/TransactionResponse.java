package com.example.split_bill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String from;
    private String to;
    private BigDecimal amount;

}
