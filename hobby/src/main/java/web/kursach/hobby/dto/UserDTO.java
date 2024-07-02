package web.kursach.hobby.dto;

import web.kursach.hobby.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private UserRole role;
}