package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Student")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends PersonalInfo {

    @ManyToMany
    private List<Course> courseList = new ArrayList<>();

}

