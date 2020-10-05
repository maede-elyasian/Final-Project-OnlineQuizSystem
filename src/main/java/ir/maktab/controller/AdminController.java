package ir.maktab.controller;

import ir.maktab.dto.SearchAccountDto;
import ir.maktab.model.entity.*;
import ir.maktab.model.enums.StatusTitle;
import ir.maktab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private AccountService accountService;
    private StatusService statusService;
    private RoleService roleService;
    private CourseService courseService;

    @Autowired
    public AdminController(AccountService accountService,
                           StatusService statusService, RoleService roleService,
                           CourseService courseService) {
        this.accountService = accountService;
        this.statusService = statusService;
        this.roleService = roleService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/adminMenu")
    public String getAdminMenu(Model model,HttpServletRequest request) {
        int page = 0;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        model.addAttribute("accounts", accountService.getRecentUsers(page));
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("studentCounter", accountService.countByRole((long) 2));
        model.addAttribute("teacherCounter", accountService.countByRole((long) 3));
        model.addAttribute("courseCounter", courseService.countAll());

        return "admin-dashboard";
    }

    @GetMapping(value = "/allUsers")
    public String showUsers(HttpServletRequest request, Model model) {
        int page = 0;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        model.addAttribute("accounts", accountService.findAll(page));
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("search", new SearchAccountDto());
        return "accounts-list";
    }

    @PostMapping(value = "/search")
    public String searchUsers(@ModelAttribute("search") SearchAccountDto searchAccountDto,
                              Model model, HttpServletRequest request) {
        int page = 0;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        Page<Account> searchAccounts = accountService.findMaxMatch(searchAccountDto.getRole()
                , searchAccountDto.getStatus()
                , searchAccountDto.getFirstName(),
                searchAccountDto.getLastName(), searchAccountDto.getEmail(), page);

        model.addAttribute("accounts", searchAccounts);
        model.addAttribute("search", new SearchAccountDto());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        return "accounts-list";
    }

    @RequestMapping(value = "/accountActivation/{accountId}")
    public String activateAccount(@PathVariable("accountId") long id) {

        if (accountService.findById(id).getStatus().getTitle() != StatusTitle.ACTIVE) {
            accountService.activateAccount(id);
        } else {
            accountService.inActivateAccount(id);
        }
        return "redirect:/admin/allUsers";
    }

    @RequestMapping(value = "/editAccount/{accountId}")
    public String editAccount(@PathVariable("accountId") long id, Model model) {
        Account account = accountService.findById(id);
//        EditAccountDto editAccountDto = new EditAccountDto();
//        editAccountDto.setId(account.getId());
//        editAccountDto.setFirstName(account.getPersonalInfo().getFirstName());
//        editAccountDto.setLastName(account.getPersonalInfo().getLastName());
//        editAccountDto.setPhoneNumber(account.getPersonalInfo().getPhoneNumber());
//        editAccountDto.setEmail(account.getPersonalInfo().getEmail());
//        editAccountDto.setRole(account.getRole().getTitle());
//        editAccountDto.setUsername(account.getUsername());
//        editAccountDto.setStatus(account.getStatus().getTitle().name());
        model.addAttribute("account", account);
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "edit-account-page";
    }

    @PostMapping(value = "/editAccount/{id}")
    public String submitEditAccount(@PathVariable("id") Long id,
                                    @ModelAttribute("editAccountDto") Account account) {

        accountService.updateAccount(account);
        return "redirect:/admin/allUsers";

    }
}

