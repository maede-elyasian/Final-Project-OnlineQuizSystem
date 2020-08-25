package ir.maktab.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EditAccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String nationalCode;
    private String username;
    private String password;
    private String status;
    private String role;
}
