package com.cisco.cisco.services;

import com.cisco.cisco.entities.CourseMark;
import com.cisco.cisco.entities.User;

import java.util.List;

public interface CourseMarkService {
    CourseMark findCourseMark(Long courseId, Long studentId);
    public void save(CourseMark courseMark);
    public List<CourseMark> findCourseMarkByStudentId(Long student_id);
    public void deleteCourseOfStudent(Long course_id,Long student_id);
    List<CourseMark> findCourseMarkByCourseId(Long course_id);
}
