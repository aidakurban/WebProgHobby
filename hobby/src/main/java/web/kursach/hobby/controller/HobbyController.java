package web.kursach.hobby.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import web.kursach.hobby.service.HobbyService;
import web.kursach.hobby.dto.HobbyDTO;

@RestController
@RequestMapping("/hobbies")
public class HobbyController {

    private final HobbyService hobbyService;

    //@Autowired
    public HobbyController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @PostMapping
    public ResponseEntity<HobbyDTO> createHobby(@RequestBody HobbyDTO hobbyDTO) {
        HobbyDTO createdHobby = hobbyService.createHobby(hobbyDTO);
        return new ResponseEntity<>(createdHobby, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HobbyDTO>> getAllHobbies() {
        List<HobbyDTO> hobbies = hobbyService.getAllHobbies();
        return new ResponseEntity<>(hobbies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HobbyDTO> updateHobby(@PathVariable Long id, @RequestBody HobbyDTO hobbyDTO) {
        HobbyDTO updatedHobby = hobbyService.updateHobby(id, hobbyDTO);
        return new ResponseEntity<>(updatedHobby, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HobbyDTO> getHobbyById(@PathVariable Long id) {
        HobbyDTO hobbyDTO = hobbyService.getHobbyById(id);
        return new ResponseEntity<>(hobbyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHobbyById(@PathVariable Long id) {
        hobbyService.deleteHobbyById(id);
        return ResponseEntity.noContent().build();
    }

}