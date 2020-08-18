package ir.maktab.repository;

import ir.maktab.model.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo,Integer> {

    Optional<PersonalInfo> findByEmail(String email);
}
