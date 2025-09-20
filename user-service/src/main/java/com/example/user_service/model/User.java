package com.example.user_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection
    @CollectionTable(name = "user_quizzes", joinColumns = @JoinColumn(name = "user_id"))
    private List<Integer> quizIds;

}
