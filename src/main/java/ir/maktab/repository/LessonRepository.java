package ir.maktab.repository;

import ir.maktab.model.entity.Lesson;
import ir.maktab.model.enums.LessonTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Lesson findByTitle(LessonTitle title);
}
