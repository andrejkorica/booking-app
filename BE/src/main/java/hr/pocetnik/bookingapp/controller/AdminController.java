package hr.pocetnik.bookingapp.controller;

import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.UserRepository;
import hr.pocetnik.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AdminController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<Map<String, String>> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUser)
                .toList();
    }

    @PostMapping("/users/{userId}")
    public Map<String, String> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody Map<String, String> userMap
    ) {
        UserEntity updatedUser = userService.updateUserByAdmin(
                userId,
                userMap.get("name"),
                userMap.get("surname"),
                userMap.get("email"),
                userMap.get("phoneNumber"),
                userMap.get("role")
        );

        return mapUser(updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserByAdmin(userId);
    }

    private Map<String, String> mapUser(UserEntity user) {
        return Map.of(
                "id", user.getId().toString(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "phoneNumber", user.getPhoneNumber(),
                "role", user.getRole().name()
        );
    }
}