package com.bank.banking_system.controller;

import com.bank.banking_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public void deposit(@RequestParam String accountNumber,
                        @RequestParam Double amount) {
        transactionService.deposit(accountNumber, amount);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestParam String accountNumber,
                         @RequestParam Double amount) {
        transactionService.withdraw(accountNumber, amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam String fromAcc,
                         @RequestParam String toAcc,
                         @RequestParam Double amount) {
        transactionService.transfer(fromAcc, toAcc, amount);
    }
}