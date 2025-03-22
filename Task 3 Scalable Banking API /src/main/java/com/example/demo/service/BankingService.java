package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankingService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public void processTransaction(String accountId, double amount, String type) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (type.equals("debit") && account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        double newBalance = type.equals("debit") ?
                account.getBalance() - amount :
                account.getBalance() + amount;

        account.setBalance(newBalance);
        accountRepository.save(account); // Optimistic locking handled via @Version
    }
}
