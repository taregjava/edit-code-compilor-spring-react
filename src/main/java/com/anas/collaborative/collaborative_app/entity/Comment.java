package com.anas.collaborative.collaborative_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CodeFile codeFile;

    private int lineNumber;
    private String content;
    private String author;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
