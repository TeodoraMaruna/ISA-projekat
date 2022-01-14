package com.application.bekend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;
    
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<MyUser> users= new HashSet<MyUser>();

    public UserCategory(Integer id, int points, String name, int discountPercentage) {
        this.id = id;
        this.points = points;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public UserCategory() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscountPercentage() {
        return this.discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

}