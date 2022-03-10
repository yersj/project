package com.cisco.cisco.repositories;

import com.cisco.cisco.entities.CourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CourseGradeRepository extends JpaRepository<CourseGrade,Long> {

}
