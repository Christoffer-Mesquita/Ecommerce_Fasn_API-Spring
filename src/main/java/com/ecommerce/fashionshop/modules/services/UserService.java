package com.ecommerce.fashionshop.modules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.fashionshop.modules.dto.UserDTO;
import com.ecommerce.fashionshop.modules.dto.LoginDTO;
import com.ecommerce.fashionshop.model.User;
import com.ecommerce.fashionshop.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Já existe um usuário com este email.");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());

        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Email ou senha inválidos.");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario não encontrado com o ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}