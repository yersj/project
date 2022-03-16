package com.cisco.cisco.services;

import com.cisco.cisco.entities.CourseMark;

public interface CourseMarkService {
    CourseMark findCourseMark(Long courseId, Long studentId);
}
