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
    private int natrium;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int carbohydrates;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int sugar;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int trans_fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int saturated_fat;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int cholesterol;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int protein;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int calorie;

    @Column(nullable = false, length = 50)
    private String capacity;

    @Column(nullable = false, length = 5000)
    private String allergy;

    @Column(nullable = false, length = 5000)
    private String image;

    @Column(nullable = false, length = 100)
    private String barcode;
}
