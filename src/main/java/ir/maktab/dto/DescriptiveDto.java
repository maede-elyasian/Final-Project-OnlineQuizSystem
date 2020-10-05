package ir.maktab.dto;

import ir.maktab.model.entity.DescriptiveQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DescriptiveDto {
    private List<DescriptiveQuestion> questionList;
    private int totalElements;
}
