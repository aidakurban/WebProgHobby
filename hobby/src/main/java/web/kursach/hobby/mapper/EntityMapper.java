package web.kursach.hobby.mapper;

import web.kursach.hobby.entity.*;
import web.kursach.hobby.dto.*;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    HobbyDTO hobbyToDTO(Hobby hobby);
    Hobby hobbyDTOToEntity(HobbyDTO hobbyDTO);

    UserDTO userToDTO(User user);
    User userDTOToEntity(UserDTO userDTO);

    UserCommentDTO commentToDTO(UserComment userComment);
    UserComment commentDTOToEntity(UserCommentDTO UserCommentDTO);

    MaterialDTO materialToDTO(Material material);
    Material materialDTOToEntity(MaterialDTO materialDTO);

    PlannedHobbyDTO plannedHobbyToDTO(PlannedHobby plannedHobby);
    PlannedHobby plannedHobbyDTOToEntity(PlannedHobbyDTO plannedHobbyDTO);

    // Добавляем метод для преобразования списка хобби в список DTO
    List<HobbyDTO> hobbyListToDTO(List<Hobby> hobbies);
    List<UserDTO> userListToDTO(List<User> users);
    List<UserCommentDTO> commentListToDTO(List<UserComment> comments);
    List<MaterialDTO> materialListToDTO(List<Material> materials);
    List<PlannedHobbyDTO> plannedHobbyListToDTO(List<PlannedHobby> plannedHobbies);

}