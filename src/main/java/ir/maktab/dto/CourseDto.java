package ir.maktab.dto;

import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private List<Course> courseList;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
