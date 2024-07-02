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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.kursach.hobby.dto.PlannedHobbyDTO;
import web.kursach.hobby.dto.UserCommentDTO;
import web.kursach.hobby.service.PlannedHobbyService;
import web.kursach.hobby.service.UserCommentService;

@RestController
@RequestMapping("/plannedhobbies")
public class PlannedHobbyController {
    private final PlannedHobbyService plannedHobbyService;

    public PlannedHobbyController(PlannedHobbyService plannedHobbyService) {
        this.plannedHobbyService = plannedHobbyService;
    }

    @PostMapping
    public ResponseEntity<PlannedHobbyDTO> createPlannedHobby(@RequestBody PlannedHobbyDTO plannedHobbyDTO) {
        PlannedHobbyDTO createdPlannedHobby = plannedHobbyService.createPlannedHobby(plannedHobbyDTO);
        return new ResponseEntity<>(createdPlannedHobby, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannedHobbyDTO> getPlannedHobbyById(@PathVariable Long id) {
        PlannedHobbyDTO plannedHobbyDTO = plannedHobbyService.getPlannedHobbyById(id);
        return new ResponseEntity<>(plannedHobbyDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlannedHobbyDTO>> getAllPlannedHobbies() {
        List<PlannedHobbyDTO> plannedHobbyDTOs = plannedHobbyService.getAllPlannedHobbies();
        return new ResponseEntity<>(plannedHobbyDTOs, HttpStatus.OK);
    }

    //@PreAuthorize("hasPermission(authentication, 'editComment')")
    @PutMapping("/{id}")
    public ResponseEntity<PlannedHobbyDTO> updatePlannedHobby(@PathVariable Long id, @RequestBody PlannedHobbyDTO updatedPlannedHobbyDTO) {
        PlannedHobbyDTO updatedPlannedHobby = plannedHobbyService.updatePlannedHobby(id, updatedPlannedHobbyDTO);
        return new ResponseEntity<>(updatedPlannedHobby, HttpStatus.OK);
    }
    //@PreAuthorize("hasPermission(authentication, 'deleteComment')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlannedHobby(@PathVariable Long id) {
        plannedHobbyService.deletePlannedHobby(id);
        return ResponseEntity.noContent().build();
    }

    //******************************************************
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlannedHobbyDTO>> getPlannedHobbiesByUserId(@PathVariable Long userId) {
        List<PlannedHobbyDTO> plannedHobby = plannedHobbyService.getAllPlannedHobbiesByUserId(userId);
        return ResponseEntity.ok(plannedHobby);
    }

    @GetMapping("/hobby/{hobbyId}")
    public ResponseEntity<List<PlannedHobbyDTO>> getPlannedHobbiesByHobbyId(@PathVariable Long hobbyId) {
        List<PlannedHobbyDTO> plannedHobbies = plannedHobbyService.getAllPlannedHobbiesByHobbyId(hobbyId);
        return ResponseEntity.ok(plannedHobbies);
    }
}


