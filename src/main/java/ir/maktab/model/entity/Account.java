package ir.maktab.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private boolean isEnabled;

    @ManyToOne
    private Status status;

    @OneToOne
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info_id",referencedColumnName = "id")
    private PersonalInfo personalInfo;

    @ManyToMany
    private List<Course> courseList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", isEnabled=" + isEnabled +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
