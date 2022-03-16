package com.cisco.cisco.services.implementation;

import com.cisco.cisco.entities.CourseMark;
import com.cisco.cisco.repositories.CourseMarkRepository;
import com.cisco.cisco.services.CourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMarkServiceImpl implements CourseMarkService {

    @Autowired
    private CourseMarkRepository courseMarkRepository;

   public CourseMark findCourseMark(Long courseId, Long studentId){
        return courseMarkRepository.findCourseMarkByCourseIdAndStudentId(courseId,studentId);
    }
}
