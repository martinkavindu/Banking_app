package mutuku.coke.bankingapp.controller;

import jakarta.validation.Valid;
import mutuku.coke.bankingapp.dto.AccountDto;
import mutuku.coke.bankingapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    //add account restapi
    @PostMapping()
    public ResponseEntity<AccountDto> addAccount(@Valid @RequestBody AccountDto accountDto){
        return  new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }
    //getaccountdetails
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }
    //deposit api
    @PutMapping("/{id}/deposit")

    public  ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String,Double>request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);

    }
    //withdraw api
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String,Double>request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdrawal(id,amount);
        return ResponseEntity.ok(accountDto);

    }
    @PostMapping("/transfer")
    public ResponseEntity<String> internalTransfer(@RequestBody Map<String, Object> request) {
        Long from = Long.valueOf(request.get("from").toString());
        Long to = Long.valueOf(request.get("to").toString());
        double amount = Double.parseDouble(request.get("amount").toString());

        String result = accountService.internalTransfer(from, to, amount);
        return ResponseEntity.ok(result);
    }

    //getallaccounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }


}
