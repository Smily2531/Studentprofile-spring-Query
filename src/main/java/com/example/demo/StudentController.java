package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Base64;
import java.util.Locale;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("student", new StudentEntity());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegister(@ModelAttribute StudentEntity student,
                                 @RequestParam("photoFile") MultipartFile photoFile,
                                 Model model) throws IOException {
        service.saveStudent(student, photoFile);
        model.addAttribute("student", student);
        if (student.getPhoto() != null) {
            String base64Image=Base64.getEncoder().encodeToString(student.getPhoto());
            model.addAttribute("photoBase64", base64Image);
        }
        return "register-success";
    }

    @GetMapping("/view/{id}")
    public String viewById(@PathVariable Integer id, Model model) {
        StudentEntity s = service.getById(id);
        if (s == null) {
            model.addAttribute("error", "Student not found with id " + id);
            return "view";
        }
        model.addAttribute("student", s);

        if (s.getDob() != null) {
            String dobFormatted = formatDobAsRequested(s.getDob());
            model.addAttribute("dobFormatted", dobFormatted);
        }

        if (s.getPhoto() != null) {
            String base64Image = Base64.getEncoder().encodeToString(s.getPhoto());
            model.addAttribute("photoBase64", base64Image);
        }

        return "view";
    }

    private String formatDobAsRequested(LocalDate dob) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        Period p = Period.between(dob, now);
        int years = p.getYears();
        String monthName = dob.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
        int year = dob.getYear();
        return String.format("%d YEARS %s MONTH %d", years, monthName, year);
    }
}
