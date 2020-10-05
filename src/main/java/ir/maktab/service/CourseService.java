package ir.maktab.service;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Classification;
import ir.maktab.model.entity.Course;
import ir.maktab.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private ClassificationService classificationService;
    private AccountService accountService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         ClassificationService classificationService,
                         AccountService accountService) {
        this.courseRepository = courseRepository;
        this.classificationService = classificationService;
        this.accountService = accountService;
    }

    public Page<Course> findAll(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses;
    }

    public Course saveNewCourse(Course course) {
        Course updatingCourse = course;
        updatingCourse.setClassification(classificationService.findByTitle(course.getClassification().getTitle()));
        return courseRepository.save(updatingCourse);
    }

    public void updateById(Course course, Long courseId) {
        Course updatingCourse = findById(courseId);
        updatingCourse.setCourseTitle(course.getCourseTitle());
        course.setClassification(course.getClassification());
        course.setStartDate(course.getStartDate());
        course.setEndDate(course.getEndDate());
        save(updatingCourse);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void removeById(Long id) {
        courseRepository.deleteById(id);
    }

    public Course findById(Long id) {
        Optional<Course> found = courseRepository.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public int countAll() {
        return (int) courseRepository.count();
    }

    public Page<Course> getTeacherCourses(Account teacher, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
        Page<Course> courses = courseRepository.findByTeachersContains(teacher, pageable);
        return courses;
    }

    public Page<Course> getStudentCourses(Long studentId, int page) {
        Account account = accountService.findById(studentId);

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
        return courseRepository.findByStudentsContains(account, pageable);
    }


}
