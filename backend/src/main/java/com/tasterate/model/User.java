package com.tasterate.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String id;

    private String displayName;
    private String email;
    private String profileImageUrl;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
