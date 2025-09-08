package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentEntity, Integer> {
    // JpaRepository already provides:
    // - save()
    // - findById()
    // - findAll(Sort sort)
}
