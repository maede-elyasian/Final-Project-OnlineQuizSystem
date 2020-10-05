package ir.maktab.controller;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.StudentAnswer;
import ir.maktab.service.AccountService;
import ir.maktab.service.CourseService;
import ir.maktab.service.QuestionService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    private AccountService accountService;
    private CourseService courseService;
    private QuizService quizService;
    private QuestionService questionService;

    @Autowired
    public StudentController(AccountService accountService, CourseService courseService,
                             QuizService quizService, QuestionService questionService) {
        this.accountService = accountService;
        this.courseService = courseService;
        this.quizService = quizService;
        this.questionService = questionService;
    }


    @GetMapping(value = "/profile/{studentId}")
    public String getStudentProfile(@PathVariable("studentId") Long studentId, Model model) {
        Account account = accountService.findById(studentId);
        model.addAttribute("account", account);
        return "student-pages/profile-page";
    }

    @GetMapping(value = "/{studentId}/exams/{courseId}")
    public String getExams(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("studentId", studentId);
        return "student-pages/quizzes-page";
    }

    @GetMapping(value = "/{studentId}/start-exam/{quizId}")
    public String startExam(@PathVariable("studentId") Long studentId,
                            @PathVariable("quizId") Long quizId, Model model) {
        model.addAttribute("studentId", studentId);
        model.addAttribute("quizId", quizId);
//        model.addAttribute("descriptiveQuestions", questionService.getDescriptiveQuestions(quizId));
//        model.addAttribute("multiChoiceQuestions", questionService.getMultipleChoiceQuestions(quizId));
        model.addAttribute("studentAnswer", new StudentAnswer());
        return "student-pages/quiz-page";
    }
}
