package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Model.Query;
import com.capstone.simplek.Model.User;
import com.capstone.simplek.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class SearchController {

    // collection of repos referring to our database
    @Autowired
    private ChildrenRepository childrenDao;
    @Autowired
    private SchoolRepository schoolDao;
    @Autowired
    private DistrictRepository districtDao;
    @Autowired
    private UserRepository userDao;

     // requests that interact with our Dao Factory
    @GetMapping("/search")
    public String all(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated;
        if (authentication != null) {
            isAuthenticated = authentication instanceof AnonymousAuthenticationToken ? false
                    : authentication.isAuthenticated();
            if (isAuthenticated) {
                User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User copyUser = userDao.findOne(loggedInUser.getId());
                model.addAttribute("user", copyUser);
                model.addAttribute("queries", new Query());
            }
        }
        model.addAttribute("schools", schoolDao.findAll());
        model.addAttribute("districts", districtDao.findAll());

        // TODO Add filters here

        return "search/index";
    }

//    @GetMapping("/search/{id}")
//    public String getSchoolById(@PathVariable long id, Model model) {
//
//        return "search/index";
//    }

    @GetMapping("/search/query")
    public String viewSearchQueryForm (Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated;
        if (authentication != null) {
            isAuthenticated = authentication instanceof AnonymousAuthenticationToken ? false
                    : authentication.isAuthenticated();
            if (isAuthenticated) {
                User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User copyUser = userDao.findOne(loggedInUser.getId());
                Children copyChild = childrenDao.getFirstChild(copyUser.getId());
                model.addAttribute("user", copyUser);
                model.addAttribute("queries", new Query());
                if (copyChild != null) {
                    model.addAttribute("child", copyChild);
                } else {
                    model.addAttribute("child", new Children());
                }
            }
        }
        return "search/search";
    }// viewSearchQueryForm

    @PostMapping("/search/query")
    public String createSearchQuery (@ModelAttribute Children child,
                                     @ModelAttribute User user,
                                     @ModelAttribute Query query,
                                     HttpSession session) {

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User copyUser = userDao.findOne(loggedInUser.getId());
        Children copyChild = childrenDao.getFirstChild(copyUser.getId());

        boolean userInputHasErrors = user.getuFirstName().isEmpty()
                || user.getuLastName().isEmpty()
                || user.getAddress().isEmpty()
                || user.getPhoneNumber().isEmpty();
//                || user.getZipCode().isEmpty()

        // check if user inputs are empty
        if (userInputHasErrors) {
            session.setAttribute("inputErrors", "Invalid inputs!");
            return "redirect:/search/query";
        }

        // update user
        copyUser.setuFirstName(user.getuFirstName());
        copyUser.setuLastName(user.getuLastName());
        copyUser.setPhoneNumber(user.getPhoneNumber());
        copyUser.setAddress(user.getAddress());
//        copyUser.setZipCode(user.getZipCode());
        userDao.save(copyUser);

        boolean childInputHasErrors = child.getFirstName().isEmpty()
                || child.getLastName().isEmpty()
                || child.getDob() == null
                || Character.isSpaceChar(child.getGender());

        // check if child inputs are empty
        if (childInputHasErrors) {
            session.setAttribute("inputErrors", "Invalid inputs!");
            return "redirect:/search/query";
        }

        // check if child exist in database
        if (copyChild != null
                && copyChild.getFirstName().equals(child.getFirstName())
                && copyChild.getLastName().equals(child.getLastName())) {
            // update child info
            copyChild.setUser(copyUser);
            copyChild.setFirstName(child.getFirstName());
            copyChild.setLastName(child.getLastName());
            copyChild.setDob(child.getDob());
            copyChild.setGender(child.getGender());
            childrenDao.save(copyChild);
            // set session attribute for child
            session.setAttribute("child", copyChild);
        } else {
            // if child does not exist in database
            // save new child
            child.setUser(copyUser);
            child.setFirstName(child.getFirstName());
            child.setLastName(child.getLastName());
            child.setDob(child.getDob());
            child.setGender(child.getGender());
            childrenDao.save(child);
            // set session attribute for child
            session.setAttribute("child", child);
        }// save/update child

        // set session attribute for search results
        session.setAttribute("queries", query);

        return "redirect:/search";

    }// createSearchQuery

//    @GetMapping("school-results")
//    public String schoolResults () {
//        return "school-results";
//    }

}// class