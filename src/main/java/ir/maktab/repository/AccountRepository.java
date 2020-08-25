package ir.maktab.repository;

import ir.maktab.dto.SearchAccountDto;
import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.model.entity.Role;
import ir.maktab.model.enums.RoleTitle;
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

    Optional<Account> findByPassword(String password);

    Optional<Account> findByPersonalInfoEmail(String email);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    @Query(value="select count(a.role.id) from Account a where a.role.id=?1")
    Long countAccountByRole(Long roleId);


}

