package ir.maktab.repository;

import ir.maktab.model.entity.DescriptiveQuestion;
import ir.maktab.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptiveQuestionRepository extends JpaRepository<DescriptiveQuestion, Long> {
    Page<DescriptiveQuestion> findByQuizListContains(Quiz quiz, Pageable pageable);
}
