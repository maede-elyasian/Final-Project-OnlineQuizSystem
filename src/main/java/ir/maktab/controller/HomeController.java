package ir.maktab.controller;

import ir.maktab.dto.RegisterDto;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.ConfirmationToken;
import ir.maktab.repository.ConfirmationTokenRepository;
import ir.maktab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private PersonalInfoService personalInfoService;
    private AccountService accountService;
    private RoleService roleService;
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public HomeController(PersonalInfoService personalInfoService,
                          AccountService accountService,
                          RoleService roleService,
                          ConfirmationTokenRepository confirmationTokenRepository) {
        this.personalInfoService = personalInfoService;
        this.accountService = accountService;
        this.roleService = roleService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @RequestMapping(value = "/index")
    public String getHome() {
        return "index";
    }

    @GetMapping(value = "/register")
    public String RegisterPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        model.addAttribute("allRoles", roleService.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute RegisterDto registerDto, Model model) {
        String redirectUrl = "/register/?";
        boolean isValidInput = true;

        if (accountService.findByUsername(registerDto.getUsername()) != null) {
            redirectUrl += "&usernameError";
            isValidInput = false;
        }
        if (personalInfoService.emailExist(registerDto.getEmail()) != null) {
            redirectUrl += "&emailError";
            isValidInput = false;
        }
        if (!isValidInput) {
            return "redirect:" + redirectUrl;
        } else {
            accountService.registerNewUser(registerDto);
            model.addAttribute("userEmail", registerDto.getEmail());
            return "sign-in";
        }
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            Account account = accountService.findByEmail(token.getAccount().getPersonalInfo().getEmail());
            account.setEnabled(true);
            accountService.save(account);
            modelAndView.setViewName("accountVerified");
            confirmationTokenRepository.deleteById(token.getTokenid());
        } else {
            modelAndView.addObject("message", "Link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

    @GetMapping(value = "/signIn")
    public String signIn() {
        return "sign-in";
    }

}
