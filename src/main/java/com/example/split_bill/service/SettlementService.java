package com.example.split_bill.service;

import com.example.split_bill.dto.response.SettlementResponse;
import com.example.split_bill.dto.response.TransactionResponse;
import com.example.split_bill.entity.Expense;
import com.example.split_bill.entity.ExpenseShare;
import com.example.split_bill.util.GithubChargeUtils;
import com.example.split_bill.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final ExpenseService expenseService;

    @Transactional(readOnly = true)
    public SettlementResponse getSettlement(Long groupId) {

        List<Expense> expenses = expenseService.getExpensesByGroup(groupId);

        Map<String, BigDecimal> balances = new HashMap<>();
        BigDecimal totalExpenses = MoneyUtils.zero();

        for (Expense expense : expenses) {

            String payer = expense.getPaidBy().getName();

            balances.putIfAbsent(payer, MoneyUtils.zero());
            balances.put(
                    payer,
                    MoneyUtils.add(balances.get(payer), expense.getAmount())
            );

            totalExpenses =
                    MoneyUtils.add(totalExpenses, expense.getAmount());

            for (ExpenseShare share : expense.getShares()) {

                String participant = share.getParticipant().getName();

                balances.putIfAbsent(participant, MoneyUtils.zero());

                balances.put(
                        participant,
                        MoneyUtils.subtract(
                                balances.get(participant),
                                share.getShareAmount()
                        )
                );
            }
        }

        List<TransactionResponse> transactions =
                simplifyBalances(balances);

        int pct = GithubChargeUtils.getServiceChargePct();
        BigDecimal charge =
                GithubChargeUtils.calculateCharge(totalExpenses);

        SettlementResponse response = new SettlementResponse();
        response.setTotalExpenses(totalExpenses);
        response.setServiceChargePct(pct);
        response.setServiceChargeAmount(charge);
        response.setTransactions(transactions);

        return response;
    }

    private List<TransactionResponse> simplifyBalances(
            Map<String, BigDecimal> balances
    ) {

        List<Map.Entry<String, BigDecimal>> creditors = new ArrayList<>();
        List<Map.Entry<String, BigDecimal>> debtors = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : balances.entrySet()) {

            if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(entry);
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(entry);
            }
        }

        List<TransactionResponse> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < debtors.size() && j < creditors.size()) {

            BigDecimal debt = debtors.get(i).getValue().abs();
            BigDecimal credit = creditors.get(j).getValue();

            BigDecimal pay = debt.min(credit);

            TransactionResponse trx = new TransactionResponse();
            trx.setFrom(debtors.get(i).getKey());
            trx.setTo(creditors.get(j).getKey());
            trx.setAmount(MoneyUtils.scale(pay));

            result.add(trx);

            debtors.get(i).setValue(
                    debtors.get(i).getValue().add(pay)
            );

            creditors.get(j).setValue(
                    creditors.get(j).getValue().subtract(pay)
            );

            if (debtors.get(i).getValue().compareTo(BigDecimal.ZERO) == 0) {
                i++;
            }

            if (creditors.get(j).getValue().compareTo(BigDecimal.ZERO) == 0) {
                j++;
            }
        }

        return result;
    }
}