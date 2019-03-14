package com.capstone.simplek.controllers;

import com.capstone.simplek.Repository.ChildrenRepository;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import com.capstone.simplek.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueryController {

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
    @GetMapping("/query/district")
    public String districtFilter (){
        return "query/district";
    }

    @GetMapping("/query/transportation")
    public String transportation (){
        return "query/transportation";
    }

    @GetMapping("/query/disabilities")
    public String disabilities (){
        return "query/disabilities";
    }
}
