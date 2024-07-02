package web.kursach.hobby.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String description;


    @JsonBackReference
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<UserComment> userComment;

    @JsonBackReference
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    private List<Material> materials;

    @JsonBackReference
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    private List<PlannedHobby> plannedHobbies;
}