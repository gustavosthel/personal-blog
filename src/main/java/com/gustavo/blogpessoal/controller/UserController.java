package com.gustavo.blogpessoal.controller;

import com.gustavo.blogpessoal.DTO.LoginDTO;
import com.gustavo.blogpessoal.DTO.ResponseDTO;
import com.gustavo.blogpessoal.DTO.UpdateUserDTO;
import com.gustavo.blogpessoal.DTO.UserDTO;
import com.gustavo.blogpessoal.confg.TokenService;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.entity.user.UserType;
import com.gustavo.blogpessoal.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity register(@RequestBody @Valid UserDTO userDTO) throws Exception {

        if (userServices.findByEmail(userDTO.email()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userDTO.type() == UserType.ADMIN) {
            User newUser = userServices.createAdmin(userDTO);
            if (newUser == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getUserType(), token));
        }
        User newUser = userServices.createUser(userDTO);
        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUserType(), token));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) throws Exception {

        User user = userServices.findByEmail(loginDTO.email()).orElseThrow(() ->
                new RuntimeException("User not found"));
        if (passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUserType(), token));
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        userServices.deleteUserId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();
        userServices.deleteUser(loggedInUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateUserDTO userDTO) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();
        userServices.updateUser(userDTO, loggedInUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> listUsers() throws Exception {
        List<User> users = userServices.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}