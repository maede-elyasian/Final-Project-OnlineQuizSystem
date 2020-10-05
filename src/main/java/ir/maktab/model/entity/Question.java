package ir.maktab.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "title can not be null")
    private String title;

    private String content;
    
    private boolean isMultipleChoice;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @ManyToOne
    private Account creator;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_quizList",joinColumns = @JoinColumn(name = "question_id"),
          inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    private List<Quiz> quizList = new ArrayList<>();

    @Override
    public String toString() {
        return "{ \"id\" : " + id +
                ", \"title\" : \"" + title + '\"' +
                ", \"content\" : \"" + content + '\"' +
                "}";
    }

}
