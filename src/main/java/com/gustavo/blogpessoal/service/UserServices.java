package com.gustavo.blogpessoal.service;

import com.gustavo.blogpessoal.DTO.UpdateUserDTO;
import com.gustavo.blogpessoal.DTO.UserDTO;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.entity.user.UserType;
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
        newUser.setUserType(userDTO.type());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        saveUserDatabase(newUser);
        return newUser;
    }

    public User createAdmin(UserDTO userDTO) {
        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        newUser.setUserType(userDTO.type());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        saveUserDatabase(newUser);
        return newUser;
    }

    public void updateUser(UpdateUserDTO userDTO, User loggedInUser) throws Exception {
        loggedInUser.setUsername(userDTO.username());
        loggedInUser.setEmail(userDTO.email());
        loggedInUser.setPassword(passwordEncoder.encode(userDTO.password()));
        saveUserDatabase(loggedInUser);
    }

    public List<User> getAllUsers() {return userRepository.findAll();}

    public void deleteUserId(UUID userId) throws Exception {userRepository.deleteById(userId);}

    public void deleteUser(User loggedInUser) {userRepository.delete(loggedInUser);}

    public void saveUserDatabase(User user) {
        this.userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByType(UserType type) {return userRepository.findByUserType(type);}

    public Optional<User> findById(UUID id) {return userRepository.findById(id);}
}
