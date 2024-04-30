package net.banking.bankingapp.impl;

import net.banking.bankingapp.dto.AccountDto;
import net.banking.bankingapp.entities.Account;
import net.banking.bankingapp.mapper.AccountMapper;
import net.banking.bankingapp.repositories.AccountRepository;
import net.banking.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class accountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    public accountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository.getAccountById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository.getAccountById(id);
        double total= account.getBalance()+amount;
        account.setBalance(total);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Account does not exists"));
        if (account.getBalance()<amount){
            throw new RuntimeException("Insuffient amount");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Account does not exists"));
        accountRepository.deleteById(id);
    }
}
