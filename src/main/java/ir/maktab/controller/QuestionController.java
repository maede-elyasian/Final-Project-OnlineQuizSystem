package ir.maktab.controller;

import ir.maktab.model.entity.*;
import ir.maktab.service.AccountService;
import ir.maktab.service.QuestionService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/question")
public class QuestionController {
    private QuestionService questionService;
    private QuizService quizService;

    @Autowired
    public QuestionController(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
    }

    @RequestMapping(value = "/{quizId}/questionPage")
    public String getQuestionPage(@PathVariable("quizId") Long quizId, Model model) {
//        model.addAttribute("questions", quizService.getQuestions(quizId));
//        model.addAttribute("maxPoint", quizService.getMaxPoint(quizId));
        model.addAttribute("quizId", quizId);
        model.addAttribute("courseId", quizService.findById(quizId).getCourse());
        return "teacher-pages/questions-page";
    }

    @GetMapping(value = "/addDescriptive/{quizId}")
    public String sendDetailedQuestionForm(@PathVariable("quizId") Long quizId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("question", new Question());
        return "teacher-pages/add-descriptive-page";
    }

    @PostMapping(value = "/addDescriptiveQuestion/{quizId}")
    public String submitDetailedQuestion(@PathVariable("quizId") Long quizId,
                                         @ModelAttribute("question") DescriptiveQuestion descriptiveQuestion,
                                         HttpServletRequest request) {
        Double point = Double.valueOf(request.getParameter("point"));
        String addToBank = request.getParameter("addToBank");
        System.out.println("ADD TO BANK IN CONTROLLER " + addToBank);
        questionService.saveDescriptiveQuestion(descriptiveQuestion, quizId, point,addToBank);

        return "redirect:/question/" + quizId + "/questionPage";
    }

    @GetMapping(value = "/addMultiple/{quizId}")
    public String sendMultipleForm(@PathVariable("quizId") Long quizId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("question", new MultipleChoiceQuestion());
        return "teacher-pages/add-multipleChoice-page";
    }

    @PostMapping(value = "/addMultipleChoice/{quizId}")
    public String submitMultipleForm(@PathVariable("quizId") Long quizId,
                                     @ModelAttribute("question") MultipleChoiceQuestion question,
                                     HttpServletRequest request) {
        String choice = request.getParameter("allOptions");
        String addToBank = request.getParameter("addToBank");
        Double point = Double.parseDouble(request.getParameter("point"));
        questionService.saveMultipleChoice(question, choice, point, quizId,addToBank);
        return "redirect:/question/" + quizId + "/questionPage";
    }

//    @GetMapping(value = "/{questionId}/edit/{quizId}")
//    public String editQuestion(@PathVariable("questionId") Long questionId,
//                               @PathVariable("quizId") Long quizId, Model model) {
//        Question question = questionService.findById(questionId);
//        if (question instanceof DescriptiveQuestion) {
//            model.addAttribute("question", question);
//            return "teacher-pages/add-descriptive-page";
//        } else if (question instanceof MultipleChoiceQuestion) {
//            model.addAttribute("question", question);
//            return "teacher-pages/add-multipleChoice-page";
//        }
//        return "/question/" + quizId + "/questionPage";
//    }

//    @GetMapping(value = "/{questionId}/remove/{quizId}")
//    public String removeQuestion(@PathVariable("questionId") Long questionId,
//                                 @PathVariable("quizId") Long quizId) {
//        quizService.removeQuestion(questionService.findById(questionId), quizId);
//        return "redirect:/question/" + quizId + "/questionPage";
//    }

    @GetMapping(value = "/{quizId}/addFromBank")
    public String addQuestionFromBank(@PathVariable("quizId") Long quizId, Model model) {
        model.addAttribute("questions", questionService.getQuestionBank(quizId));
        model.addAttribute("quizId", quizId);
        return "teacher-pages/question-bank-page";
    }
//    *****GET QUESTIN FROM BANK BY PAGINATION
//    @GetMapping(value = "/{quizId}/addFromBank")
//    public String addQuestionFromBank(@PathVariable("quizId") Long quizId, HttpServletRequest request, Model model) {
//        int page = 0;
//
//        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
//            page = Integer.parseInt(request.getParameter("page")) - 1;
//        }
//        model.addAttribute("questions", questionService.getQuestionBank(quizId, page));
//        model.addAttribute("quizId", quizId);
//        return "teacher-pages/question-bank-page";
//    }

    @GetMapping(value = "/addToQuiz/{quizId}/{questionId}")
    public String addQuestionToQuiz(@PathVariable("quizId") Long quizId,
                                    @PathVariable("questionId") Long questionId) {
        questionService.addQuestionFromBank(quizId, questionId);
        return "redirect:/question/addFromBank/" + quizId;
    }

}