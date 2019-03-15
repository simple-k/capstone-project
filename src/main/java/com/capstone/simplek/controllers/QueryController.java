package com.capstone.simplek.controllers;

import com.capstone.simplek.Repository.ChildrenRepository;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import com.capstone.simplek.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("query")
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
    @GetMapping("/query/index")
    @ModelAttribute("query")
    public String districtFilter (Model model){
        String district = "SELECT *";
        model.addAttribute("district", district);
        return "query/index";
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
