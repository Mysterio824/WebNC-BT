package com.webnc.bt.repository;
import com.webnc.bt.entity.Payment;
import com.webnc.bt.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Short> {
    // We need this to find payments associated with a rental
    List<Payment> findByRental(Rental rental);
}