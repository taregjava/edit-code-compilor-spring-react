package com.anas.collaborative.collaborative_app.repository;

import com.anas.collaborative.collaborative_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCodeFileId(Long fileId);
}
