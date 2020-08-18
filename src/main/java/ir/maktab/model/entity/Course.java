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
    private int id;

    private String title;

    @Enumerated(EnumType.STRING)
    private LessonTitle lessonTitle;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date finishDate;

    @ManyToMany
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    private Teacher teacher;
}
