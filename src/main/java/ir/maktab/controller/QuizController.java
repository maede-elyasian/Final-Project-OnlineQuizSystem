package ir.maktab.controller;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Quiz;
import ir.maktab.service.AccountService;
import ir.maktab.service.CourseService;
import ir.maktab.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/quiz")
public class QuizController {

    static final int pageSize = 5;
    private QuizService quizService;
    private CourseService courseService;
    private AccountService accountService;

    @Autowired
    public QuizController(QuizService quizService, CourseService courseService, AccountService accountService) {
        this.quizService = quizService;
        this.courseService = courseService;
        this.accountService = accountService;
    }
//
//    @GetMapping(value = "/quizzes/{courseId}")
//    public String getAllQuizzes(@PathVariable("courseId") Long courseId, @RequestParam(value = "page",defaultValue = "1") int page, Model model) {
//        Pageable pageable = PageRequest.of(page-1, pageSize);
//        Page<Quiz> quizzes = quizService.getQuizzesOfCourse(courseId, pageable);
//        int totalPages = quizzes.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("activeAccountList", true);
//        model.addAttribute("quizList", quizzes.getContent());
//        Account account = accountService.getCurrentAccount();
//        model.addAttribute("teacherAccount", account);
//        model.addAttribute("quiz",new Quiz());
//        model.addAttribute("courseId", courseId);
//        return "teacher-pages/quiz-page";
//    }

    @GetMapping(value = "/quizzes/{courseId}")
    public String getAllQuizzes(@PathVariable("courseId") Long courseId, Model model,HttpServletRequest request) {
        Account account = accountService.getCurrentAccount();
        int page = 0;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        model.addAttribute("quizList", quizService.getAllQuizzes(courseId,page));
        model.addAttribute("teacherAccount", account);
        model.addAttribute("courseId", courseId);
        return "teacher-pages/quiz-page";
    }

//    @GetMapping(value = "/myQuizzes/{courseId}")
//    public String getMyQuizzes(@PathVariable("courseId") Long courseId, Model model) {
//        Account account = accountService.getCurrentAccount();
//        Course course = courseService.findById(courseId);
//        List<Quiz> quizList = course.getQuizzes().stream().
//                filter(quiz -> quiz.getTeacher().getId().equals(account.getId())).collect(Collectors.toList());
//        model.addAttribute("quizList", quizList);
//        model.addAttribute("quiz", new Quiz());
//        model.addAttribute("teacherAccount", account);
//        return "teacher-pages/quiz-page";
//    }

    @GetMapping(value = "/{courseId}/createQuiz")
    public String sendCreateQuizForm(@PathVariable("courseId") Long courseId, Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherAccount", accountService.getCurrentAccount());
        model.addAttribute("courseId",courseId);
        return "teacher-pages/add-new-quiz";
    }

    @PostMapping(value = "/{courseId}/createQuiz")
    public String createQuiz(@PathVariable("courseId") Long courseId, HttpServletRequest request,
                             @ModelAttribute("quiz") Quiz quiz, Model model) {
        Long teacherId = Long.parseLong(request.getParameter("teacherId"));
        String start = request.getParameter("start");
        quizService.saveNewQuiz(quiz, courseId, teacherId,start);
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherAccount", accountService.getCurrentAccount());
        return "redirect:/quiz/quizzes/" + courseId;
    }


    //    @GetMapping(value = "/{courseId}/deleteQuiz/{quizId}")
//    public String removeQuiz(@PathVariable("quizId") Long quizId,
//                             @PathVariable("courseId") Long courseId,
//                             Model model) {
//        Quiz quiz = quizService.findById(quizId);
//        Course course = courseService.findById(courseId);
//        course.getQuizzes().remove(quiz);
//        quizService.removeQuizById(quizId);
//
//        List<Quiz> requestedCourseQuizzes = courseService.findById(courseId).getQuizzes();
//        model.addAttribute("quizList", requestedCourseQuizzes);
//        model.addAttribute("quiz", new Quiz());
//        model.addAttribute("teacherAccount", accountService.getCurrentAccount());
//        return "redirect:/quiz/quizzes/" + courseId;
//
//    }
    @GetMapping(value = "/{courseId}/editQuiz/{quizId}")
    public String editQuiz(@PathVariable("quizId") Long quizId,
                           @PathVariable("courseId") Long courseId,
                           Model model) {

        model.addAttribute("quiz", quizService.findById(quizId));
        model.addAttribute("teacherAccount",accountService.getCurrentAccount());
        return "add-new-quiz";
    }


//    @GetMapping(value = "/{courseId}/editQuiz/{quizId}")
//    public String editQuiz(@PathVariable("quizId") Long quizId,
//                           @PathVariable("courseId") Long courseId,
//                           Model model) {
//        Account currentAccount = accountService.getCurrentAccount();
//        List<Quiz> requestedCourseQuizzes = courseService.findById(courseId).getQuizzes();
//        model.addAttribute("quizList", requestedCourseQuizzes);
//        model.addAttribute("quiz", quizService.findById(quizId));
//        model.addAttribute("teacherAccount", currentAccount);
//        return "teacher-pages/quiz-page";
//    }

}
