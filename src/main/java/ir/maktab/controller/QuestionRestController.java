package ir.maktab.controller;

import ir.maktab.dto.DescriptiveDto;
import ir.maktab.dto.QuizOperationDto;
import ir.maktab.model.entity.DescriptiveQuestion;
import ir.maktab.model.entity.MultipleChoiceQuestion;
import ir.maktab.model.entity.Question;
import ir.maktab.service.QuestionService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/question")
public class QuestionRestController {
    private QuestionService questionService;
    private QuizService quizService;

    @Autowired
    public QuestionRestController(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
    }


    @DeleteMapping(value = "/{questionId}/remove/{quizId}")
    public ResponseEntity removeQuestion(@PathVariable("questionId") Long questionId,
                                         @PathVariable("quizId") Long quizId) {
        try {
            quizService.removeQuestion(questionService.findById(questionId), quizId);
            return ResponseEntity.ok().body("Question removed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unable to remove question!");
        }
    }

    @GetMapping(value = "/getQuestions/{quizId}")
    public Map<Question, Double> getQuestions(@PathVariable("quizId") Long quizId) {
        return quizService.getQuestions(quizId);
    }

    @PutMapping(value = "/edit/{quizId}")
    public String editQuestion(@PathVariable("quizId") Long quizId,
                               @RequestBody Question question,
                               @RequestParam("point") Double point) {
        try {
            questionService.update(question, quizId, point);
            return "question updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "unable to update question!";
        }
    }

//    @GetMapping("/start/{quizId}")
//    public QuizOperationDto startQuiz(@Param("offset") int offset, @PathVariable("quizId") Long quizId) {
//        Page<MultipleChoiceQuestion> multiList = questionService.getMultipleChoiceQuestions(quizId, offset);
//        Page<DescriptiveQuestion> descriptive = questionService.getDescriptiveQuestions(quizId, offset);
//        return new QuizOperationDto(multiList.getContent(), descriptive.getContent(),descriptive.getTotalElements());
//    }

    @GetMapping(value = "/descriptive/{quizId}")
    public DescriptiveDto getDescriptive(@Param("offset")int offset, @PathVariable("quizId")Long quizId){
        Page<DescriptiveQuestion> descriptive = questionService.getDescriptiveQuestions(quizId, offset);
         return new DescriptiveDto(descriptive.getContent(),descriptive.getTotalPages());
    }
}
