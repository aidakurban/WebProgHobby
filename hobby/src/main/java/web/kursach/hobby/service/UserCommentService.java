package web.kursach.hobby.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.kursach.hobby.dto.UserCommentDTO;
import web.kursach.hobby.entity.UserComment;
import web.kursach.hobby.exception.EntityNotFoundException;
import web.kursach.hobby.mapper.EntityMapper;
import web.kursach.hobby.repository.HobbyRepository;
import web.kursach.hobby.repository.UserCommentRepository;
import web.kursach.hobby.repository.UserRepository;

@Service
public class UserCommentService {

    private final UserCommentRepository userCommentRepository;
    private final EntityMapper entityMapper;
   
    public UserCommentService(UserCommentRepository userCommentRepository,
                              UserRepository userRepository,
                              HobbyRepository hobbyRepository,
                              EntityMapper entityMapper) {
        this.userCommentRepository = userCommentRepository;
        this.entityMapper = entityMapper;
    }

    public UserCommentDTO createUserComment(UserCommentDTO userCommentDTO) {
        UserComment userComment = entityMapper.commentDTOToEntity(userCommentDTO);
        // Дополнительные проверки и логика, если необходимо
        UserComment savedUserComment = userCommentRepository.save(userComment);
        return entityMapper.commentToDTO(savedUserComment);
    }

    public UserCommentDTO getUserCommentById(Long id) {
        UserComment userComment = userCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User comment not found with id: " + id));
        return entityMapper.commentToDTO(userComment);
    }

    public List<UserCommentDTO> getAllUserComments() {
        List<UserComment> userComments = userCommentRepository.findAll();
        return entityMapper.commentListToDTO(userComments);
    }

    public UserCommentDTO updateUserComment(Long id, UserCommentDTO updatedUserCommentDTO) {
        UserComment existingUserComment = userCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User comment not found with id: " + id));

        // Обновление полей комментария пользователя из DTO
        existingUserComment.setCommentDateTime(updatedUserCommentDTO.getCommentDateTime());
        existingUserComment.setCommentText(updatedUserCommentDTO.getCommentText());

        // Сохранение обновленного комментария пользователя
        UserComment updatedUserComment = userCommentRepository.save(existingUserComment);

        return entityMapper.commentToDTO(updatedUserComment);
    }

    public void deleteUserComment(Long id) {
        UserComment userComment = userCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User comment not found with id: " + id));

        userCommentRepository.delete(userComment);
    }
    //******************************************************
    public List<UserCommentDTO> getAllUserCommentsByUserId(Long userId) {
        List<UserComment> userComments = userCommentRepository.findByUserId(userId);
        return entityMapper.commentListToDTO(userComments);
    }

    public List<UserCommentDTO> getAllUserCommentsByHobbyId(Long hobbyId) {
        List<UserComment> userComments = userCommentRepository.findByHobbyId(hobbyId);
        return entityMapper.commentListToDTO(userComments);
    }
}
