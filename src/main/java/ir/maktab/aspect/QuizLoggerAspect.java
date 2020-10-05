package ir.maktab.aspect;

import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.Quiz;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QuizLoggerAspect {

    private static final Logger logger = LogManager.getLogger();

    @Pointcut("execution(* ir.maktab.service.QuizService.save(..))")
    public void saveNewQuiz() {
    }

    @After("(saveNewQuiz())")
    public void saveNewQuizAspect(JoinPoint joinPoint) {
        Quiz quiz = (Quiz) joinPoint.getArgs()[0];
        logger.info("** New Quiz With Id : \'" + quiz.getId() + " \' Saved **");
    }

    @Pointcut("execution(* ir.maktab.service.QuizService.removeQuizById(..))")
    public void removeQuiz() {
    }

    @After("(removeQuiz())")
    public void removeQuizAspect(JoinPoint joinPoint) {
        Long quizId = (Long) joinPoint.getArgs()[0];
        logger.info("** Quiz With Id : \'" + quizId + "\' removed **");
    }

    @Pointcut("execution(* ir.maktab.service.QuizService.activateQuiz(..))")
    public void activateQuiz() {
    }

    @After("(activateQuiz())")
    public void activateQuizAspect(JoinPoint joinPoint) {
        Quiz quiz = (Quiz) joinPoint.getArgs()[0];
        logger.info("** Quiz With Id : \'" + quiz + " \'Is Active ** ");
    }

    @Pointcut("execution(* ir.maktab.service.QuizService.inactivateQuiz(..))")
    public void inactivateQuiz() {
    }

    @After("inactivateQuiz()")
    public void inactivateQuizAspect(JoinPoint joinPoint) {
        Quiz quiz = (Quiz) joinPoint.getArgs()[0];
        logger.info("** Quiz With Id:\' " + quiz.getId() + "\' Is Inactive **");
    }

    @Pointcut("execution(* ir.maktab.service.QuizService.removeQuestion(..))")
    public void removeQuestion() {
    }

    @After("removeQuestion()")
    public void removeQuestionAspect(JoinPoint joinPoint) {
        Question question = (Question) joinPoint.getArgs()[0];
        Long quizId = (Long) joinPoint.getArgs()[1];
        logger.info("** Question With Id: \'" + question.getId() + "\'" +
                " Removed From Quiz With Id:\' " + "\'" + quizId + " **");
    }

    @Pointcut("execution(* ir.maktab.service.QuizService.addQuestion(..))")
    public void addQuestion() {
    }

    @After("addQuestion()")
    public void addQuestionAspect(JoinPoint joinPoint) {
        Question question = (Question) joinPoint.getArgs()[0];
        Quiz quiz = (Quiz) joinPoint.getArgs()[2];
        logger.info("** Question With Id:\'" + question.getId() + "\'" +
                " Added To Quiz With Id:\'" + quiz.getId() + "\'" + " **");
    }
}
