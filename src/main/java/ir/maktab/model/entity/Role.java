package ir.maktab.model.entity;

import ir.maktab.model.enums.RoleTitle;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleTitle title;


}
