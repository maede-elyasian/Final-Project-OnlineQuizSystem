package ir.maktab.service;

import ir.maktab.model.entity.Classification;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.Quiz;
import ir.maktab.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private CourseService courseService;
    private AccountService accountService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public QuizService(QuizRepository quizRepository, CourseService courseService, AccountService accountService) {
        this.quizRepository = quizRepository;
        this.courseService = courseService;
        this.accountService = accountService;
    }

    public Page<Quiz> getAllQuizzes(Long courseId,int page) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE, Sort.by("id"));
        return quizRepository.findByCourse_Id(courseId, pageable);
    }


    @Transactional
    public String saveNewQuiz(Quiz quiz, Long courseId, Long teacherId, String start) {
        String result = "";
//        LocalDateTime startDate = LocalDateTime.parse(start.replace('T', ' '));
//        String newBegin = start.replace('T',' ');
//        System.out.println(newBegin + " *****************************************");
//        LocalDateTime begin = LocalDateTime.parse(newBegin, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        System.out.println(begin + " LocalDAte START");

//        if (quiz.getStartDate().equals(quiz.getEndDate())) {
//            result = "Start Date and Finish Date are equal to each other";
//        }
//        if (quiz.getFinishDate().before(quiz.getStartDate())) {
//            result = "End Date Can not be before start date";
//         else {
        Course updatingCourse = courseService.findById(courseId);
        quiz.setTeacher(accountService.findById(teacherId));
//        quiz.setStartDate(startDate);
        quiz.setCourse(updatingCourse);
        updatingCourse.getQuizzes().add(quiz);
        save(quiz);
//        }
        return result;
    }


    @Transactional
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz update(Long quizId, Quiz quiz) {
        Quiz updatingQuiz = findById(quizId);
        updatingQuiz.setTitle(quiz.getTitle());
        updatingQuiz.setStartDate(quiz.getStartDate());
        updatingQuiz.setEndDate(quiz.getEndDate());
        updatingQuiz.setTime(quiz.getTime());
        updatingQuiz.setDescription(quiz.getDescription());
        return save(updatingQuiz);
    }

    public void removeQuizById(Long id) {
        quizRepository.deleteById(id);
    }

    public Quiz stopQuiz(Long quizId) {
        Quiz updatingQuiz = findById(quizId);
        updatingQuiz.setStop(true);
        return save(updatingQuiz);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Quiz findById(Long id) {
        Optional<Quiz> found = quizRepository.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public void removeQuestion(Question question, Long quizId) {
        Quiz quiz = findById(quizId);
        quiz.getQuestions().remove(question);
        save(quiz);
    }

    public void addQuestion(Question question, Double point, Quiz quiz) {
        Quiz updatingQuiz = quiz;
        Map<Question, Double> questionMap = updatingQuiz.getQuestions();
        questionMap.put(question, point);
        save(updatingQuiz);
    }

    public Double getMaxPoint(Long quizId) {
        Quiz quiz = findById(quizId);
        return quiz.getQuestions().values().stream().reduce(0.0, Double::sum);
    }

    public Set<Question> getBankQuestions(Long quizId, int page) {
        Quiz quiz = findById(quizId);
        Classification classification = quiz.getCourse().getClassification();
        return classification.getBankQuestions();
    }

    public Map<Question, Double> getQuestions(Long quizId) {
        Quiz quiz = findById(quizId);
        return quiz.getQuestions();
    }

    public int getQuizTime(Long quizId) {
        Quiz quiz = findById(quizId);
        return quiz.getTime();
    }

}
