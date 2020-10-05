package ir.maktab.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseTitle;

    @OneToOne
    private Classification classification;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private List<Account> students = new ArrayList<>();

    @ManyToMany(cascade =CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private List<Account> teachers=new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<Quiz> quizzes=new ArrayList<>();
}
