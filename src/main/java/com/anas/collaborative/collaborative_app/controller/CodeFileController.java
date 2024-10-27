package com.anas.collaborative.collaborative_app.controller;
import com.anas.collaborative.collaborative_app.entity.CodeFile;
import com.anas.collaborative.collaborative_app.service.CodeFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class CodeFileController {
    private static final Logger logger = LoggerFactory.getLogger(CodeFileController.class);

    @Autowired
    private CodeFileService codeFileService;


    @PostMapping("/create")
    public ResponseEntity<CodeFile> createFile(@RequestBody CodeFile codeFile) {
        logger.info("Creating file with name: " + codeFile.getFilename());
        CodeFile createdFile = codeFileService.createFile(codeFile);
        return new ResponseEntity<>(createdFile, HttpStatus.CREATED);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<CodeFile> getFileById(@PathVariable Long fileId) {
        CodeFile file = codeFileService.getFileById(fileId);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
    @PostMapping("/{fileId}/update")
    public ResponseEntity<CodeFile> updateFileContent(@PathVariable Long fileId, @RequestBody String newContent) {
        CodeFile updatedFile = codeFileService.updateFileContent(fileId, newContent);
        return ResponseEntity.ok(updatedFile);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CodeFile>> getAllFiles() {
        List<CodeFile> files = codeFileService.findAll();
        return ResponseEntity.ok(files);
    }
}
