package ir.maktab.repository;

import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Page<Quiz> findAll(Pageable pageable);

    Page<Quiz> findByCourse_Id(Long courseId, Pageable pageable);

}
