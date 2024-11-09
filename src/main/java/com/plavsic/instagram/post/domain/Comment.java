package com.plavsic.instagram.post.domain;

import com.plavsic.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Post post;
    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    private User createdBy;
}
