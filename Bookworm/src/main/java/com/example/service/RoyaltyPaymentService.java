package com.example.service;

import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Purchase;
import com.example.model.RoyaltyPayment;
import com.example.model.RoyaltyPayment.PayeeType;
import com.example.model.RoyaltyPayment.RoyaltyType;
import com.example.repository.RoyaltyPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoyaltyPaymentService implements IRoyaltyPaymentService {

	@Autowired
	private RoyaltyPaymentRepository royaltyPaymentRepository;

	@Override
	public RoyaltyPayment saveRoyaltyPayment(RoyaltyPayment royaltyPayment) {
		if (royaltyPayment.getPaymentDate() == null) {
			royaltyPayment.setPaymentDate(LocalDate.now());
		}
		return royaltyPaymentRepository.save(royaltyPayment);
	}

	@Override
	public Optional<RoyaltyPayment> getRoyaltyPaymentById(int id) {
		return royaltyPaymentRepository.findById(id);
	}

	@Override
	public List<RoyaltyPayment> getAllRoyaltyPayments() {
		return royaltyPaymentRepository.findAll();
	}

	@Override
	public void deleteRoyaltyPayment(int id) {
		royaltyPaymentRepository.deleteById(id);
	}

	@Override
	public List<RoyaltyPayment> getByPayee(PayeeType payeeType, int payeeId) {
		return royaltyPaymentRepository.findByPayeeTypeAndPayeeId(payeeType, payeeId);
	}

	@Override
	public List<RoyaltyPayment> getByProductId(int productId) {
		return royaltyPaymentRepository.findByProductProductId(productId);
	}

	@Override
	public List<RoyaltyPayment> getByOrderId(int orderId) {
		return royaltyPaymentRepository.findByOrderOrderId(orderId);
	}

	@Override
	public BigDecimal getTotalRoyaltyAmount(PayeeType payeeType, int payeeId) {
		return royaltyPaymentRepository.sumAmountByPayeeTypeAndPayeeId(payeeType, payeeId).orElse(BigDecimal.ZERO);
	}

	@Override
	public List<RoyaltyPayment> getByRoyaltyType(RoyaltyType royaltyType) {
		return royaltyPaymentRepository.findByRoyaltyType(royaltyType);
	}

	@Override
	public void saveRoyalty(Purchase purchase, CartItem item, Order order) {

		RoyaltyPayment payment = new RoyaltyPayment();
		payment.setAmount(purchase.getAuthorRoyalty());
	}
}