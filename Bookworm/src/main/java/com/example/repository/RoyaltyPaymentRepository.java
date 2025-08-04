package com.example.repository;

import com.example.model.RoyaltyPayment;
import com.example.model.RoyaltyPayment.PayeeType;
import com.example.model.RoyaltyPayment.RoyaltyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RoyaltyPaymentRepository extends JpaRepository<RoyaltyPayment, Integer> {

List<RoyaltyPayment> findByPayeeTypeAndPayeeId(PayeeType payeeType, int payeeId);

List<RoyaltyPayment> findByProductProductId(int productId);

List<RoyaltyPayment> findByOrderOrderId(int orderId);

List<RoyaltyPayment> findByRoyaltyType(RoyaltyType royaltyType);

@Query("SELECT SUM(r.amount) FROM RoyaltyPayment r WHERE r.payeeType = :payeeType AND r.payeeId = :payeeId")
Optional<BigDecimal> sumAmountByPayeeTypeAndPayeeId(@Param("payeeType") PayeeType payeeType,
                                                    @Param("payeeId") int payeeId);
}