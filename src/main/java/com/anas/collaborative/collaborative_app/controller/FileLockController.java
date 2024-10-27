package com.anas.collaborative.collaborative_app.controller;

import com.anas.collaborative.collaborative_app.service.FileLockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/lock")
public class FileLockController {

    @Autowired
    private FileLockService fileLockService;

    @PostMapping("/{fileId}/line/{lineNumber}")
    public ResponseEntity<?> lockLine(@PathVariable Long fileId, @PathVariable int lineNumber, Principal principal) {
        return ResponseEntity.ok(fileLockService.lockLine(fileId, lineNumber, principal.getName()));
    }

    @DeleteMapping("/{fileId}/line/{lineNumber}")
    public ResponseEntity<?> unlockLine(@PathVariable Long fileId, @PathVariable int lineNumber, Principal principal) {
        fileLockService.unlockLine(fileId, lineNumber, principal.getName());
        return ResponseEntity.ok("delete success");
    }
}
