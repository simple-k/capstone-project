package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Model.School;
import com.capstone.simplek.Model.User;
import com.capstone.simplek.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/search")
    public String all(Model model) {
        List<School> schools = schoolDao.findAll();
        model.addAttribute("schools", schools);
        return "search/index";
    }

    @GetMapping("/search/query")
    public String viewSearchQuery (Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated;
        if (authentication != null) {
            isAuthenticated = authentication instanceof AnonymousAuthenticationToken ? false
                    : authentication.isAuthenticated();
            if (isAuthenticated) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                model.addAttribute("user", user);
                List<Children> children = childrenDao.getAllChildren(user.getId());
                model.addAttribute("children", children);
            }
        }

        return "search";
    }


    @PostMapping("/search/query")
    public String createSearchQuery (@PathVariable long id,
                                     @RequestAttribute (name ="address") String address,
                                     @RequestAttribute (name ="transportation-yes") boolean transportation,
                                     @RequestAttribute (name ="esl-yes") boolean esl,
                                     @RequestAttribute (name ="idea-yes") boolean idea)
    {
            User user = userDao.findOne(id);
            user.setAddress(address);
            Children child = new Children();


        return "redirect:/search";
    }

//    @GetMapping("school-results")
//    public String schoolResults () {
//        return "school-results";
//    }

}
