package com.cisco.cisco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "course_grade")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CourseGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int grade;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User student;

    @ManyToOne
    Course course;
}
