package web.kursach.hobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import web.kursach.hobby.service.UserService;
import web.kursach.hobby.dto.UserDTO;
import web.kursach.hobby.entity.User;
import web.kursach.hobby.mapper.EntityMapper;

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
//@RequestMapping("/users")
public class UserController {
    /*
    @Autowired
    private AuthenticationManager authenticationManager;
    */
    @Autowired // Или @Resource, если предпочитаете
    private EntityMapper entityMapper;
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // create user
    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.createUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
    /*/
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginUser) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    */
    // Метод обновления пользователя
    //@PreAuthorize("hasPermission(authentication, 'editUser')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        User updatedUser = userService.updateUser(id, updatedUserDTO);
        UserDTO updatedUserResponse = entityMapper.userToDTO(updatedUser); 
        return new ResponseEntity<>(updatedUserResponse, HttpStatus.OK);
    }

    // Метод удаления пользователя
    //@PreAuthorize("hasPermission(authentication, 'deleteUser')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // find by id
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    // find by usrname (aka login)
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    // find all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

}



