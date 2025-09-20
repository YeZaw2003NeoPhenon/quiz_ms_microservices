package com.example.quiz_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "quiz")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ElementCollection
    @CollectionTable(name = "quiz_questions", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "question_id")
    private List<Integer> questionIds;

    private Integer userId;
}
