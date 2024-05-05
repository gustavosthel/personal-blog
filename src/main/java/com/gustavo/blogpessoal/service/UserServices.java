package com.gustavo.blogpessoal.service;

import com.gustavo.blogpessoal.DTO.UserDTO;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) throws Exception {
        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        saveUserDatabase(newUser);
        return newUser;
    }

    public void updateUser(UserDTO userDTO) throws Exception {
        User updateUser = new User();
        BeanUtils.copyProperties(userDTO, updateUser);
        updateUser.setUsername(userDTO.username());
        updateUser.setEmail(userDTO.email());
        saveUserDatabase(updateUser);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void deleteUser(UUID userId) throws Exception {
        this.userRepository.deleteById(userId);
    }

    public void saveUserDatabase(User user) {
        this.userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
