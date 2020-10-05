package ir.maktab.repository;

import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByPersonalInfoEmail(String email);

    Page<Account> findAll(Specification specififcation, Pageable pageable);

    @Query(value="select count(a.id) from Account a where a.role.id=?1")
    Long countAccountByRole(Long roleId);
}

