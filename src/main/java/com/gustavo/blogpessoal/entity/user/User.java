package com.gustavo.blogpessoal.entity.user;

import com.gustavo.blogpessoal.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
