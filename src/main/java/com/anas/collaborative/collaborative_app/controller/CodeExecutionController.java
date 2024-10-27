package com.anas.collaborative.collaborative_app.controller;

import com.anas.collaborative.collaborative_app.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping
    public ResponseEntity<?> executeCode(
            @RequestParam String language,
            @RequestBody String code) {
        try {
            // Call executeCode with the className parameter
            String output = codeExecutionService.executeCode(language, code);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
