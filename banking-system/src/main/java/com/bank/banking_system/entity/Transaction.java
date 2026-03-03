package com.bank.banking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType; // DEPOSIT, WITHDRAW, TRANSFER

    private Double amount;

    private LocalDateTime transactionDate;

    private String fromAccount;

    private String toAccount;
}