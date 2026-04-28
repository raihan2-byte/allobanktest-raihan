package com.example.split_bill.controller;

import com.example.split_bill.dto.request.CreateExpenseRequest;
import com.example.split_bill.dto.response.ExpenseResponse;
import com.example.split_bill.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse addExpense(
            @PathVariable Long groupId,
            @RequestBody CreateExpenseRequest request
    ) {
        return expenseService.addExpense(groupId, request);
    }
}