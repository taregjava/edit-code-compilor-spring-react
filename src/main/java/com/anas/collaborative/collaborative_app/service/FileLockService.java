package com.anas.collaborative.collaborative_app.service;

import com.anas.collaborative.collaborative_app.entity.FileLock;
import com.anas.collaborative.collaborative_app.repository.FileLockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileLockService {

    @Autowired
    private FileLockRepository fileLockRepository;

    public boolean lockLine(Long fileId, int lineNumber, String username) {
        if (fileLockRepository.existsByFileIdAndLineNumber(fileId, lineNumber)) {
            return false;  // Line is already locked
        }
        FileLock lock = new FileLock(fileId, lineNumber, username);
        fileLockRepository.save(lock);
        return true;
    }

    public void unlockLine(Long fileId, int lineNumber, String username) {
        fileLockRepository.deleteByFileIdAndLineNumberAndLockedBy(fileId, lineNumber, username);
    }
}
