package ir.maktab.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminLoggerAspect {

    private static final Logger logger = LogManager.getLogger();

    @Pointcut("execution(* ir.maktab.controller.AdminController.submitEditAccount(..))")
    public void editAccount(){
    }

    @After("editAccount()")
    public void activateAccountAspect(JoinPoint joinPoint){
        Long userId = (Long) joinPoint.getArgs()[0];
        logger.info("user with id: " + userId + " has updated successfully");
    }


}
