package com.anas.collaborative.collaborative_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class FileLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fileId;
    private int lineNumber;
    private String lockedBy;
    private LocalDateTime lockTime;

    public FileLock(Long fileId, int lineNumber, String username) {
        this.fileId= fileId;
        this.lineNumber= lineNumber;
        this.lockedBy= username;
    }

    // Getters and Setters
}
