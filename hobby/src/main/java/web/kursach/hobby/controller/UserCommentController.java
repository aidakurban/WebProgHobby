package web.kursach.hobby.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import web.kursach.hobby.dto.UserCommentDTO;
import web.kursach.hobby.service.UserCommentService;

@RestController
public class UserCommentController {

    private final UserCommentService userCommentService;

    public UserCommentController(UserCommentService userCommentService) {
        this.userCommentService = userCommentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<UserCommentDTO> createUserComment(@RequestBody UserCommentDTO userCommentDTO) {
        UserCommentDTO createdUserComment = userCommentService.createUserComment(userCommentDTO);
        return new ResponseEntity<>(createdUserComment, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<UserCommentDTO> getUserCommentById(@PathVariable Long id) {
        UserCommentDTO userCommentDTO = userCommentService.getUserCommentById(id);
        return new ResponseEntity<>(userCommentDTO, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<UserCommentDTO>> getAllUserComments() {
        List<UserCommentDTO> userCommentDTOs = userCommentService.getAllUserComments();
        return new ResponseEntity<>(userCommentDTOs, HttpStatus.OK);
    }

    //@PreAuthorize("hasPermission(authentication, 'editComment')")
    @PutMapping("/comments/{id}")
    public ResponseEntity<UserCommentDTO> updateUserComment(@PathVariable Long id, @RequestBody UserCommentDTO updatedUserCommentDTO) {
        UserCommentDTO updatedUserComment = userCommentService.updateUserComment(id, updatedUserCommentDTO);
        return new ResponseEntity<>(updatedUserComment, HttpStatus.OK);
    }
    //@PreAuthorize("hasPermission(authentication, 'deleteComment')")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteUserComment(@PathVariable Long id) {
        userCommentService.deleteUserComment(id);
        return ResponseEntity.noContent().build();
    }

    //******************************************************
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<List<UserCommentDTO>> getAllUserCommentsByUserId(@PathVariable Long userId) {
        List<UserCommentDTO> userComments = userCommentService.getAllUserCommentsByUserId(userId);
        return ResponseEntity.ok(userComments);
    }

    @GetMapping("/comments/hobby/{hobbyId}")
    public ResponseEntity<List<UserCommentDTO>> getAllUserCommentsByHobbyId(@PathVariable Long hobbyId) {
        List<UserCommentDTO> userComments = userCommentService.getAllUserCommentsByHobbyId(hobbyId);
        return ResponseEntity.ok(userComments);
    }
    /*

    @GetMapping("/comments/user/{userId}/hobby/{hobbyId}")
    public ResponseEntity<List<UserCommentDTO>> getAllUserCommentsByHobbyId(
                                                    @PathVariable Long userId, 
                                                    @PathVariable Long hobbyId) {
        List<UserCommentDTO> userComments = userCommentService.getAllUserCommentsByHobbyId(hobbyId);
        return ResponseEntity.ok(userComments);
    }*/
}
