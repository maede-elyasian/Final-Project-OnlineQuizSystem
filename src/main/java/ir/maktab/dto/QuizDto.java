package ir.maktab.dto;


import ir.maktab.model.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private List<Quiz> quizList;
    private int totalPages;

}
