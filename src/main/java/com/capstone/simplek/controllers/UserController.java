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
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, HttpSession session) {

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

        // check if user already exist in the users table
        User checkUserName = userDao.findByUsername(user.getUsername());
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

        // hash password before saving to database
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        // save new user to database
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user/profile")
    public String viewProfile(Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", currentUser);
        return "user/profile";
    }

    @GetMapping("/user/edit")
    public String editProfile(Model model,
                              @ModelAttribute User user) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", currentUser);

        return "user/edit";
    }

    @PostMapping("/user/edit")
    public String updateProfile (Model model, HttpSession session,
                                 @ModelAttribute User user) {
//        updateUser is necessary for Spring Boot Security to immediately register/display database changes
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User updateUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", updateUser);

//        checks if user form inputs already exist in the database
        User userAlreadyExists = userDao.findByUsername(user.getUsername());
        User emailAlreadyRegistered = userDao.findByEmail(user.getEmail());
        boolean emptyInput = user.getUsername().isEmpty()
                || user.getEmail().isEmpty()
                || user.getAddress().isEmpty()
                || user.getZipCode().isEmpty()
                || user.getPhoneNumber().isEmpty();


//        creates error messages if any of the user form inputs are already taken
        if (userAlreadyExists != null)  { session.setAttribute("userError", "This username is already taken");}
        if (emailAlreadyRegistered != null)  {session.setAttribute("emailError", "This email address has already been registered");}
        if (emptyInput){ session.setAttribute("emptyError", "One or more required fields is empty.");}

        boolean inputHasErrors = (userAlreadyExists!=null || emailAlreadyRegistered!=null || emptyInput);

        if (!inputHasErrors){
                updateUser.setUsername(user.getUsername());
                updateUser.setEmail(user.getEmail());
                updateUser.setAddress(user.getAddress());
                updateUser.setZipCode(user.getZipCode());
                updateUser.setPhoneNumber(user.getPhoneNumber());
                userDao.save(updateUser);
                return "redirect:/user/profile";
        } else {
            return "redirect:/user/edit";
        }
    }

}