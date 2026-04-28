package com.example.split_bill.controller;

import com.example.split_bill.dto.response.SettlementResponse;
import com.example.split_bill.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/settlement")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping
    public SettlementResponse getSettlement(
            @PathVariable Long groupId
    ) {
        return settlementService.getSettlement(groupId);
    }
}