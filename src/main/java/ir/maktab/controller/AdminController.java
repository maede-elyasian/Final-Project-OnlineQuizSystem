package ir.maktab.controller;

import ir.maktab.dto.CourseDto;
import ir.maktab.dto.EditAccountDto;
import ir.maktab.dto.SearchAccountDto;
import ir.maktab.model.entity.*;
import ir.maktab.model.enums.LessonTitle;
import ir.maktab.model.enums.RoleTitle;
import ir.maktab.model.enums.StatusTitle;
import ir.maktab.repository.CourseRepository;
import ir.maktab.service.*;
import ir.maktab.utility.PersianDate;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private AccountService accountService;
    private PersonalInfoService personalInfoService;
    private StatusService statusService;
    private RoleService roleService;
    private CourseService courseService;
    private LessonService lessonService;

    @Autowired
    public AdminController(AccountService accountService, PersonalInfoService personalInfoService,
                           StatusService statusService, RoleService roleService,
                           CourseService courseService, LessonService lessonService) {
        this.accountService = accountService;
        this.personalInfoService = personalInfoService;
        this.statusService = statusService;
        this.roleService = roleService;
        this.courseService = courseService;
        this.lessonService = lessonService;
    }


    @RequestMapping(value = "/adminMenu")
    public String getAdminMenu(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("studentCounter",accountService.countByRole((long) 2));
        model.addAttribute("teacherCounter", accountService.countByRole((long) 3));
        model.addAttribute("courseCounter",courseService.countAll());

        return "admin-dashboard";
    }

    @GetMapping(value = "/allUsers")
    public String showUsers(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("search", new SearchAccountDto());
        return "accounts-list";
    }

    @PostMapping(value = "/search")
    public String searchUsers(@ModelAttribute("search") SearchAccountDto searchAccountDto, Model model) {

        List<Account> searchAccounts = accountService.findMaxMatch(searchAccountDto.getRole()
                , searchAccountDto.getStatus()
                , searchAccountDto.getFirstName(),
                searchAccountDto.getLastName(), searchAccountDto.getEmail(),
                searchAccountDto.getNationalCode());

        System.out.println(searchAccounts.toString());
        model.addAttribute("accounts", searchAccounts);
        model.addAttribute("search", new SearchAccountDto());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        System.out.println("**SEARCH FINISHED**");
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
        EditAccountDto editAccountDto = new EditAccountDto();
        editAccountDto.setId(account.getId());
        editAccountDto.setFirstName(account.getPersonalInfo().getFirstName());
        editAccountDto.setLastName(account.getPersonalInfo().getLastName());
        editAccountDto.setPhoneNumber(account.getPersonalInfo().getPhoneNumber());
        editAccountDto.setNationalCode(account.getPersonalInfo().getNationalCode());
        editAccountDto.setEmail(account.getPersonalInfo().getEmail());
        editAccountDto.setPassword(account.getPassword());
        editAccountDto.setRole(account.getRole().getTitle().name());
        editAccountDto.setUsername(account.getUsername());
        editAccountDto.setStatus(account.getStatus().getTitle().name());
        model.addAttribute("editAccountDto", editAccountDto);
        model.addAttribute("statuses", statusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "edit-account-page";
    }

    @PostMapping(value = "/editAccount/{id}")
    public String editAccount(@PathVariable("id") Long id,
                              @ModelAttribute("editAccountDto") EditAccountDto editAccountDto) {

        Account account = accountService.findById(id);
        PersonalInfo personalInfo = account.getPersonalInfo();
        personalInfo.setFirstName(editAccountDto.getFirstName());
        personalInfo.setLastName(editAccountDto.getLastName());
        personalInfo.setNationalCode(editAccountDto.getNationalCode());
        personalInfo.setPhoneNumber(editAccountDto.getNationalCode());
        personalInfo.setEmail(editAccountDto.getEmail());

        account.setPassword(editAccountDto.getPassword());
        account.setUsername(editAccountDto.getUsername());
        account.setRole(roleService.findByTitle(RoleTitle.valueOf(editAccountDto.getRole())));
        account.setStatus(statusService.findByTitle(StatusTitle.valueOf(editAccountDto.getStatus())));

        accountService.save(account);
        System.out.println("**ACCOUNT UPDATED SUCCESSFULLY**");

        return "redirect:/admin/allUsers";

    }

    @RequestMapping(value = "/addCourse")
    public String createCourse(Model model) {
        List<Course> courses = courseService.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        if (courses.size() > 0 && courses != null) {
            for (Course course : courses) {
                courseDtoList.add(new CourseDto(course.getId(),
                        course.getCourseTitle(),
                        String.valueOf(course.getLesson().getTitle())
                        , course.getStartDate(),
                        course.getFinishDate()));
            }
            model.addAttribute("allCourses", courseDtoList);
            model.addAttribute("courseDto", new CourseDto());
            model.addAttribute("allLessons", lessonService.findAll());
            return "add-course";
        } else {
            return "admin-dashboard";
        }

    }

    @PostMapping(value = "/addCourse")
    public String getAddCourse(@ModelAttribute("courseDto") CourseDto courseDto, Model model) {
        Course course = new Course();
        course.setCourseTitle(courseDto.getCourseTitle());
        course.setLesson(lessonService.findByTitle(LessonTitle.valueOf(courseDto.getLessonTitle())));
        course.setStartDate(courseDto.getStartDate());
        course.setFinishDate(courseDto.getFinishDate());
        courseService.save(course);
        System.out.println("**COURSE SAVED SUCCESSFULLY**");
        return "redirect:/admin/addCourse";
        //TODO CONVERT PERSIAN DATE,CHECK SAME DATE ERROR, GREATER THAN
    }

    @RequestMapping(value = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.removeById(id);
        return "redirect:/admin/addCourse";
    }

    @RequestMapping(value = "/editCourse/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.findById(id);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setCourseTitle(course.getCourseTitle());
        courseDto.setLessonTitle(course.getLesson().getTitle().name());
        courseDto.setStartDate(course.getStartDate());
        courseDto.setFinishDate(course.getFinishDate());
        model.addAttribute("courseDto", courseDto);
        model.addAttribute("allLessons", lessonService.findAll());
        return "edit-course";
    }

    @PostMapping(value = "/editCourse/{courseId}")
    public String editCourse(@PathVariable("courseId") Long courseId,
                             @ModelAttribute("courseDto") CourseDto courseDto) {
        Course course = courseService.findById(courseId);
        course.setCourseTitle(courseDto.getCourseTitle());
        course.setLesson(lessonService.findByTitle(LessonTitle.valueOf(courseDto.getLessonTitle())));
        course.setStartDate(courseDto.getStartDate());
        course.setFinishDate(courseDto.getFinishDate());
        courseService.save(course);
        System.out.println("**COURSE UPDATED SUCCESSFULLY**");
        return "redirect:/admin/addCourse";
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
        return "redirect:/admin/courseMembers/" + id;
    }

    @RequestMapping(value = "/addMemberToCourse/{courseId}")
    public String addMemberToCourse(@PathVariable("courseId") Long id,
                                    @RequestParam(name = "memberRole") String role,
                                    Model model) {
        Course course = courseService.findById(id);
        List<Account> accounts = accountService.findAll().stream()
                .filter(account -> account.getRole().equals(roleService.findByTitle(RoleTitle.valueOf(role))))
                .collect(Collectors.toList());
        System.out.println(accounts.toString());
        List<Account> requestedAccounts = new ArrayList<>();

        if (role.equals(RoleTitle.STUDENT.name())) {
            requestedAccounts = accounts.stream()
                    .filter(account -> !course.getStudents().contains(account))
                    .collect(Collectors.toList());
        }
        if (role.equals(RoleTitle.TEACHER.name())) {
            requestedAccounts = accounts.stream()
                    .filter(account -> !course.getTeachers().contains(account))
                    .collect(Collectors.toList());
        }

        System.out.println(requestedAccounts.toString());
        model.addAttribute("accounts", requestedAccounts);
        model.addAttribute("courseInfo", course);
        return "add-accounts-to-course";
    }

    @RequestMapping(value = "/addMembersToCourse/{courseId}", method = RequestMethod.POST)
    public String addMember(@PathVariable("courseId") Long id,
                            @RequestParam(name = "memberId") Long memberId,
                            @RequestParam(name = "role") String role) {
        System.out.println("course id :" + id);
        Course course = courseService.findById(id);
        Account account = accountService.findById(memberId);

        if (role.equals(RoleTitle.STUDENT.name())) {
            course.getStudents().add(account);
        }
        if (role.equals(RoleTitle.TEACHER.name())) {
            course.getTeachers().add(account);
            System.out.println(account.toString());
        }
        courseService.save(course);
        System.out.println("**SAVED MEMBER TO COURSE SUCCESSFULLY**");
        return "redirect:/admin/addMemberToCourse/" + id + "?memberRole=" + role + "&memberId="+memberId;
    }
}

