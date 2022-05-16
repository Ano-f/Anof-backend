package com.shinsunsu.anofspring.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}
