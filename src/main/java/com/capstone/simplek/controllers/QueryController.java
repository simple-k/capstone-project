package com.capstone.simplek.controllers;

import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Repository.ChildrenRepository;
import com.capstone.simplek.Repository.DistrictRepository;
import com.capstone.simplek.Repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueryController {

//    collection of repos referring to database for queries and sub queries


//    private final ChildrenRepository childrenDao;
//    public QueryController(ChildrenRepository childrenDao) {this.childrenDao = childrenDao;}
//
//    private final DistrictRepository districtDao;
//    public QueryController(DistrictRepository districtDao) {this.districtDao = districtDao;}

    @GetMapping("/query")
    public String stepOne (){
        return "query";
    }
}
