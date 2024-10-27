package com.anas.collaborative.collaborative_app.repository;

import com.anas.collaborative.collaborative_app.entity.FileLock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileLockRepository extends JpaRepository<FileLock,Long> {
    boolean existsByFileIdAndLineNumber(Long fileId, int lineNumber);

    void deleteByFileIdAndLineNumberAndLockedBy(Long fileId, int lineNumber, String username);
}
