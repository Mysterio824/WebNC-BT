package com.webnc.bt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Short customerId; // smallint

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // Maps fk_customer_store

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address; // Maps fk_customer_address

    @Column(name = "active", nullable = false)
    private boolean active; // tinyint(1)

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate; // datetime

    /**
     * This field is managed by the database (DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP).
     * We tell JPA to never try to insert or update it.
     */
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private Instant lastUpdate;

    // --- Inverse Relationships (for solving delete constraints) ---

    @OneToMany(mappedBy = "customer")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "customer")
    private Set<Rental> rentals;
}