package com.cisco.cisco.controllers;

import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.User;
import com.cisco.cisco.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private  UserService userService;





    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "index";
    }

    @GetMapping(value = "/signin")
    public String signIn(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "signin";
    }

    @GetMapping(value="/profile")
    @PreAuthorize("isAuthenticated()")
    public String deanPanel(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "profile";
    }
    @GetMapping(value="/deanPanel")
    @PreAuthorize("hasAnyRole('ROLE_DEANOFFICE')")
    public String profilePag(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "deanPanel";
    }

    @GetMapping(value = "/accessError")
    public String accessError(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "accesserror";
    }
    @GetMapping(value = "/signup")
    public String signUp(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "signup";
    }
    @PostMapping(value = "/signup")
    public String accessError(@RequestParam(name = "user_fullname")String fullname,
                              @RequestParam(name = "user_email")String email,
                              @RequestParam(name = "user_password")String password,
                              @RequestParam(name = "user_conf_password")String repassword){
       if(password.equals(repassword)){

           User check=userService.findByEmail(email);

           if(check==null) {
               User user = new User();
               user.setEmail(email);
               user.setPassword(password);
               user.setFullName(fullname);
               user.setCourses(null);
               user.setPassword(passwordEncoder.encode(user.getPassword()));
               userService.register(user);

               User newUser = userService.register(user);
               if (newUser != null) {
                   return "redirect:/signin?success";
               }
           }
           return "redirect:/signup?error";
       }
        return "redirect:/signup?errorPass";
    }


    @GetMapping(value = "/courses")
    public String getCourses(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        model.addAttribute("courses",userService.getAllCourses());
        List<Course> courseList=userService.getAllCourses();
        model.addAttribute("courseList",courseList);
        return "courses";
    }

    private User getCurrentUser(){
       Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
       if(!(authentication instanceof AnonymousAuthenticationToken)){
           User user=(User)authentication.getPrincipal();
           return user;
       }
       return null;
    }

    @GetMapping(value = "/profilePage")
    public String profilePage(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "profilePage";
    }
    @PostMapping(value="/assigncourse")
    public String assignCourse(@RequestParam(name = "course_id") Long course_id,
                               @RequestParam(name = "student_id")Long student_id){

        User user=userService.getUser(student_id);
        if(user!=null){
            Course course= userService.getCourse(course_id);
            if(course!=null){
                 List<Course> courseList=userService.getAllCourses();
                 if(courseList==null){
                     courseList=new ArrayList<>();
                 }
                 courseList.add(course);
                 user.setCourses(courseList);
                 userService.saveUser(user);
                 return "redirect:/courses";
            }
        }
        return "redirect:/courses";
    }



}
