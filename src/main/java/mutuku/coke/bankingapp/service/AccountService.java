package mutuku.coke.bankingapp.service;

import mutuku.coke.bankingapp.dto.AccountDto;
import mutuku.coke.bankingapp.entity.Account;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id , double amount);
    AccountDto withdrawal (Long id , double amount);
    List<AccountDto> getAccounts();
    void  deleteAccount(Long id);


}
