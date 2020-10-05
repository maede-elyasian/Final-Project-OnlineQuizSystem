package ir.maktab.controller;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Message;
import ir.maktab.service.AccountService;
import ir.maktab.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/course")
public class CourseRestController {

    private CourseService courseService;
    private AccountService accountService;

    @Autowired
    public CourseRestController(CourseService courseService, AccountService accountService) {
        this.courseService = courseService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/{courseId}",produces = "application/json")
    public Course getCourseById(@PathVariable("courseId") Long courseId){
        return courseService.findById(courseId);
    }

    @PostMapping
    public ResponseEntity addCourse(@RequestBody Course course){
        try {
            courseService.save(course);
            return ResponseEntity.ok()
                    .body("course " + course.getCourseTitle() + "saved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }

    @GetMapping(value = "/courseStudents/{courseId}",produces = "application/json")
    public List<Account> getCourseStudents(@PathVariable("courseId")Long id){
         Course course = courseService.findById(id);
         List<Account> students = course.getStudents();
         return students;
    }

    @GetMapping(value = "/courseTeachers/{courseId}",produces = "application/json")
    public List<Account> getCourseTeachers(@PathVariable("courseId")Long id){
        Course course = courseService.findById(id);
        List<Account> teachers = course.getTeachers();
        return teachers;
    }

    @DeleteMapping(value = "/deleteCourse/{courseId}")
    public ResponseEntity deleteCourseById(@PathVariable("courseId") Long courseId){
        try {
            courseService.removeById(courseId);
           return ResponseEntity.ok().body("course deleted successfully");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("unable to delete course" + e.getStackTrace());
        }
    }

    @GetMapping(value = "/deleteMember/{courseId}")
    public ResponseEntity addMembersToCourse(@PathVariable("courseId") Long courseId,
                                             @RequestParam("memberId") Long memberId,
                                             @RequestParam("role") String role){
   try {
       Course course = courseService.findById(courseId);
       Account account = accountService.findById(memberId);
       if (role.equals("TEACHER") && course.getTeachers().contains(account) ){
           course.getTeachers().remove(account);
       }
       if (role.equals("STUDENT") && course.getTeachers().contains(account) ){
           course.getStudents().remove(account);
       }
       return ResponseEntity.ok().body("member removed successfully");
   }
   catch (Exception e){
       return ResponseEntity.badRequest().body("Unable To Remove Member From Course");
   }
    }
}
