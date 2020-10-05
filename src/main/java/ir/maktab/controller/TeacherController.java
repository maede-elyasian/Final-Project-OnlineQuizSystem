package ir.maktab.controller;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Course;
import ir.maktab.service.AccountService;
import ir.maktab.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
    private CourseService courseService;
    private AccountService accountService;

    @Autowired
    public TeacherController(CourseService courseService, AccountService accountService) {
        this.courseService = courseService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/dashboard")
    public String getTeacherDashboard() {
        return "teacher-pages/dashboard";
    }

    @RequestMapping(value = "/courses")
    public String getTeacherMenu(Model model, HttpServletRequest request) {
        int page = 0;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        Account account = accountService.getCurrentAccount();
        Page<Course> teacherCourses = courseService.getTeacherCourses(account, page);

        model.addAttribute("teacherCourses", teacherCourses);
        model.addAttribute("teacherAccount", account);
        return "teacher-pages/dashboard";

    }
}
