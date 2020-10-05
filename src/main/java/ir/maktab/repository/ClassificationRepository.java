package ir.maktab.repository;

import ir.maktab.model.entity.Classification;
import ir.maktab.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification,Long> {

}
