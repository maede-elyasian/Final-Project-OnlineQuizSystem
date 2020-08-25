package ir.maktab.service;

import ir.maktab.model.entity.Course;
import ir.maktab.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        if (!CollectionUtils.isEmpty(courses)) {
            return courses;
        }
        return null;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void removeById(Long id){
        courseRepository.deleteById(id);
    }

    public Course findById(Long id){
        Optional<Course> found = courseRepository.findById(id);
        if (found.isPresent()){
            return found.get();
        }
        return null;
    }

    public int countAll(){
        return (int) courseRepository.count();
    }


}
