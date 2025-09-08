package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Arrays;

@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String branch;
    private String gender;
    private String skills;
    private LocalDate dob;

    private Integer marks;

    @Lob
    @Column(length = 100000)
    private byte[] photo;

    // Getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }

    // Helper to produce Base64 string for template (avoids using T(...) in Thymeleaf)
    public String getPhotoBase64() {
        return (photo != null && photo.length > 0) ? Base64.getEncoder().encodeToString(photo) : null;
    }
    public String getAgeString() {
        if (dob == null) return "";
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        Period p = Period.between(dob, now);
        return String.format("%d YEARS %d MONTHS %d DAYS", p.getYears(), p.getMonths(), p.getDays());
    }


    // Return count of selected skills (splits by comma, trims blanks)
    public int getSkillsCount() {
        if (skills == null || skills.trim().isEmpty()) return 0;
        return (int) Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .count();
    }
}
