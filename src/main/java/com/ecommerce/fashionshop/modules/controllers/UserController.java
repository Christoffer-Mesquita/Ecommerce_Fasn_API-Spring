package com.ecommerce.fashionshop.modules.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.fashionshop.modules.dto.UserDTO;
import com.ecommerce.fashionshop.modules.dto.LoginDTO;
import com.ecommerce.fashionshop.model.User;
import com.ecommerce.fashionshop.modules.services.UserService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SECRET_KEY = "Fashion2024@Secret";

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        User loggedUser = userService.loginUser(loginDTO);
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAllUsers(@RequestParam String secretKey) {
        if (!SECRET_KEY.equals(secretKey)) {
            return new ResponseEntity<>("Key invalida!", HttpStatus.UNAUTHORIZED);
        }
        userService.deleteAllUsers();
        return new ResponseEntity<>("Todos os usuarios foram apagados!", HttpStatus.OK);
    }

    @PostMapping("/deleteId")
    public ResponseEntity<String> deleteID(@RequestParam String secretKey, @RequestParam Long id) {
        if (!SECRET_KEY.equals(secretKey)) {
            return new ResponseEntity<>("Key invalida!", HttpStatus.UNAUTHORIZED);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>("Usuario apagado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam String secretKey) {
        if (!SECRET_KEY.equals(secretKey)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}