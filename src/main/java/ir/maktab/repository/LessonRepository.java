package ir.maktab.repository;

import ir.maktab.model.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepository extends JpaRepository<Classification,Long> {
    Classification findByTitle(String title);

}
