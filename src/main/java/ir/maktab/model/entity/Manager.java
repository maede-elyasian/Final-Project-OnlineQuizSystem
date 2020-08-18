package ir.maktab.model.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends PersonalInfo {


}
