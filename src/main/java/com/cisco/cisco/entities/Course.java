package com.cisco.cisco.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private int credits;

    @OneToMany(mappedBy = "course")
    Set<CourseGrade> courseGrade;

    @ManyToMany
    private List<User> users;

    @ManyToOne
    private User teacher;  //my courses view



}
