package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.Children;
import com.capstone.simplek.Model.Query;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {

    // collection of repos referring to our database
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

     // requests that interact with our Dao Factory
    @GetMapping("/search")
    public String all(Model model) {
        List<School> schools = schoolDao.findAll();
        model.addAttribute("schools", schools);
        return "search/index";
    }

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
                model.addAttribute("query", new Query());
                if (copyChild != null) {
                    model.addAttribute("child", copyChild);
                } else {
                    model.addAttribute("child", new Children());
                }
            }
        }
        return "search";
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
                || user.getEmail().isEmpty()
                || user.getAddress().isEmpty()
//                || user.getZipCode().isEmpty()
                || user.getPhoneNumber().isEmpty();

        // check if inputs are empty
        if (userInputHasErrors) {
            session.setAttribute("inputErrors", "Invalid inputs!");
            return "redirect:/search/query";
        // check if email matches current user email in the database
        } else if (user.getEmail().equals(copyUser.getEmail())){
            //update user with same email input as database
            copyUser.setuFirstName(user.getuFirstName());
            copyUser.setuLastName(user.getuLastName());
            copyUser.setPhoneNumber(user.getPhoneNumber());
            copyUser.setAddress(user.getAddress());
            copyUser.setZipCode(user.getZipCode());
            userDao.save(copyUser);
        } else {
        // check if email already exist in the users table
            User checkEmail = userDao.findByEmail(user.getEmail());
            if (checkEmail != null) {
                session.setAttribute("inputErrors", "Email already exist!");
                return "redirect:/search/query";
            }
        }// email check

        // update user
        copyUser.setuFirstName(user.getuFirstName());
        copyUser.setuLastName(user.getuLastName());
        copyUser.setEmail(user.getEmail());
        copyUser.setPhoneNumber(user.getPhoneNumber());
        copyUser.setAddress(user.getAddress());
        copyUser.setZipCode(user.getZipCode());
        userDao.save(copyUser);

        // save/update child
        if (copyChild != null
                && copyChild.getFirstName().equals(child.getFirstName())
                && copyChild.getLastName().equals(child.getLastName())) {
            copyChild.setUser(copyUser);
            copyChild.setFirstName(child.getFirstName());
            copyChild.setLastName(child.getLastName());
            copyChild.setDob(child.getDob());
            copyChild.setGender(child.getGender());
            childrenDao.save(copyChild);
            // set session attribute for child
            session.setAttribute("child", copyChild);
        } else {
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
        session.setAttribute("query", query);

        return "redirect:/search";

    }// createSearchQuery

//    @GetMapping("school-results")
//    public String schoolResults () {
//        return "school-results";
//    }

}// class