package com.capstone.simplek.controllers;
import com.capstone.simplek.Model.User;
import com.capstone.simplek.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return"user/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, HttpSession session){

        boolean userInputHasErrors = user.getUsername().isEmpty()
                || user.getEmail().isEmpty()
                || user.getPassword().isEmpty();

        // validate user input
        if (userInputHasErrors) {
            session.setAttribute("inputErrors", "Invalid inputs!");
            return "redirect:/register";
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            session.setAttribute("inputErrors", "Password did not match!");
            return "redirect:/register";
        }

        User checkUserName = userDao.findByUsername(user.getUsername());
        System.out.println("Testing : " + checkUserName);

        // check if user already exist in the users table
        if (checkUserName != null) {
            session.setAttribute("inputErrors", "Username already exist!");
            return "redirect:/register";
        }

        // check if email already exist in the users table
        User checkEmail = userDao.findByEmail(user.getEmail());
        if (checkEmail != null) {
            session.setAttribute("inputErrors", "Email already exist!");
            return "redirect:/register";
        }

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user/profile")
    public String viewProfile (Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", currentUser);
        return "user/profile";
    }
    @GetMapping("/user/edit")
    public String editProfile (Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", sessionUser);
        return "user/edit";
    }
    @PostMapping("/user/edit")
    public String updateProfile (Model model,
                                 @RequestParam(name = "address") String address,
                                 @RequestParam(name = "username") String username)
    {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User updateUser = userDao.findOne(sessionUser.getId());
        updateUser.setUsername(username);
        updateUser.setAddress(address);
        updateUser.setUsername(username);
        userDao.save(updateUser);

//        model.addAttribute("user", updateUser);

        return "redirect:/user/profile";
    }

}// class