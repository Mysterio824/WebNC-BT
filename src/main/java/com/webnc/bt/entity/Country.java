package com.webnc.bt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Short countryId; // smallint

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    /**
     * This field is managed by the database.
     * We tell JPA to never insert or update it.
     */
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private Instant lastUpdate;

    /**
     * Inverse relationship: One Country can have many Cities.
     * 'mappedBy = "country"' refers to the 'country' field in the City entity.
     */
    @OneToMany(mappedBy = "country")
    private Set<City> cities;
}