package ir.maktab.repository;

import ir.maktab.model.entity.Status;
import ir.maktab.model.enums.StatusTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Long> {
 Status findByTitle(StatusTitle title);

}
