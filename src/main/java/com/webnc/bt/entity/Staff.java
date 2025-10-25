package com.webnc.bt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Byte staffId; // tinyint

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address; // Maps fk_staff_address

    @Lob
    @Column(name = "picture")
    @JsonIgnore

    private byte[] picture; // blob maps to byte[]

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // Maps fk_staff_store

    @Column(name = "active", nullable = false)
    private boolean active; // tinyint(1) maps to boolean

    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @Column(name = "password", length = 40)
    private String password;

    /**
     * This field is managed by the database (DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP).
     * We tell JPA to never try to insert or update it.
     */
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    @Schema(hidden = true)
    private Instant lastUpdate;
}