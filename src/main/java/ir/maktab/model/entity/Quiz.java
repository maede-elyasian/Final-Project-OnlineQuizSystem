package ir.maktab.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private int time;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @ManyToOne
    private Account teacher;

    @OneToMany
    private List<Account> participants;

    private boolean stop;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @ElementCollection
    @MapKeyColumn(name = "question_id")
    @Column(name = "question_point")
    @JsonIgnore
    private Map<Question, Double> questions;

}
