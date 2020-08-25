package ir.maktab.service;


import ir.maktab.dto.SearchAccountDto;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Role;
import ir.maktab.model.entity.Status;
import ir.maktab.model.enums.RoleTitle;
import ir.maktab.model.enums.StatusTitle;
import ir.maktab.repository.AccountRepository;
import ir.maktab.repository.AccountSpecifications;
import ir.maktab.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StatusRepository statusRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, StatusRepository statusRepository) {
        this.accountRepository = accountRepository;
        this.statusRepository = statusRepository;
    }

    public Account findByUsername(String username) {
        Optional<Account> found = accountRepository.findByUsername(username);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public Account findByPassword(String password) {
        Optional<Account> found = accountRepository.findByPassword(password);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public Account save(Account account) {
        return accountRepository.save(account);

    }

    public Account findByUsernameAndPassword(String username, String password) {
        Optional<Account> found = accountRepository.findByUsernameAndPassword(username, password);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }

    public void activateAccount(Long id) {
        Account account = findById(id);
        account.setStatus(statusRepository.findByTitle(StatusTitle.ACTIVE));
        save(account);
    }

    public void inActivateAccount(Long id) {
        Account account = findById(id);
        account.setStatus(statusRepository.findByTitle(StatusTitle.INACTIVE));
        save(account);
    }

    public Account findByEmail(String email){
        Optional<Account> found = accountRepository.findByPersonalInfoEmail(email);
        if (found.isPresent()){
            return found.get();
        }
        return null;
    }

    public List<Account> findMaxMatch(String roles, String statuses,
                                      String name, String lastName,
                                      String email, String nationalCode){
      return accountRepository.findAll(AccountSpecifications.findMaxMatch(roles,statuses,name,lastName,
             email,nationalCode));

    }

    public Long countByRole(Long roleId){
        return accountRepository.countAccountByRole(roleId);
    }
}
