package com.shinsunsu.anofspring.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
