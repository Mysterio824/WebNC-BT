package com.webnc.bt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.webnc.bt.enums.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film")
@Getter
@Setter
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false)
    private Integer filmId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "release_year")
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false)
    private Short rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "`length`")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Column(name = "rating", length = 10)
    private Rating rating;

    @Lob
    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "last_update", nullable = false)
    private Instant lastUpdate;
    @ManyToMany
    @JoinTable(
            name = "film_actor", // The join table
            joinColumns = @JoinColumn(name = "film_id"), // This side
            inverseJoinColumns = @JoinColumn(name = "actor_id") // The other side
    )
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "film_category", // The join table from the error
            joinColumns = @JoinColumn(name = "film_id"), // This side
            inverseJoinColumns = @JoinColumn(name = "category_id") // The other side
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(
            mappedBy = "film", // "film" is the field name in the Inventory class
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Inventory> inventory = new HashSet<>();
}
