package com.cisco.cisco.repositories;


import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query(value = "Select * from user join user_courses on id=user_id where courses_id=:id",nativeQuery = true)
        List<User> findAllByCourseId(Long id);






}
