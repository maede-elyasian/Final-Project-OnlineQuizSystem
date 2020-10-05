package ir.maktab.dto;

import ir.maktab.model.entity.DescriptiveQuestion;
import ir.maktab.model.entity.MultipleChoiceQuestion;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizOperationDto {
    private List<MultipleChoiceQuestion> multipleChoice = new ArrayList<>();
    private List<DescriptiveQuestion> descriptive = new ArrayList<>();
    private Long total;

}
