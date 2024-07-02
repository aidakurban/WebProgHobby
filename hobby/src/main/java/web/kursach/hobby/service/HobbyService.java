package web.kursach.hobby.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.kursach.hobby.mapper.EntityMapper;
import web.kursach.hobby.repository.HobbyRepository;
import web.kursach.hobby.dto.HobbyDTO;
import web.kursach.hobby.entity.Hobby;
import web.kursach.hobby.exception.EntityNotFoundException;
@Service
public class HobbyService {
    
    private final HobbyRepository hobbyRepository;
    private final EntityMapper entityMapper; // маппер для преобразования DTO и сущностей

    public HobbyService(HobbyRepository hobbyRepository, EntityMapper entityMapper) {
        this.hobbyRepository = hobbyRepository;
        this.entityMapper = entityMapper;
    }
    // создать
    public HobbyDTO createHobby(HobbyDTO hobbyDTO) {
        Hobby hobby = entityMapper.hobbyDTOToEntity(hobbyDTO);
        Hobby savedHobby = hobbyRepository.save(hobby);
        return entityMapper.hobbyToDTO(savedHobby);
    }
    // все хобби
    public List<HobbyDTO> getAllHobbies() {
        List<Hobby> hobbies = hobbyRepository.findAll();
        return entityMapper.hobbyListToDTO(hobbies); 
    }
    // обновить хобби
    public HobbyDTO updateHobby(Long id, HobbyDTO hobbyDTO) {
        Hobby existingHobby = hobbyRepository.findById(id)
                                             .orElseThrow(() -> new EntityNotFoundException("Хобби с ID " + id + " не найдено"));

        // Обновление полей хобби
        existingHobby.setName(hobbyDTO.getName());
        existingHobby.setCategory(hobbyDTO.getCategory());
        existingHobby.setDescription(hobbyDTO.getDescription());

        // Сохранение обновленного хобби
        Hobby updatedHobby = hobbyRepository.save(existingHobby);

        // Преобразование в DTO и возврат
        return entityMapper.hobbyToDTO(updatedHobby);
    }
    // найти хобби по айди
    public HobbyDTO getHobbyById(Long id) {
        Hobby hobby = hobbyRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException("Хобби с ID " + id + " не найдено"));
        return entityMapper.hobbyToDTO(hobby);
    }
    // удалить по айди
    public void deleteHobbyById(Long id) {
        Hobby hobby = hobbyRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException("Hobby with id " + id + " not found"));
        hobbyRepository.delete(hobby);
    }

}