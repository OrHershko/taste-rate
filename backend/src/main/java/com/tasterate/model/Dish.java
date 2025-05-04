package com.tasterate.model;

import javax.persistence.*;

@Entity // Marks this class as a JPA entity (maps to a database table)
public class Dish {

    @Id  //Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    private String name;
    private String description;

    // Getters & Setters
}
