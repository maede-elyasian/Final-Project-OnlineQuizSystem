package ir.maktab.controller;

import ir.maktab.dto.LoginDto;
import ir.maktab.dto.RegisterDto;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.ConfirmationToken;
import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.model.enums.RoleTitle;
import ir.maktab.model.enums.StatusTitle;
import ir.maktab.repository.ConfirmationTokenRepository;
import ir.maktab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private PersonalInfoService personalInfoService;
    private AccountService accountService;
    private RoleService roleService;
    private StatusService statusService;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
    private CourseService courseService;


    @Autowired
    public HomeController(PersonalInfoService personalInfoService,
                          AccountService accountService,
                          RoleService roleService, StatusService statusService,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          EmailSenderService emailSenderService, CourseService courseService) {
        this.personalInfoService = personalInfoService;
        this.accountService = accountService;
        this.roleService = roleService;
        this.statusService = statusService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/index")
    public String showHome() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginProcess(@ModelAttribute LoginDto loginDto, Model model) {
        String redirectUrl = "/login?";

        if ((loginDto.getUsername().equals("admin")) && (loginDto.getPassword().equals("admin"))) {
            model.addAttribute("accounts", accountService.findAll());
            model.addAttribute("statuses", statusService.findAll());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("studentCounter", accountService.countByRole((long) 2));
            model.addAttribute("teacherCounter", accountService.countByRole((long) 3));
            model.addAttribute("courseCounter", courseService.countAll());

            return "admin-dashboard";

        } else {
            redirectUrl += "loginError";
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = "/register")
    public String RegisterPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        model.addAttribute("allRoles", roleService.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute RegisterDto registerDto, Model model) {
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
        if (personalInfoService.findByNationalCode(registerDto.getNationalCode()) != null) {
            redirectUrl += "&nationalCodeError";
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
            personalInfo.setNationalCode(registerDto.getNationalCode());


            Account account = new Account();
            account.setPassword(registerDto.getPassword());
            account.setUsername(registerDto.getUsername());
            account.setRole(roleService.findByTitle
                    (RoleTitle.valueOf(registerDto.getRoleTitle()))
            );
            account.setStatus(statusService.findByTitle(StatusTitle.WAITING_FOR_VERIFY));
            account.setPersonalInfo(personalInfo);
            accountService.save(account);

            ConfirmationToken confirmationToken = new ConfirmationToken(account);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(registerDto.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("maeelyasian@gmail.com");
            mailMessage.setText(":برای فعال سازی اکانت خود روی لینک کلیک کنید "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

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
            // TODO DELETE TOKEN WORK?
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }


    @GetMapping(value = "/signIn")
    public String signIn() {
        return "sign-in";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "index";
    }


}
