package mutuku.coke.bankingapp.service.impl;

import jakarta.transaction.Transactional;
import mutuku.coke.bankingapp.dto.AccountDto;
import mutuku.coke.bankingapp.entity.Account;
import mutuku.coke.bankingapp.mapper.AccountMapper;
import mutuku.coke.bankingapp.repository.AccountRepository;
import mutuku.coke.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);

        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    @Cacheable(value= "account" , key = "#id")
    public AccountDto getAccountById(Long id) {

        System.out.println("Getting account from database...");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist"));

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    @CachePut(value= "account" , key = "#id")
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist"));

        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    @CachePut(value= "account" , key = "#id")
    public AccountDto withdrawal(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist"));

        if (account.getBalance() < amount){
       throw new RuntimeException("Insufficient Balance");
        }

        double total = account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account -> AccountMapper.mapToAccountDto(account))).collect(Collectors.toList());


    }

    @Override
    @CacheEvict(value= "account" , key = "#id")
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String internalTransfer(Long fromId, Long toId, double amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Account From doesn't exist"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Account To doesn't exist"));

        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
       from.setBalance(from.getBalance()- amount);
        to.setBalance(to.getBalance()+amount);
        accountRepository.save(from);
        accountRepository.save(to);
        return "Transfer was successful";
    }


}
