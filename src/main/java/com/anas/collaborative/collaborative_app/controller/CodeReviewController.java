package com.anas.collaborative.collaborative_app.controller;

import com.anas.collaborative.collaborative_app.entity.Comment;
import com.anas.collaborative.collaborative_app.service.CodeFileService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@Slf4j
public class CodeReviewController {

    @Autowired
    private CodeFileService codeFileService;

    @PostMapping("/{fileId}/comment")
    public ResponseEntity<?> addComment(@PathVariable Long fileId, @RequestBody Comment comment) {
        log.info("Received comment for file ID {}: {}", fileId, comment);
        return ResponseEntity.ok(codeFileService.addComment(fileId, comment));
    }

    @GetMapping("/{fileId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long fileId) {
        return ResponseEntity.ok(codeFileService.getComments(fileId));
    }
}
