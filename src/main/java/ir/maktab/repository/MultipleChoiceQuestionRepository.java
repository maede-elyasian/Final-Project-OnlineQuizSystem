package ir.maktab.repository;

import ir.maktab.model.entity.MultipleChoiceQuestion;
import ir.maktab.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion,Long> {
    Page<MultipleChoiceQuestion> findByQuizListContains(Quiz quiz, Pageable pageable);
}
