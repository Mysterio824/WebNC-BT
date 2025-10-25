package com.webnc.bt.repository;
import com.webnc.bt.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RentalRepository extends JpaRepository<Rental, Integer> {}