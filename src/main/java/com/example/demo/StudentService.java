package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class StudentService {
    private final StudentRepo repo;

    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    public StudentEntity saveStudent(StudentEntity s, MultipartFile photoFile) throws IOException {
        if (photoFile != null && !photoFile.isEmpty()) {
            s.setPhoto(photoFile.getBytes());
        }
        return repo.save(s);
    }

    public StudentEntity getById(Integer id) {
        return repo.getById(id);
    }
}
