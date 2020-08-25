package ir.maktab.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchAccountDto {
    private String nationalCode;
    private String firstName;
    private String lastName;
    private String role;
    private String status;
    private String email;
}
