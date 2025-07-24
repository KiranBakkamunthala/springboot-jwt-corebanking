package com.example.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.core.entity.Account;
import com.example.core.entity.Customer;
import com.example.core.exception.AccountClosedException;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.AccountRepository;
import com.example.core.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Account createAccount(Long customerId, String accountType) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setBalance(0.0);
        account.setStatus("Active");
        return accountRepository.save(account);
    }

    @Override
    public Account deposit(Long accountNumber, Double amount) {
    	logger.info("Initiating deposit of {} into account {}", amount, accountNumber);
    	Account account = accountRepository.findById(accountNumber)
    	        .orElseThrow(() -> new EntityNotFoundException("Account not found"));

    	    if ("Closed".equalsIgnoreCase(account.getStatus())) {
    	        throw new AccountClosedException("Cannot deposit into a closed account. Please contact your branch.");
    	    }

    	    account.setBalance(account.getBalance() + amount);
    	    return accountRepository.save(account);
    }

    @Override
    public Account withdraw(Long accountNumber, Double amount) {
    	logger.info("Initiating withdrawal of {} from account {}", amount, accountNumber);
    	Account account = accountRepository.findById(accountNumber)
    	        .orElseThrow(() -> new EntityNotFoundException("Account not found"));

    	    if ("Closed".equalsIgnoreCase(account.getStatus())) {
    	    	throw new AccountClosedException("Cannot withdraw from a closed account.Please contact your branch.");
    	    }

    	    if (account.getBalance() < amount) {
    	        throw new IllegalArgumentException("Insufficient balance.");
    	    }

    	    account.setBalance(account.getBalance() - amount);
    	    return accountRepository.save(account);
    }

    @Override
    public Account closeAccount(Long accountNumber) {
        Account account = getAccount(accountNumber);
        account.setStatus("Closed");
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
    }
}
