package com.cisco.cisco.repositories;

import com.cisco.cisco.entities.CourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseGradeRepository extends JpaRepository<CourseGrade,Long> {


}
