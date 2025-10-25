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
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Short cityId; // smallint

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    /**
     * This maps the 'fk_city_country' constraint.
     * It's the "owning" side of the relationship.
     */
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /**
     * This field is managed by the database.
     * We tell JPA to never insert or update it.
     */
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private Instant lastUpdate;

    /**
     * Inverse relationship: One City can have many Addresses.
     * 'mappedBy = "city"' refers to the 'city' field in the Address entity.
     */
    @OneToMany(mappedBy = "city")
    private Set<Address> addresses;
}