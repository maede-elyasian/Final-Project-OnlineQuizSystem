package ir.maktab.controller;

import ir.maktab.dto.QuizResponse;
import ir.maktab.model.entity.*;
import ir.maktab.service.CourseService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/quiz")
public class QuizRestController {

    private CourseService courseService;
    private QuizService quizService;

    @Autowired
    public QuizRestController(CourseService courseService, QuizService quizService) {
        this.courseService = courseService;
        this.quizService = quizService;
    }

    @GetMapping("/getQuizzes")
    public QuizResponse retrieveCustomer(@RequestParam(value = "courseId") Long courseId,
                                         @Param(value = "page") int page) {
        Page<Quiz> quizzes = quizService.getAllQuizzes(courseId, page);
        QuizResponse response = new QuizResponse(quizzes.getContent(), quizzes.getTotalPages(),
                quizzes.getNumber(), quizzes.getSize());
        return response;
    }

    @DeleteMapping(value = "/{courseId}/deleteQuiz/{quizId}")
    public ResponseEntity removeQuiz(@PathVariable("courseId") Long courseId, @PathVariable("quizId") Long quizId) {
        try {
            System.out.println("here we are");
            Quiz quiz = quizService.findById(quizId);
            Course course = courseService.findById(courseId);
            course.getQuizzes().remove(quiz);
            quizService.removeQuizById(quizId);
            return ResponseEntity.ok().body("Quiz removed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unable to remove quiz with id: " + quizId);
        }
    }

    @GetMapping("/search/{id}")
    public Quiz getCustomerById(@PathVariable long id) {
        try {
            Quiz quiz = quizService.findById(id);
            return quiz;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public Quiz updateCustomerById(@RequestBody Quiz quiz,
                                   @PathVariable long id) {
        try {
            Quiz updated = quizService.update(id, quiz);
            return updated;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to update Quiz with id: " + id);
            return null;
        }
    }

    @PutMapping("/stop/{quizId}")
    public Quiz stopQuiz(@PathVariable("quizId") Long quizId) {
        try {
            return quizService.stopQuiz(quizId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to stop quiz with Id: " + quizId);
            return null;
        }
    }

    @GetMapping(value = "/totalPoint/{quizId}")
    public Double getMaxPoint(@PathVariable("quizId")Long quizId){
        return quizService.getMaxPoint(quizId);
    }

    @GetMapping(value = "/get-time/{quizId}")
    public int getQuizTime(@PathVariable("quizId")Long quizId){
     return quizService.getQuizTime(quizId);
    }

}

