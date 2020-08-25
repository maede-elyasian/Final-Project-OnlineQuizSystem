package ir.maktab.repository;

import ir.maktab.model.entity.Role;
import ir.maktab.model.enums.RoleTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByTitle(RoleTitle roleTitle);
}
