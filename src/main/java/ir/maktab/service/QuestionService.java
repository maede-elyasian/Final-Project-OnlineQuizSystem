package ir.maktab.service;

import ir.maktab.model.entity.*;
import ir.maktab.repository.DescriptiveQuestionRepository;
import ir.maktab.repository.MultipleChoiceQuestionRepository;
import ir.maktab.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.crypto.Des;

import java.util.*;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private QuizService quizService;
    private CourseService courseService;
    private DescriptiveQuestionRepository descriptiveRepository;
    private MultipleChoiceQuestionRepository multipleChoiceRepository;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuizService quizService, CourseService courseService,
                           DescriptiveQuestionRepository descriptiveQuestionRepository,
                           MultipleChoiceQuestionRepository multipleChoiceQuestionRepository) {
        this.questionRepository = questionRepository;
        this.quizService = quizService;
        this.courseService = courseService;
        this.descriptiveRepository = descriptiveQuestionRepository;
        this.multipleChoiceRepository = multipleChoiceQuestionRepository;
    }

    public Question findById(Long questionId) {
        Optional<Question> found = questionRepository.findById(questionId);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    @Transactional
    public Question saveDescriptiveQuestion(DescriptiveQuestion question, Long quizId, Double point, String addToBank) {
        Quiz updatingQuiz = quizService.findById(quizId);

        question.setCourse(updatingQuiz.getCourse());
        question.setCreator(updatingQuiz.getTeacher());
        question.setMultipleChoice(false);
        question.getQuizList().add(updatingQuiz);

        Question savingQuestion = descriptiveRepository.save(question);
        quizService.addQuestion(savingQuestion, point, updatingQuiz);

        addQuestionToBank(savingQuestion, updatingQuiz, addToBank);
        return savingQuestion;
    }

    @Transactional
    public Question saveMultipleChoice(MultipleChoiceQuestion question, String choices,
                                       Double point, Long quizId, String addToBAnk) {
        Quiz updatingQuiz = quizService.findById(quizId);

        question.setCourse(updatingQuiz.getCourse());
        question.setCreator(updatingQuiz.getTeacher());
        question.setMultipleChoice(true);
        question.getQuizList().add(updatingQuiz);

        List<String> allChoices = Arrays.asList(choices.split(","));

        allChoices.stream().map(Answer::new).forEach(answer -> question.getOptions().add(answer));
//        for (String answer : allChoices) {
//            question.getOptions().add(new Answer(answer));
//        }

        Question savingQuestion = multipleChoiceRepository.save(question);
        quizService.addQuestion(question, point, updatingQuiz);

        addQuestionToBank(savingQuestion, updatingQuiz, addToBAnk);
        return savingQuestion;
    }

    public void addQuestionToBank(Question question, Quiz quiz, String addToBank) {
        Course course = quiz.getCourse();
        Classification classification = course.getClassification();

        if (addToBank.equals("Yes")) {
            classification.getBankQuestions().add(question);
            courseService.save(course);
        }
    }

    public Question update(Question question, Long quizId, Double point) {
        Question updatingQuestion = findById(question.getId());
        updatingQuestion.setContent(question.getContent());
        updatingQuestion.setTitle(question.getTitle());

        Quiz quiz = quizService.findById(quizId);
        quiz.getQuestions().replace(updatingQuestion, point);
        quizService.save(quiz);
        return questionRepository.save(updatingQuestion);
    }

    public void addQuestionFromBank(Long quizId, Long questionId) {
        Quiz quiz = quizService.findById(quizId);
        Question question = findById(questionId);
        Map<Question, Double> questionsMap = quiz.getQuestions();
        questionsMap.put(question, 0.0);
        quizService.save(quiz);
    }

    public Set<Question> getQuestionBank(Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        Classification classification = quiz.getCourse().getClassification();
        Set<Question> questions = classification.getBankQuestions();
        return questions;
    }

    public List<Question> getQuizQuestions(Long quizId) {
        return questionRepository.findByQuizListContains(quizService.findById(quizId));
    }

    public Page<MultipleChoiceQuestion> getMultipleChoiceQuestions(Long quizId,int offset) {
        Pageable pageable = PageRequest.of(offset,1);
        Quiz quiz = quizService.findById(quizId);
        Page<MultipleChoiceQuestion> questions = multipleChoiceRepository.findByQuizListContains(quiz,pageable);
        return questions;
    }

    public Page<DescriptiveQuestion> getDescriptiveQuestions(Long quizId,int offset) {
        Pageable pageable = PageRequest.of(offset,1);
        Quiz quiz = quizService.findById(quizId);
        Page<DescriptiveQuestion> questions = descriptiveRepository.findByQuizListContains(quiz,pageable);
        return questions;
    }

//    public Page<Question> getQuestionBank(Long quizId, int page) {
//        Quiz quiz = quizService.findById(quizId);
//        Classification classification = quiz.getCourse().getClassification();
//
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("title"));
//        Page<Question> questions = questionRepository
//                .findByCourse_Classification(classification, pageable);
//        return questions;
//    }

}
