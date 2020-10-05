package ir.maktab.controller;

import ir.maktab.service.AccountService;
import ir.maktab.service.CourseService;
import ir.maktab.service.StatusService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    private AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "sign-in-page";
    }

    @RequestMapping(value = "/menu")
    public ModelAndView getMenuPage(Principal principal) {
        List<String> authoritiesNames = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(authority -> authority.toString()).collect(Collectors.toList());
        String role = authoritiesNames.get(0);
        ModelAndView modelAndView;

        switch (role) {
            case "ADMIN":
                modelAndView = new ModelAndView("redirect:/admin/adminMenu");
                break;

            case "TEACHER":
                modelAndView = new ModelAndView("redirect:/teacher/courses");
                break;

            case "STUDENT":
                modelAndView = new ModelAndView("student-pages/dashboard");
                modelAndView.addObject("student", accountService.findByUsername(principal.getName()));
                break;

            default:
                modelAndView = new ModelAndView("error-page");
                modelAndView.addObject("message", "Unknown authority!!");
        }
        return modelAndView;
    }
}
