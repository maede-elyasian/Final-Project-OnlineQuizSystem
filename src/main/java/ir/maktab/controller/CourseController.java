package ir.maktab.controller;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Course;
import ir.maktab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
    private AccountService accountService;
    private RoleService roleService;
    private CourseService courseService;
    private ClassificationService classificationService;

    @Autowired
    public CourseController(AccountService accountService, RoleService roleService,
                            CourseService courseService, ClassificationService classificationService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.courseService = courseService;
        this.classificationService = classificationService;
    }

    @RequestMapping(value = "/courses-page")
    public String createCourse(Model model, HttpServletRequest request) {
        int page = 0;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        model.addAttribute("allCourses", courseService.findAll(page));
        return "courses-page";
    }

    @GetMapping(value = "/add-new-course")
    public String submitAddCourse(Model model) {
        model.addAttribute("course",new Course());
        model.addAttribute("classifications", classificationService.findAll());
        return "add-new-course-page";
    }

    @PostMapping(value = "/addCourse")
    public String submitAddCourse(@ModelAttribute("course") Course course) {
        courseService.saveNewCourse(course);
        return "redirect:/course/courses-page";
    }

    @RequestMapping(value = "/editCourse/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("allLessons", classificationService.findAll());
        return "edit-course";
    }

    @PostMapping(value = "/editCourse/{courseId}")
    public String editCoursePost(@PathVariable("courseId") Long courseId,
                                 @ModelAttribute("course") Course course) {
        courseService.updateById(course, courseId);
        return "redirect:/course/addCourse";
    }

    @RequestMapping(value = "/courseMembers/{courseId}")
    public String courseMembers(@PathVariable("courseId") Long courseId, Model model) {
        model.addAttribute("courseInfo", courseService.findById(courseId));
        model.addAttribute("roles", roleService.findAll());
        return "course-members";
    }

    @RequestMapping(value = "/deleteCourseMember/{courseId}")
    public String deleteCourseMember(@PathVariable("courseId") Long id,
                                     @RequestParam(name = "memberId") Long memberId,
                                     @RequestParam(name = "memberRole") String role) {
        Course course = courseService.findById(id);
        Account account = accountService.findById(memberId);
        if (role.equals("TEACHER") && course.getTeachers().contains(account)) {
            course.getTeachers().remove(account);
        } else if (role.equals("STUDENT") && course.getStudents().contains(account)) {
            course.getStudents().remove(account);
        }
        courseService.save(course);
        return "redirect:/course/courseMembers/" + id;
    }

    @RequestMapping(value = "/addMemberToCourse/{courseId}")
    public String addMemberToCourse(@PathVariable("courseId") Long id,
                                    @RequestParam(name = "memberRole") String role,
                                    Model model) {
        Course course = courseService.findById(id);
        List<Account> accounts = accountService.findAll().stream()
                .filter(account -> account.getRole().equals(roleService.findByTitle(role)))
                .collect(Collectors.toList());
        List<Account> requestedAccounts = new ArrayList<>();

        if (role.equals("STUDENT")) {
            requestedAccounts = accounts.stream()
                    .filter(account -> !course.getStudents().contains(account))
                    .collect(Collectors.toList());
        }
        if (role.equals("TEACHER")) {
            requestedAccounts = accounts.stream()
                    .filter(account -> !course.getTeachers().contains(account))
                    .collect(Collectors.toList());
        }

        model.addAttribute("accounts", requestedAccounts);
        model.addAttribute("courseInfo", course);
        model.addAttribute("role", role);
        return "add-member-to-course";
    }

    @RequestMapping(value = "/addMembersToCourse/{courseId}", method = RequestMethod.POST)
    public String addMember(@PathVariable("courseId") Long id,
                            @RequestParam(name = "memberId") Long memberId,
                            @RequestParam(name = "role") String role) {
        System.out.println("course id :" + id);
        Course course = courseService.findById(id);
        Account account = accountService.findById(memberId);

        if (role.equals("STUDENT")) {
            course.getStudents().add(account);
        }
        if (role.equals("TEACHER")) {
            course.getTeachers().add(account);
            System.out.println(account.toString());
        }
        courseService.save(course);

        return "redirect:/course/addMemberToCourse/" + id + "?memberRole=" + role + "&memberId=" + memberId;
    }


}
