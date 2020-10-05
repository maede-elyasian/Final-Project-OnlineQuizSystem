package ir.maktab.service;


import ir.maktab.dto.*;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.ConfirmationToken;
import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.model.enums.*;
import ir.maktab.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;
    private StatusRepository statusRepository;
    private RoleService roleService;
    private StatusService statusService;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
    private PasswordEncoder passwordEncoder;
    private static final int PAGE_SIZE = 4;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          StatusRepository statusRepository,
                          RoleService roleService, StatusService statusService,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          EmailSenderService emailSenderService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.statusRepository = statusRepository;
        this.roleService = roleService;
        this.statusService = statusService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    public Account findByUsername(String username) {
        Optional<Account> found = accountRepository.findByUsername(username);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public Account registerNewUser(RegisterDto registerDto) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName(registerDto.getFirstName());
        personalInfo.setLastName(registerDto.getLastName());
        personalInfo.setEmail(registerDto.getEmail());
        personalInfo.setPhoneNumber(registerDto.getPhoneNumber());

        Account account = new Account();
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        account.setUsername(registerDto.getUsername());
        account.setRole(roleService.findByTitle(registerDto.getRoleTitle()));
        account.setStatus(statusService.findByTitle(StatusTitle.WAITING_FOR_VERIFY));
        account.setPersonalInfo(personalInfo);

        Account savedAccount = accountRepository.save(account);
//        ConfirmationToken confirmationToken = new ConfirmationToken(account);
//        confirmationTokenRepository.save(confirmationToken);
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(registerDto.getEmail());
//        mailMessage.setSubject("Complete Registration!");
//        mailMessage.setFrom("maeelyasian@gmail.com");
//        mailMessage.setText(":برای فعال سازی اکانت خود روی لینک کلیک کنید "
//                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
//        emailSenderService.sendEmail(mailMessage);
//        System.out.println("**EMAIL SENT**");
        return savedAccount;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void updateAccount(Account account) {
        Account updatingAccount = account;
        PersonalInfo personalInfo = updatingAccount.getPersonalInfo();
        personalInfo.setFirstName(updatingAccount.getPersonalInfo().getFirstName());
        personalInfo.setLastName(updatingAccount.getPersonalInfo().getLastName());
        personalInfo.setPhoneNumber(updatingAccount.getPersonalInfo().getPhoneNumber());
        personalInfo.setEmail(updatingAccount.getPersonalInfo().getEmail());

        account.setUsername(updatingAccount.getUsername());
        account.setRole((roleService.findByTitle(updatingAccount.getRole().getTitle())));
        account.setStatus(statusService.findByTitle(StatusTitle.valueOf(updatingAccount.getStatus().getTitle().name())));
    }


    public Page<Account> findAll(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
        return accountRepository.findAll(pageable);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Page<Account> getRecentUsers(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts;
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

    public Account findByEmail(String email) {
        Optional<Account> found = accountRepository.findByPersonalInfoEmail(email);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public Page<Account> findMaxMatch(String roles, String status,
                                      String name, String lastName,
                                      String email, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("username"));
        return accountRepository.findAll(AccountSpecifications.findMaxMatch(roles, status, name, lastName,
                email), pageable);
    }

    public Long countByRole(Long roleId) {
        return accountRepository.countAccountByRole(roleId);
    }

    public Account getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails)
            username = ((UserDetails) principal).getUsername();
        else
            username = principal.toString();

        return findByUsername(username);
    }
}
