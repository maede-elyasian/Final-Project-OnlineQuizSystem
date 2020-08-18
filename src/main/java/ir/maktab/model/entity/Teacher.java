package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends PersonalInfo{

    @OneToMany
    private List<Teacher> teachers = new ArrayList<>();

}
