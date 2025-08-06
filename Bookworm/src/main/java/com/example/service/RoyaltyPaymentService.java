package com.example.service;

import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Purchase;
import com.example.model.Rental;
import com.example.model.RoyaltyPayment;
import com.example.model.RoyaltyPayment.PayeeType;
import com.example.model.RoyaltyPayment.RoyaltyType;
import com.example.repository.RoyaltyPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoyaltyPaymentService implements IRoyaltyPaymentService {

	@Autowired
	private RoyaltyPaymentRepository royaltyPaymentRepository;

	@Override
	public RoyaltyPayment saveRoyaltyPayment(RoyaltyPayment royaltyPayment) {
		if (royaltyPayment.getPaymentDate() == null) {
			royaltyPayment.setPaymentDate(LocalDateTime.now());
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

		RoyaltyPayment payment1 = new RoyaltyPayment();
		RoyaltyPayment payment2 = new RoyaltyPayment();
		
		
		payment1.setAmount(purchase.getAuthorRoyalty());
		payment1.setPayeeId(purchase.getProduct().getAuthor().getAuthorId());
		payment1.setPaymentDate(purchase.getPurchaseDate());
		payment1.setOrder(order);
		payment1.setProduct(purchase.getProduct());
		payment1.setPayeeType(PayeeType.author);
		payment1.setRoyaltyType(RoyaltyType.purchase);
		royaltyPaymentRepository.save(payment1);
		
		payment2.setAmount(purchase.getPublisherRoyalty());
		payment2.setPayeeId(purchase.getProduct().getPublisher().getPublisherId());
		payment2.setPaymentDate(purchase.getPurchaseDate());
		payment2.setOrder(order);
		payment2.setProduct(purchase.getProduct());
		payment2.setPayeeType(PayeeType.publisher);
		payment2.setRoyaltyType(RoyaltyType.purchase);
		royaltyPaymentRepository.save(payment2);
	}

	@Override
	public void saveRoyalty(Rental rental, CartItem item, Order order) {
		// TODO Auto-generated method stub
		RoyaltyPayment payment1 = new RoyaltyPayment();
		RoyaltyPayment payment2 = new RoyaltyPayment();
		
		
		payment1.setAmount(rental.getAuthorRoyalty());
		payment1.setPayeeId(rental.getProduct().getAuthor().getAuthorId());
		payment1.setPaymentDate(order.getOrderDate());
		payment1.setOrder(order);
		payment1.setProduct(rental.getProduct());
		payment1.setPayeeType(PayeeType.author);
		payment1.setRoyaltyType(RoyaltyType.rental);
		royaltyPaymentRepository.save(payment1);
		
		payment2.setAmount(rental.getPublisherRoyalty());
		payment2.setPayeeId(rental.getProduct().getPublisher().getPublisherId());
		payment2.setPaymentDate(order.getOrderDate());
		payment2.setOrder(order);
		payment2.setProduct(rental.getProduct());
		payment2.setPayeeType(PayeeType.publisher);
		payment2.setRoyaltyType(RoyaltyType.rental);
		royaltyPaymentRepository.save(payment2);
		
	}
}