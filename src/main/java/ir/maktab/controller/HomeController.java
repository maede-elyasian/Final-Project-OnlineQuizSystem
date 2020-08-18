package ir.maktab.controller;

import ir.maktab.dto.RegisterDto;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.model.enums.AccountStatus;
import ir.maktab.model.enums.RoleTitle;
import ir.maktab.service.AccountService;
import ir.maktab.service.PersonalInfoService;
import ir.maktab.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class HomeController {
    private PersonalInfoService personalInfoService;
    private AccountService accountService;
    private RoleService roleService;

    @Autowired
    public HomeController(AccountService accountService,
                          PersonalInfoService personalInfoService,
                          RoleService roleService) {
        this.accountService = accountService;
        this.personalInfoService = personalInfoService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String RegisterPage(Model model, HttpServletRequest request) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute RegisterDto registerDto) {
        String redirectUrl = "/register/?";
        boolean isValidInput = true;

        if (accountService.usernameExist(registerDto.getUsername())) {
            redirectUrl += "usernameError";
            isValidInput = false;
        }
        if (personalInfoService.emailExist(registerDto.getEmail())) {
            redirectUrl += "emailError";
            isValidInput = false;
        }

        if (!isValidInput) {
            return "redirect:" + redirectUrl;
        } else {
            PersonalInfo personalInfo = new PersonalInfo();
            personalInfo.setFirstName(registerDto.getFirstName());
            personalInfo.setLastName(registerDto.getLastName());
            personalInfo.setEmail(registerDto.getEmail());
            personalInfo.setPhoneNumber(registerDto.getPhoneNumber());
            PersonalInfo savedPersonalInfo = personalInfoService.save(personalInfo);

            Account account = new Account();
            account.setPassword(registerDto.getPassword());
            account.setUsername(registerDto.getUsername());
            account.setRoleTitle(
                    new ArrayList<>(Arrays.asList(roleService.findByTitle
                            (RoleTitle.valueOf(registerDto.getRoleTitle()))))
            );
            account.setAccountStatus(AccountStatus.AWAITING);
            account.setPersonalInfo(savedPersonalInfo);
            accountService.save(account);
            return "sign-in";
        }

    }


}
