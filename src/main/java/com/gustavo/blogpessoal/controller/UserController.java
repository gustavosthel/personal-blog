package com.gustavo.blogpessoal.controller;

import com.gustavo.blogpessoal.DTO.LoginDTO;
import com.gustavo.blogpessoal.DTO.ResponseDTO;
import com.gustavo.blogpessoal.DTO.UserDTO;
import com.gustavo.blogpessoal.confg.TokenService;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO) throws Exception {

        if (!userServices.findByEmail(userDTO.email()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = userServices.createUser(userDTO);
        String token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) throws Exception {

        User user = userServices.findByEmail(loginDTO.email()).orElseThrow(() ->
                new RuntimeException("User not found"));
        if (passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {

        userServices.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO) throws Exception {
        userServices.updateUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<User>> listUsers() throws Exception {
        List<User> users = userServices.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
