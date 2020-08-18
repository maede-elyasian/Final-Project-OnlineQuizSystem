package ir.maktab.service;


import ir.maktab.model.entity.Account;
import ir.maktab.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public boolean usernameExist(String username){
        if (accountRepository.findByUsername(username).isPresent()){
            return true;
        }
        return false;
    }

    public Account save(Account account){
       return accountRepository.save(account);

    }
}
