package com.cisco.cisco.services;

import com.cisco.cisco.entities.Course;

import java.util.List;

public interface CourseService {

    public Course getCourse(Long id);

    public List<Course> getAllCourses();
    public List<Course> findTeacherCourses(Long teacher_id);
}
