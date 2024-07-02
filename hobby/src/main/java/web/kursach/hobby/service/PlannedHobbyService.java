package web.kursach.hobby.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.kursach.hobby.dto.PlannedHobbyDTO;
import web.kursach.hobby.entity.PlannedHobby;
import web.kursach.hobby.exception.EntityNotFoundException;
import web.kursach.hobby.mapper.EntityMapper;
import web.kursach.hobby.repository.HobbyRepository;
import web.kursach.hobby.repository.PlannedHobbyRepository;
import web.kursach.hobby.repository.UserRepository;

@Service
public class PlannedHobbyService {

    private final PlannedHobbyRepository plannedHobbyRepository;
    private final EntityMapper entityMapper;
   
    public PlannedHobbyService(PlannedHobbyRepository plannedHobbyRepository,
                              UserRepository userRepository,
                              HobbyRepository hobbyRepository,
                              EntityMapper entityMapper) {
        this.plannedHobbyRepository = plannedHobbyRepository;
        this.entityMapper = entityMapper;
    }

    public PlannedHobbyDTO createPlannedHobby(PlannedHobbyDTO plannedHobbyDTO) {
        PlannedHobby plannedHobby = entityMapper.plannedHobbyDTOToEntity(plannedHobbyDTO);
        // Дополнительные проверки и логика, если необходимо
        PlannedHobby savedPlannedHobby = plannedHobbyRepository.save(plannedHobby);
        return entityMapper.plannedHobbyToDTO(savedPlannedHobby);
    }

    public PlannedHobbyDTO getPlannedHobbyById(Long id) {
        PlannedHobby plannedHobby = plannedHobbyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlannedHobby not found with id: " + id));
        return entityMapper.plannedHobbyToDTO(plannedHobby);
    }

    public List<PlannedHobbyDTO> getAllPlannedHobbies() {
        List<PlannedHobby> plannedHobby = plannedHobbyRepository.findAll();
        return entityMapper.plannedHobbyListToDTO(plannedHobby);
    }

    public PlannedHobbyDTO updatePlannedHobby(Long id, PlannedHobbyDTO updatedPlannedHobbyDTO) {
        PlannedHobby existingPlannedHobby = plannedHobbyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlannedHobby not found with id: " + id));

        // Обновление полей пользователя из DTO
        existingPlannedHobby.setPlannedHobbyDateTime(updatedPlannedHobbyDTO.getPlannedHobbyDateTime());

        // Сохранение обновленного 
        PlannedHobby updatedPlannedHobby = plannedHobbyRepository.save(existingPlannedHobby);

        return entityMapper.plannedHobbyToDTO(updatedPlannedHobby);
    }

    public void deletePlannedHobby(Long id) {
        PlannedHobby plannedHobby = plannedHobbyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlannedHobby not found with id: " + id));

        plannedHobbyRepository.delete(plannedHobby);
    }
    //******************************************************
    public List<PlannedHobbyDTO> getAllPlannedHobbiesByUserId(Long userId) {
        List<PlannedHobby> plannedHobbies = plannedHobbyRepository.findByUserId(userId);
        return entityMapper.plannedHobbyListToDTO(plannedHobbies);
    }

    public List<PlannedHobbyDTO> getAllPlannedHobbiesByHobbyId(Long hobbyId) {
        List<PlannedHobby> plannedHobbies = plannedHobbyRepository.findByHobbyId(hobbyId);
        return entityMapper.plannedHobbyListToDTO(plannedHobbies);
    }
}
