package ir.maktab.repository;

import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizListContains(Quiz quiz);

}
