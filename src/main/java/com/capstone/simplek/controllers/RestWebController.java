package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.School;
import com.capstone.simplek.Repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestWebController {

    @Autowired
    private SchoolRepository schoolDao;

    @GetMapping("/api/school/selected")
    public School getSelectedSchool(@RequestParam("schoolId") long schoolId) {
        School school = schoolDao.getById(schoolId);
        return school;
    }

}