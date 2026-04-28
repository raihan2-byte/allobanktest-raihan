package com.example.split_bill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "paid_by_id")
    private Participant paidBy;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private BillGroup group;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseShare> shares = new ArrayList<>();
}