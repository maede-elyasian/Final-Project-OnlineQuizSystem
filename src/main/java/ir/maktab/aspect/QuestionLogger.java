package ir.maktab.aspect;

import ir.maktab.model.entity.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QuestionLogger {
    private static final Logger logger = LogManager.getLogger();

    @Pointcut("execution(* ir.maktab.service.QuestionService.update(..))")
    public void updateQuestion() {
    }

    @After("updateQuestion()")
    public void updateQuestionAspect(JoinPoint joinPoint) {
        Question question = (Question) joinPoint.getArgs()[0];
        logger.info("** Question with id: \'" + question.getId() + "\' Updated **");
    }

    @Pointcut("execution(* ir.maktab.service.QuestionService.saveMultipleChoice(..))")
    public void addMultipleChoice(){}

    @After("addMultipleChoice()")
    public void addMultipleChoiceAspect(JoinPoint joinPoint){
        Question question = (Question) joinPoint.getArgs()[0];
        logger.info("** Multiple Choice Question With Id:\' " + question.getId() + "\' Saved **");
    }


}
