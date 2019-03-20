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
                User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User copyUser = userDao.findOne(loggedInUser.getId());
                model.addAttribute("user", copyUser);
                model.addAttribute("child", new Children());
                model.addAttribute("query", new Query());
            }
        }
        return "search";
    }

    @PostMapping("/search/query")
    public String createSearchQuery (@ModelAttribute Children child, @ModelAttribute User user, @ModelAttribute Query query, HttpSession session) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User copyUser = userDao.findOne(loggedInUser.getId());

        boolean userInputHasErrors = user.getuFirstName().isEmpty()
                || user.getuLastName().isEmpty()
                || user.getEmail().isEmpty()
                || user.getAddress().isEmpty()
                || user.getZipCode().isEmpty()
                || user.getPhoneNumber().isEmpty();

        // check if email input is empty
        if (userInputHasErrors) {
            session.setAttribute("inputErrors", "Invalid inputs!");
            return "redirect:/search/query";
        // check if email matches current email in the database
        } else if (user.getEmail().equals(copyUser.getEmail())){
            //update user with same email input as database
            copyUser.setuFirstName(user.getuFirstName());
            copyUser.setuLastName(user.getuLastName());
            copyUser.setPhoneNumber(user.getPhoneNumber());
            copyUser.setAddress(user.getAddress());
            copyUser.setZipCode(user.getZipCode());
            userDao.save(copyUser);
        }
        else {
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

        // update child
        child.setUser(copyUser);
        child.setFirstName(child.getFirstName());
        child.setLastName(child.getLastName());
        child.setDob(child.getDob());
        child.setGender(child.getGender());
        childrenDao.save(child);

        // set session attribute for search results
        session.setAttribute("query", query);
        session.setAttribute("user", copyUser);
        session.setAttribute("child", child);

        return "redirect:/search";
    }


//    @PostMapping("/search/query")
//    public String createSearchQuery (@PathVariable long id,
//                                     @RequestAttribute (name ="address") String address,
//                                     @RequestAttribute (name ="transportation-yes") boolean transportation,
//                                     @RequestAttribute (name ="esl-yes") boolean esl,
//                                     @RequestAttribute (name ="idea-yes") boolean idea)
//    {
//            User user = userDao.findOne(id);
//            user.setAddress(address);
//            Children child = new Children();
//
//
//        return "redirect:/search";
//    }

//    @GetMapping("school-results")
//    public String schoolResults () {
//        return "school-results";
//    }

}
