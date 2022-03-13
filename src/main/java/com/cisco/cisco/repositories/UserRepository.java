package com.cisco.cisco.repositories;


import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@EnableJpaRepositories
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);




}
