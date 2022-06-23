package com.shinsunsu.anofspring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 5000)
    private String rawMaterial;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String natrium;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String carbohydrates;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String sugar;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String trans_fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String saturated_fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String cholesterol;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String protein;

    @Column(nullable = false)
    @ColumnDefault("0")
    private String calorie;

    @Column(nullable = false, length = 50)
    private String capacity;

    @Column(nullable = false, length = 5000)
    private String allergy;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int wheat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int milk;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int buckwheat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int peanut;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int soybean;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int mackerel;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int crab;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int shrimp;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int pork;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int peach;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int tomato;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int walnut;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int chicken;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int beef;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int squid;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int shellfish;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int egg;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int sulfurous_acid;

    @Column(nullable = false, length = 5000)
    private String image;

    @Column(nullable = false, length = 100)
    private String barcode;
}
