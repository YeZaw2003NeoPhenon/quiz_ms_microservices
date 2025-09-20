package com.example.question_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    @Column(name = "answer")
    private String rightAnswer;

    private String category;

}
