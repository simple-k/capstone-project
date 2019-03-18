package com.capstone.simplek.controllers;

import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Model.User;
import com.capstone.simplek.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

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
//    @GetMapping("search")
//    public String all(Model model) {
//
//    }
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

    @GetMapping("school-results")
    public String schoolResults () {
        return "school-results";
    }
}
