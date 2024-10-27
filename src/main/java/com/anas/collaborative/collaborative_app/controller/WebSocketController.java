package com.anas.collaborative.collaborative_app.controller;

import com.anas.collaborative.collaborative_app.entity.CodeFile;
import com.anas.collaborative.collaborative_app.service.CodeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private CodeFileService codeFileService;

    // Single method to handle file edits
    @MessageMapping("/edit/{fileId}")
    @SendTo("/topic/files/{fileId}")
    public CodeFile handleFileEdit(@DestinationVariable Long fileId, String content) {
        try {
            return codeFileService.updateFileContent(fileId, content);
        } catch (Exception e) {
            throw new RuntimeException("Error updating file content", e);
        }
    }


}
