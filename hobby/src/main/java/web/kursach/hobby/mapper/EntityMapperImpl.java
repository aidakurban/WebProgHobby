package web.kursach.hobby.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import web.kursach.hobby.dto.*;
import web.kursach.hobby.entity.*;
import web.kursach.hobby.repository.*;
import web.kursach.hobby.exception.*;

@Component
public class EntityMapperImpl implements EntityMapper {

    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;

    public EntityMapperImpl(UserRepository userRepository, HobbyRepository hobbyRepository) 
        {
        this.userRepository = userRepository;
        this.hobbyRepository = hobbyRepository;
    }

//********ХОББИ******************************************************************
    @Override
    public HobbyDTO hobbyToDTO(Hobby hobby) {
        HobbyDTO hobbyDTO = new HobbyDTO();
        hobbyDTO.setId(hobby.getId());
        hobbyDTO.setName(hobby.getName());
        hobbyDTO.setCategory(hobby.getCategory());
        hobbyDTO.setDescription(hobby.getDescription());
        return hobbyDTO;
    }

    @Override
    public Hobby hobbyDTOToEntity(HobbyDTO hobbyDTO) {
        Hobby hobby = new Hobby();
        hobby.setId(hobbyDTO.getId());
        hobby.setName(hobbyDTO.getName());
        hobby.setCategory(hobbyDTO.getCategory());
        hobby.setDescription(hobbyDTO.getDescription());
        return hobby;
    }

    @Override
    public List<HobbyDTO> hobbyListToDTO(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOs = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            hobbyDTOs.add(hobbyToDTO(hobby));
        }
        return hobbyDTOs;
    }
//********Юзеры******************************************************************
    @Override
    public UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Override
    public User userDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }

    @Override
    public List<UserDTO> userListToDTO(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(userToDTO(user));
        }
        return userDTOs;
    }
//********Комментарии******************************************************************
    @Override
    public UserCommentDTO commentToDTO(UserComment userComment) {
        UserCommentDTO commentDTO = new UserCommentDTO();
        commentDTO.setId(userComment.getId());
        commentDTO.setUserId(userComment.getUser().getId());
        commentDTO.setHobbyId(userComment.getHobby().getId());
        if(userComment.getCommentDateTime() != null && userComment.getCommentDateTime()!="") {
            commentDTO.setCommentDateTime(userComment.getCommentDateTime());
        } else {
            commentDTO.setCommentDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        commentDTO.setCommentText(userComment.getCommentText());
        return commentDTO;
    }

    @Override
    public UserComment commentDTOToEntity(UserCommentDTO commentDTO) {
        UserComment userComment = new UserComment();
        userComment.setId(commentDTO.getId());
        User user = userRepository.findById(commentDTO.getUserId())
                                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + commentDTO.getUserId()));
        userComment.setUser(user);

        Hobby hobby = hobbyRepository.findById(commentDTO.getHobbyId())
                                    .orElseThrow(() -> new EntityNotFoundException("Hobby not found with id: " + commentDTO.getHobbyId()));
        userComment.setHobby(hobby);
        if(commentDTO.getCommentDateTime() != null && commentDTO.getCommentDateTime()!="") {
            userComment.setCommentDateTime(commentDTO.getCommentDateTime());
        } else {
            userComment.setCommentDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }        
        userComment.setCommentText(commentDTO.getCommentText());
        return userComment;
    }

    @Override
    public List<UserCommentDTO> commentListToDTO(List<UserComment> userComments) {
        List<UserCommentDTO> userCommentDTOs = new ArrayList<>();
        for (UserComment userComment : userComments) {
            userCommentDTOs.add(commentToDTO(userComment));
        }
        return userCommentDTOs;
    }

//********  Material   ******************************************************************
    @Override
    public MaterialDTO materialToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setMaterialName(material.getMaterialName());
        materialDTO.setHobbyId(material.getHobby().getId());
        return materialDTO;
    }

    @Override
    public Material materialDTOToEntity(MaterialDTO materialDTO) {
        Material material = new Material();
        material.setId(materialDTO.getId());
        Hobby hobby = hobbyRepository.findById(materialDTO.getHobbyId())
                                    .orElseThrow(() -> new EntityNotFoundException("Hobby not found with id: " + materialDTO.getHobbyId()));
        material.setHobby(hobby);
        material.setMaterialName(materialDTO.getMaterialName());
        return material;
    }

    @Override
    public List<MaterialDTO> materialListToDTO(List<Material> materials) {
        List<MaterialDTO> materialDTOs = new ArrayList<>();
        for (Material material : materials) {
            materialDTOs.add(materialToDTO(material));
        }
        return materialDTOs;
    }
    //********  Planned Hobby   ******************************************************************
    @Override
    public PlannedHobbyDTO plannedHobbyToDTO(PlannedHobby plannedHobby) {
        PlannedHobbyDTO plannedHobbyDTO = new PlannedHobbyDTO();
        plannedHobbyDTO.setId(plannedHobby.getId());
        plannedHobbyDTO.setUserId(plannedHobby.getUser().getId());
        plannedHobbyDTO.setHobbyId(plannedHobby.getHobby().getId());
        if(plannedHobby.getPlannedHobbyDateTime() != null && plannedHobby.getPlannedHobbyDateTime()!="") {
            plannedHobbyDTO.setPlannedHobbyDateTime(plannedHobby.getPlannedHobbyDateTime());
        } else {
            plannedHobbyDTO.setPlannedHobbyDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return plannedHobbyDTO;
    }

    @Override
    public PlannedHobby plannedHobbyDTOToEntity(PlannedHobbyDTO plannedHobbyDTO) {
        PlannedHobby plannedHobby = new PlannedHobby();
        plannedHobby.setId(plannedHobbyDTO.getId());
        User user = userRepository.findById(plannedHobbyDTO.getUserId())
                                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + plannedHobbyDTO.getUserId()));
        plannedHobby.setUser(user);

        Hobby hobby = hobbyRepository.findById(plannedHobbyDTO.getHobbyId())
                                    .orElseThrow(() -> new EntityNotFoundException("Hobby not found with id: " + plannedHobbyDTO.getHobbyId()));
        plannedHobby.setHobby(hobby);
        if(plannedHobbyDTO.getPlannedHobbyDateTime() != null && plannedHobbyDTO.getPlannedHobbyDateTime()!="") {
            plannedHobby.setPlannedHobbyDateTime(plannedHobbyDTO.getPlannedHobbyDateTime());
        } else {
            plannedHobby.setPlannedHobbyDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }        
        return plannedHobby;
    }

    @Override
    public List<PlannedHobbyDTO> plannedHobbyListToDTO(List<PlannedHobby> plannedHobbies) {
        List<PlannedHobbyDTO> plannedHobbyDTOs = new ArrayList<>();
        for (PlannedHobby plannedHobby : plannedHobbies) {
            plannedHobbyDTOs.add(plannedHobbyToDTO(plannedHobby));
        }
        return plannedHobbyDTOs;
    }
}