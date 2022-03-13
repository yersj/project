package com.cisco.cisco.controllers;

import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.CourseGrade;
import com.cisco.cisco.entities.User;
import com.cisco.cisco.services.implementation.UserServiceImpl;
import com.cisco.cisco.services.implementation.CourseGradeServiceImpl;
import com.cisco.cisco.services.implementation.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private CourseGradeServiceImpl courseGradeService;

    @Value("${file.photo.upload}")
    private String uploadPath;

    @Value("${file.photo.upload.target}")
    private String uploadPathTarget;

    @Value("${file.photo.view}")
    private String viewPath;

    @Value("${file.photo.default}")
    private String defaultPhoto;


    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signin";
    }

    @GetMapping(value = "/grade")
    public String gradePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
//        List<CourseGrade> courseGradeList=courseGradeService.getAllByUserId(getCurrentUser().getId());
//        model.addAttribute("user_courses_grades",courseGradeList);


        return "grades";
    }
    @GetMapping(value = "/filter-subject/{courseId}")
    public String studentsBySubject(Model model,
                                    @PathVariable(name="courseId") Long courseId) {
        model.addAttribute("currentUser", getCurrentUser());
//      model.addAttribute("students",userService.getAllStudentsByCourseId(courseId));
        model.addAttribute("courses",courseService.getAllCourses());
//        List<CourseGrade> courseGradeList=courseGradeService.getAllByUserId(getCurrentUser().getId());
//        model.addAttribute("user_courses_grades",courseGradeList);


        return "grading";
    }


    @GetMapping(value = "/signin")
    public String signIn(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signin";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String deanPanel(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "profile";
    }



    @GetMapping(value = "/teacherCourses")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_DEANOFFICE')")
    public String getCourseForTeacher(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        User user = getCurrentUser();
        model.addAttribute("courses", user.getCourses());
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("courseList", courseList);
        return "teacherCourse";
    }

    @GetMapping(value = "/accessError")
    public String accessError(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "accesserror";
    }

    @GetMapping(value = "/signup")
    public String signUp(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String accessError(@RequestParam(name = "user_fullname") String fullname,
                              @RequestParam(name = "user_email") String email,
                              @RequestParam(name = "user_password") String password,
                              @RequestParam(name = "user_conf_password") String repassword,
                              @RequestParam(name = "userPhoto") MultipartFile file) {
        if (password.equals(repassword)) {

            String userPhoto = uploadPhoto(file);

            User check = userService.findByEmail(email);

            if (check == null) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setFullName(fullname);
                user.setCourses(null);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setPhoto(userPhoto);
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
    public String getCourses(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        User user = getCurrentUser();
        model.addAttribute("courses", user.getCourses());
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("courseList", courseList);
        return "courses";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    @GetMapping(value = "/profilePage")
    public String profilePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "profilePage";
    }

    @PostMapping(value = "/assigncourse")
    public String assignCourse(@RequestParam(name = "course_id") Long course_id,
                               @RequestParam(name = "student_id") Long student_id) {

        User user = userService.getUser(student_id);
        if (user != null) {
            Course course = courseService.getCourse(course_id);
            if (course != null) {
                List<Course> courseList = user.getCourses(); //
                if (courseList == null) {
                    courseList = new ArrayList<>();
                }

                user.setCourseGrade(null);
                courseList.add(course);
                user.setCourses(courseList);


                CourseGrade courseGrade = new CourseGrade();
                courseGrade.setStudent(user);
                courseGrade.setCourse(course);
                courseGrade.setGrade(0);
                courseGradeService.save(courseGrade);

//                 user.setCourses(courseList);
                userService.saveUser(user);
                return "redirect:/courses";
            }
        }
        return "redirect:/courses";
    }

    @GetMapping(value = "/view-photo/{photoName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] viewStudentPhoto(@PathVariable(name = "photoName") String photoName) throws IOException {
        String photoUrl = viewPath + defaultPhoto;
        if (photoName != null) {
            photoUrl = viewPath + photoName + ".jpg";
        }
        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource(photoUrl);
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(viewPath + defaultPhoto);
            inputStream = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(inputStream);
    }

    public String uploadPhoto(MultipartFile file) {
        String photoName = DigestUtils.shaHex("photo" + file.getOriginalFilename());

        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {

            try {
                Path path = Paths.get(uploadPath + photoName + ".jpg");
                Path target = Paths.get(uploadPathTarget + photoName + ".jpg");
                byte[] bytes = file.getBytes();
                Files.write(path, bytes);
                Files.write(target, bytes);
                return photoName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
