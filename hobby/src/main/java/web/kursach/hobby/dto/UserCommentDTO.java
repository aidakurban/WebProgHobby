package web.kursach.hobby.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommentDTO {
    private long id;
    private Long userId; // Внешний ключ к пользователю
    private Long hobbyId; // Внешний ключ к хобби

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String commentDateTime;
    
    private String commentText;
}
