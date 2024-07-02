package web.kursach.hobby.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import web.kursach.hobby.enums.UserRole;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    @Column(unique=true)
    private String email;

    @Column(unique=true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<UserComment> userComment;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlannedHobby> plannedHobbies;
}
