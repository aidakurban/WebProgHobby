package web.kursach.hobby.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.kursach.hobby.dto.MaterialDTO;
import web.kursach.hobby.entity.Material;
import web.kursach.hobby.exception.EntityNotFoundException;
import web.kursach.hobby.mapper.EntityMapper;
import web.kursach.hobby.repository.HobbyRepository;
import web.kursach.hobby.repository.MaterialRepository;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final EntityMapper entityMapper;
   
    public MaterialService(MaterialRepository materialRepository,
                              HobbyRepository hobbyRepository,
                              EntityMapper entityMapper) {
        this.materialRepository = materialRepository;
        this.entityMapper = entityMapper;
    }

    public MaterialDTO createMaterial(MaterialDTO materialDTO) {
        Material material = entityMapper.materialDTOToEntity(materialDTO);
        Material savedMaterial = materialRepository.save(material);
        return entityMapper.materialToDTO(savedMaterial);
    }

    public MaterialDTO getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + id));
        return entityMapper.materialToDTO(material);
    }

    public List<MaterialDTO> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        return entityMapper.materialListToDTO(materials);
    }

    public MaterialDTO updateMaterial(Long id, MaterialDTO updatedMaterialDTO) {
        Material existingMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + id));

        // Обновление полей комментария пользователя из DTO
        existingMaterial.setMaterialName(updatedMaterialDTO.getMaterialName());

        // Сохранение обновленного комментария пользователя
        Material updatedMaterial = materialRepository.save(existingMaterial);

        return entityMapper.materialToDTO(updatedMaterial);
    }

    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + id));

        materialRepository.delete(material);
    }
    //******************************************************

    public List<MaterialDTO> getMaterialsByHobbyId(Long hobbyId) {
        List<Material> materials = materialRepository.findByHobbyId(hobbyId);
        return entityMapper.materialListToDTO(materials);
    }
}



