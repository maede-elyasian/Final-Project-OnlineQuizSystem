package ir.maktab.aspect;

import ir.maktab.dto.LoginDto;
import ir.maktab.dto.RegisterDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HomeLoggerAspect {
    private static final Logger logger = LogManager.getLogger();

    @Pointcut("execution(* ir.maktab.service.AccountService.registerNewUser(..))")
    public void userRegister(){}

    @After("(userRegister())")
    public void userRegisterAspect(JoinPoint joinPoint){
        RegisterDto registerDto = (RegisterDto) joinPoint.getArgs()[0];
        logger.info("**User with username = " + registerDto.getUsername() + "  has registered**");
    }

}
