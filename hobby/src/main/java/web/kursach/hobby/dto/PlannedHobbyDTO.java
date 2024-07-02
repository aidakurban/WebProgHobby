package web.kursach.hobby.dto;

import lombok.Getter;
import lombok.Setter;
import web.kursach.hobby.entity.Hobby;
import web.kursach.hobby.entity.User;

@Getter
@Setter
public class PlannedHobbyDTO {
    private Long id;
    private Long userId;
    private Long hobbyId;
    private String plannedHobbyDateTime;    
}