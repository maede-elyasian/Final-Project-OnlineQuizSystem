package ir.maktab.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("multipleChoice-Question")
public class MultipleChoiceQuestion extends Question {


    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> options = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Answer correctAnswer;

}
