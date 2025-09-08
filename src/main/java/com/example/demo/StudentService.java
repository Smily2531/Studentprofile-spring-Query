package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        return repo.findById(id).orElse(null);
    }

    /**
     * Returns a list sorted in-memory:
     * - if field == "marks" -> compare by marks (null-safe)
     * - if field == "skills" -> compare by number of selected skills (skillsCount)
     * direction = "asc" or "desc"
     */
    public List<StudentEntity> getAllSorted(String field, String direction) {
        List<StudentEntity> all = repo.findAll();

        Comparator<StudentEntity> comparator;
        if ("skills".equalsIgnoreCase(field)) {
            comparator = Comparator.comparingInt(StudentEntity::getSkillsCount);
        } else { // default to marks
            comparator = Comparator.comparingInt(s -> s.getMarks() == null ? Integer.MIN_VALUE : s.getMarks());
        }

        if ("desc".equalsIgnoreCase(direction)) comparator = comparator.reversed();

        return all.stream()
                  .sorted(comparator)
                  .collect(Collectors.toList());
    }
}
