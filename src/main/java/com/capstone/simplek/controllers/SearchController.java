package com.capstone.simplek.controllers;

import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Model.School;
import com.capstone.simplek.Model.User;
import com.capstone.simplek.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {

//    collection of repos referring to our database
    @Autowired
    private ChildrenRepository childrenDao;
    @Autowired
    private DistrictRepository districtDao;
    @Autowired
    private SchoolRepository schoolDao;
    @Autowired
    private ServiceRepository serviceDao;
    @Autowired
    private UserRepository userDao;

//     requests that interact with our Dao Factory
    @GetMapping("/school-results")
    public String all(Model model) {
        List<School> schools = schoolDao.findAll();
        model.addAttribute("schools", schools);
        return "school-results";
    }
    @GetMapping("/search/create")
    public String viewSearchQuery (){
        return "search";
    }
    @PostMapping("/search/create")
    public String createSearchQuery (@PathVariable long id,
                                     @RequestAttribute (name ="address") String address,
                                     @RequestAttribute (name ="transportation-yes") boolean transportation,
                                     @RequestAttribute (name ="esl-yes") boolean esl,
                                     @RequestAttribute (name ="idea-yes") boolean idea)
    {
            User user = userDao.findOne(id);
            user.setAddress(address);
            Children child = new Children();


        return "redirect:/school-results";
    }

//    @GetMapping("school-results")
//    public String schoolResults () {
//        return "school-results";
//    }
}
