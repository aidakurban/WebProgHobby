package web.kursach.hobby.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HobbyDTO {
    private Long id;
    private String name;
    private String category;
    private String description;
}