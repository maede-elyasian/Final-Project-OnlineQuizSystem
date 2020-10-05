package ir.maktab.dto;

import lombok.*;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String roleTitle;
}
