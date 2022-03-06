package com.cisco.cisco.repositories;

import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllById(Long id);

}
