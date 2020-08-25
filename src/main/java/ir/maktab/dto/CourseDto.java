package ir.maktab.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CourseDto {

    private Long id;
    private String courseTitle;
    private String lessonTitle;
    private String startDate;
    private String finishDate;
}
