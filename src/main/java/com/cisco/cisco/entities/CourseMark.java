package com.cisco.cisco.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.standard.expression.Each;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_course_marks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int markValue;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    private User student;

}
