package com.gustavo.blogpessoal.entity.post;

import com.gustavo.blogpessoal.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentId")
public class Commented {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID commentId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDateTime commentTime;
}
