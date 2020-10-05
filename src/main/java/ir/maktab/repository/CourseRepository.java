package ir.maktab.repository;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    Page<Course> findByTeachersContains(Account account,Pageable pageable);
    Page<Course> findByStudentsContains(Account account,Pageable pageable);
    Page<Course> findById(Long courseId,Pageable pageable);

}
