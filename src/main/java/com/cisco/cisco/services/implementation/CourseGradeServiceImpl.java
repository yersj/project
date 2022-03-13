package com.cisco.cisco.services.implementation;

import com.cisco.cisco.entities.CourseGrade;
import com.cisco.cisco.repositories.CourseGradeRepository;
import com.cisco.cisco.services.CourseGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseGradeServiceImpl implements CourseGradeService {
    @Autowired
    private CourseGradeRepository courseGradeRepository;


    public void save(CourseGrade courseGrade) {
        courseGradeRepository.save(courseGrade);
    }

//    @Override
//    public List<CourseGrade> getAllByUserId(Long id) {
//        return courseGradeRepository.findAllByUserId(id);
//    }
}
