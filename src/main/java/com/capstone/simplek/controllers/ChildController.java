package com.capstone.simplek.controllers;

import com.capstone.simplek.Repository.ChildrenRepository;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import com.capstone.simplek.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class ChildController {

//    collection of repos referring to our database
    @Autowired
    private ChildrenRepository childrenDao;
    @Autowired
    private DistrictRepository districtDao;
    @Autowired
    private SchoolRepository schoolDao;
    @Autowired
    private ServiceRepository serviceDao;

//     requests that interact with our Dao Factory
    @GetMapping("/child-form")
    public String districtFilter (){
        return "child-form";
    }
    @PostMapping("/child-form")
    public String findSchools (@RequestAttribute (name ="address") String address,
                               @RequestAttribute (name ="transportation-yes") boolean transportation,
                               @RequestAttribute (name ="esl-yes") boolean esl)
    {

        return "redirect/school-results";
    }
}
