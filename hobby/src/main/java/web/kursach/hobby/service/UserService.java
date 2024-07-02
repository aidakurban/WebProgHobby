package web.kursach.hobby.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.kursach.hobby.repository.UserRepository;
import web.kursach.hobby.config.CustomUserDetails;
import web.kursach.hobby.dto.UserDTO;
import web.kursach.hobby.entity.User;
import web.kursach.hobby.exception.EntityNotFoundException;
import web.kursach.hobby.mapper.EntityMapper;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EntityMapper entityMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(CustomUserDetails::new)
                        .orElseThrow(()-> new EntityNotFoundException("User with username=" + username + " not found"));
    }

    public UserDetails loadUserByUserIDetails(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(CustomUserDetails::new)
                        .orElseThrow(()-> new EntityNotFoundException("User with ID=" + id + " not found"));
    }

    // create
    public UserDTO createUser(UserDTO userDTO) {
        User user = entityMapper.userDTOToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return entityMapper.userToDTO(savedUser);
    }
    //обновить
    public User updateUser(Long userId, UserDTO updatedUserDTO) {
        // Проверяем, существует ли пользователь с заданным идентификатором
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Обновляем поля пользователя из DTO
        existingUser.setFullName(updatedUserDTO.getFullName());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        existingUser.setRole(updatedUserDTO.getRole());

        // Сохраняем обновленного пользователя
        return userRepository.save(existingUser);
    }

    // удаление пользователя
    public void deleteUser(Long userId) {
        // Проверяем, существует ли пользователь с заданным идентификатором
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userRepository.deleteById(userId);
    }
    // найти по айди 
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        return entityMapper.userToDTO(user); 
    }
    // найти по логину
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
        return entityMapper.userToDTO(user); 
    }
    // отобразить всех юзеров
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(entityMapper::userToDTO) 
                .collect(Collectors.toList());
    }

}
