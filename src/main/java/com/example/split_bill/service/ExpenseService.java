package com.example.split_bill.service;

import com.example.split_bill.dto.request.CreateExpenseRequest;
import com.example.split_bill.dto.response.ExpenseResponse;
import com.example.split_bill.entity.BillGroup;
import com.example.split_bill.entity.Expense;
import com.example.split_bill.entity.ExpenseShare;
import com.example.split_bill.entity.Participant;
import com.example.split_bill.repository.ExpenseRepository;
import com.example.split_bill.repository.ParticipantRepository;
import com.example.split_bill.util.MoneyUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ParticipantRepository participantRepository;
    private final GroupService groupService;

    @Transactional
    public ExpenseResponse addExpense(Long groupId, CreateExpenseRequest request) {

        BillGroup group = groupService.getGroupEntity(groupId);

        Participant payer = participantRepository
                .findByIdAndGroupId(request.getPaidByParticipantId(), groupId)
                .orElseThrow(() -> new RuntimeException("Payer not found"));

        List<Participant> targets = participantRepository.findAllById(request.getParticipantIds());

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(MoneyUtils.scale(request.getAmount()));
        expense.setPaidBy(payer);
        expense.setGroup(group);

        BigDecimal shareAmount =
                MoneyUtils.divide(request.getAmount(), targets.size());

        List<ExpenseShare> shares = new ArrayList<>();

        for (Participant participant : targets) {
            ExpenseShare share = new ExpenseShare();
            share.setExpense(expense);
            share.setParticipant(participant);
            share.setShareAmount(shareAmount);
            shares.add(share);
        }

        expense.setShares(shares);

        Expense saved = expenseRepository.save(expense);

        ExpenseResponse response = new ExpenseResponse();
        response.setId(saved.getId());
        response.setDescription(saved.getDescription());
        response.setAmount(saved.getAmount());
        response.setPaidBy(saved.getPaidBy().getName());

        return response;
    }

    public List<Expense> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }
}