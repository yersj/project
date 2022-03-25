package com.cisco.cisco.repositories;

import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.CourseMark;
import com.cisco.cisco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseMarkRepository extends JpaRepository<CourseMark, Long> {

    CourseMark findCourseMarkByCourseIdAndStudentId(Long courseId,Long studentId);


    List<CourseMark> findCourseMarkByStudentId(Long student_id);

    List<CourseMark> findCourseMarkByCourseId(Long course_id);

    public void deleteCourseMarkByCourseIdAndStudentId(Long course_id,Long student_id);


}
