package com.cisco.cisco.services.implementation;

import com.cisco.cisco.entities.Course;
import com.cisco.cisco.repositories.CourseRepository;
import com.cisco.cisco.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course getCourse(Long id){
        return courseRepository.findById(id).orElse(null);
    }
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}
