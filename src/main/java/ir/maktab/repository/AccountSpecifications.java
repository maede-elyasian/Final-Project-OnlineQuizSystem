package ir.maktab.repository;


import ir.maktab.model.entity.Account;
import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.model.entity.Role;
import ir.maktab.model.entity.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface AccountSpecifications extends JpaSpecificationExecutor<Account> {

    static Specification<Account> findMaxMatch(String role,
                                               String status,
                                               String name, String lastName,
                                               String email) {
        return (Specification<Account>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<Account> resultCriteria = builder.createQuery(Account.class);
            Join<Account, PersonalInfo> personalInfoJoin = root.join("personalInfo");
            Join<Account, Role> roleJoin = root.join("role");
            Join<Account, Status> statusJoin = root.join("status");

            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!StringUtils.isEmpty(role) &&role!=null) {
                predicates.add(builder.equal(roleJoin.get("title"),role));
            }
            if (!StringUtils.isEmpty(status) &&status != null) {
                predicates.add(builder.equal(statusJoin.get("title").as(String.class),status));
            }

            if (!StringUtils.isEmpty(name) && name != null) {
                predicates.add(builder.equal(personalInfoJoin.get("firstName"), name));
            }
            if (!StringUtils.isEmpty(lastName) && lastName != null) {
                predicates.add(builder.equal(personalInfoJoin.get("lastName"), lastName));
            }
            if (!StringUtils.isEmpty(email) && email != null) {
                predicates.add(builder.equal(personalInfoJoin.get("email"), email));
            }
            resultCriteria.multiselect(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
