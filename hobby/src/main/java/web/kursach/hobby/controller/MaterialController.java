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

import web.kursach.hobby.dto.MaterialDTO;
import web.kursach.hobby.service.MaterialService;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<MaterialDTO> createMaterial(@RequestBody MaterialDTO materialDTO) {
        MaterialDTO createdMaterial = materialService.createMaterial(materialDTO);
        return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getMaterialById(@PathVariable Long id) {
        MaterialDTO materialDTO = materialService.getMaterialById(id);
        return new ResponseEntity<>(materialDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        List<MaterialDTO> materialDTOs = materialService.getAllMaterials();
        return new ResponseEntity<>(materialDTOs, HttpStatus.OK);
    }

    //@PreAuthorize("hasPermission(authentication, 'editComment')")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id, @RequestBody MaterialDTO updatedMaterialDTO) {
        MaterialDTO updatedMaterial = materialService.updateMaterial(id, updatedMaterialDTO);
        return new ResponseEntity<>(updatedMaterial, HttpStatus.OK);
    }
    //@PreAuthorize("hasPermission(authentication, 'deleteComment')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    //******************************************************
    @GetMapping("/hobby/{hobbyId}")
    public ResponseEntity<List<MaterialDTO>> getAllMaterialsByHobbyId(@PathVariable Long hobbyId) {
        List<MaterialDTO> materials = materialService.getMaterialsByHobbyId(hobbyId);
        return ResponseEntity.ok(materials);
    }
}
