package ir.maktab.model.entity;

import ir.maktab.model.enums.LessonTitle;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseTitle;

    @OneToOne
    private Lesson lesson;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String finishDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Account> students = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Account> teachers;
}
