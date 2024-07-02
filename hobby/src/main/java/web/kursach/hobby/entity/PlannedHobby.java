package web.kursach.hobby.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlannedHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String plannedHobbyDateTime;    
}