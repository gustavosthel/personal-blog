/*package com.gustavo.blogpessoal.entity.post;

import com.gustavo.blogpessoal.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "postId")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String content;
    private LocalDateTime timeTamp;
    private String commented;
}*/
