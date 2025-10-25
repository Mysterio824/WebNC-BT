package com.webnc.bt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Byte storeId; // tinyint maps to Byte

    /**
     * This is a One-to-One relationship.
     * One Store is managed by one Staff member.
     * The 'unique = true' constraint on the column ensures this.
     */
    @OneToOne
    @JoinColumn(name = "manager_staff_id", nullable = false, unique = true)
    private Staff managerStaff;

    /**
     * This is a Many-to-One relationship.
     * One Store has one Address.
     * (And one Address could potentially be used by many stores).
     */
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    /**
     * The database manages this timestamp automatically
     * (DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP).
     * 'insertable = false' and 'updatable = false' tell JPA
     * to never include this field in INSERT or UPDATE statements.
     */
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private Instant lastUpdate;
}