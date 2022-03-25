package com.cisco.cisco.services.implementation;

import com.cisco.cisco.entities.CourseMark;
import com.cisco.cisco.repositories.CourseMarkRepository;
import com.cisco.cisco.services.CourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseMarkServiceImpl implements CourseMarkService {

    @Autowired
    private CourseMarkRepository courseMarkRepository;

   public CourseMark findCourseMark(Long courseId, Long studentId){
        return courseMarkRepository.findCourseMarkByCourseIdAndStudentId(courseId,studentId);
    }
    public void save(CourseMark courseMark){
        courseMarkRepository.save(courseMark);
    }

   public List<CourseMark> findCourseMarkByStudentId(Long student_id){
       return courseMarkRepository.findCourseMarkByStudentId(student_id);
    }
    public void deleteCourseOfStudent(Long course_id,Long student_id){
       courseMarkRepository.deleteCourseMarkByCourseIdAndStudentId(course_id,student_id);
    }

    public List<CourseMark> findCourseMarkByCourseId(Long course_id){
       return courseMarkRepository.findCourseMarkByCourseId(course_id);
    }

}
