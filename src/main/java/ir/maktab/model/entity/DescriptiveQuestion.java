package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Descriptive")
public class DescriptiveQuestion extends Question {

    @OneToOne
    private Answer answer;
}
