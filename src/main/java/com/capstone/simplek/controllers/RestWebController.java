package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.District;
import com.capstone.simplek.Model.School;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RestWebController {

    @Autowired
    private SchoolRepository schoolDao;
    @Autowired
    private DistrictRepository districtDao;

    @GetMapping("/api/school/all")
    public @ResponseBody List<School> getAllSchools() {
        List<School> schools = schoolDao.findAll();
        return schools;
    }

    @GetMapping("/api/school/{schoolId}")
    public @ResponseBody School getSelectedSchool(@PathVariable("schoolId") long schoolId) {
        School school = schoolDao.findById(schoolId);
        return school;
    }

    @GetMapping("/api/district/all")
    public @ResponseBody List<District> getAllDisrtrict() {
        List<District> district = districtDao.findAll();
        return district;
    }

    @GetMapping("/api/district/{districtId}")
    public @ResponseBody List<School> getAllSchoolsByDistrictId(@PathVariable("districtId") long districtId) {
        List<School> schools = schoolDao.findAllSchoolsByDistrictId(districtId);
        return schools;
    }

}