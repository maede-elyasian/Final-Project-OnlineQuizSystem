package ir.maktab.controller;

import ir.maktab.dto.CourseDto;
import ir.maktab.model.entity.Course;
import ir.maktab.service.CourseService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student")
public class StudentRestController {
    private CourseService courseService;
    private QuizService quizService;

    @Autowired
    public StudentRestController(CourseService courseService, QuizService quizService) {
        this.courseService = courseService;
        this.quizService = quizService;
    }

    @GetMapping(value = "/courses/{studentId}")
    public CourseDto findStudentCourses(@PathVariable("studentId") Long studentId, @Param(value = "page") int page) {
        Page<Course> courses = courseService.getStudentCourses(studentId, page);
        CourseDto courseResponse = new CourseDto(courses.getContent(),
                courses.getTotalPages(), courses.getNumber(), courses.getSize());
        return courseResponse;
    }
}
