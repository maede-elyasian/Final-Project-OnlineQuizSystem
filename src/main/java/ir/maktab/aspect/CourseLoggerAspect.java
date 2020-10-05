package ir.maktab.aspect;

import ir.maktab.model.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class CourseLoggerAspect {

    private static final Logger logger = LogManager.getLogger();

    @Pointcut("execution(* ir.maktab.service.CourseService.saveNewCourse(..))")
    public void addCourse(){}

    @After("addCourse()")
    public void addCourseAspect(JoinPoint joinPoint){
        Course course = (Course) joinPoint.getArgs()[0];
        logger.info("**Course with title: " + course.getCourseTitle() +
                " And Classification Title :" + course.getClassification().getTitle() + " Added successfully**");
    }

    @Pointcut("execution(* ir.maktab.service.CourseService.removeById(..))")
    public void deleteCourse(){}

    @After("deleteCourse()")
    public void deleteCourseAspect(JoinPoint joinPoint){
        Long courseId = (Long) joinPoint.getArgs()[0];
        logger.info("** Course with id: " + courseId + " deleted **");
    }

    @Pointcut("execution(* ir.maktab.service.CourseService.updateById(..))")
    public void editCourse(){}

    @After("editCourse()")
    public void editCourseAspect(JoinPoint joinPoint){
        Long courseId = (Long) joinPoint.getArgs()[1];
        logger.info("** Course with id: " + courseId + " updated **");
    }

    @Pointcut("execution(* ir.maktab.controller.CourseController.deleteCourseMember(..))")
    public void deleteCourseMember(){}

    @After("deleteCourseMember()")
    public void deleteCourseMemberAspect(JoinPoint joinPoint){
        Long courseId = (Long) joinPoint.getArgs()[0];
        logger.info("** Course with id: " + courseId + " was deleted successfully**");
    }

    @Pointcut("execution(* ir.maktab.controller.CourseController.addMember(..))")
    public void addMemebr(){}

    @After("addMemebr()")
    public void addMemberAspect(JoinPoint joinPoint){
        Long courseId = (Long) joinPoint.getArgs()[0];
        Long memberId = (Long) joinPoint.getArgs()[1];
        logger.info("**Member with id: " + memberId + " added to course with id: " + courseId + "**");
    }
}
