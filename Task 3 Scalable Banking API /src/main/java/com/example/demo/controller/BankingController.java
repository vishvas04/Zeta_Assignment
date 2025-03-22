package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountNotFoundException;
import com.example.demo.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankingController {

    @Autowired
    private BankingService bankingService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/accounts")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        try {
            accountRepository.save(account); // Ensure AccountRepository is autowired here
            return ResponseEntity.ok("Account created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> processTransaction(
            @RequestParam String accountId,
            @RequestParam double amount,
            @RequestParam String type
    ) {
        try {
            bankingService.processTransaction(accountId, amount, type);
            return ResponseEntity.ok("Transaction successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String accountId) {
//        double x=300;
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found"));
            return ResponseEntity.ok(account.getBalance());
//        return ResponseEntity.ok(1000.0); // Simplified for example
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return 404
    }
}
