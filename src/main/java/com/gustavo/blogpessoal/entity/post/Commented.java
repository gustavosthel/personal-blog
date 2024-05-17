package com.gustavo.blogpessoal.entity.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gustavo.blogpessoal.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @JsonIgnoreProperties({"password", "userType", "email",})
    private User user;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime commentTime;
}
