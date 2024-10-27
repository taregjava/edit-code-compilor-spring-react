package com.anas.collaborative.collaborative_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CodeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "owner_id") // Specifies the foreign key column name
    private User owner;
}
