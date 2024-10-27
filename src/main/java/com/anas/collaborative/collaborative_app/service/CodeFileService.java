package com.anas.collaborative.collaborative_app.service;

import com.anas.collaborative.collaborative_app.entity.CodeFile;
import com.anas.collaborative.collaborative_app.entity.Comment;
import com.anas.collaborative.collaborative_app.repository.CodeFileRepository;
import com.anas.collaborative.collaborative_app.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeFileService {

    @Autowired
    private CodeFileRepository codeFileRepository;

    @Autowired
    private CommentRepository commentRepository;

    public CodeFile updateFileContent(Long fileId, String newContent) {
        CodeFile codeFile = codeFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        codeFile.setContent(newContent);
        return codeFileRepository.save(codeFile);
    }

    public Comment addComment(Long fileId, Comment comment) {
        CodeFile codeFile = codeFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        comment.setCodeFile(codeFile);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long fileId) {
        return commentRepository.findByCodeFileId(fileId);
    }

    public CodeFile createFile(CodeFile codeFile) {
        return codeFileRepository.save(codeFile);
    }

    public CodeFile getFileById(Long id) {
        return codeFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public List<CodeFile> findAll() {   // New implementation to find all files
        return codeFileRepository.findAll();
    }
}
