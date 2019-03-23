package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.District;
import com.capstone.simplek.Model.School;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestWebController {

    @Autowired
    private SchoolRepository schoolDao;
    @Autowired
    private DistrictRepository districtDao;

    @GetMapping("/api/school/selected")
    public @ResponseBody List<Object> getSelectedSchool(@RequestParam("schoolId") long schoolId) {
        School school = schoolDao.getById(schoolId);
        Object district = districtDao.findById(school.getDistrict().getId());

        List<Object> object = new ArrayList<>();
        object.add(school);
        object.add(district);

        return object;
    }

    @GetMapping("/api/school/all")
    public @ResponseBody List<Object> getAllSchools() {
        List<School> schools = schoolDao.findAll();
        List<Object> district = districtDao.findall();

        List<Object> object = new ArrayList<>();
        object.add(schools);
        object.add(district);

        return object;
    }

    @GetMapping("/api/district")
    public @ResponseBody List<District> getAllDisrtrict() {
        List<District> districts = districtDao.findAll();
        return districts;
    }

}