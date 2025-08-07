package com.example.controller;


import com.example.configuration.SessionUserProvider;
import com.example.dto.OrderItemHistoryDTO;
import com.example.notify.InvoiceGenerator;
import com.example.service.IOrderHistoryService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
    private final SessionUserProvider sessionUserProvider;

    @Autowired
    private IOrderHistoryService orderHistoryService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    InvoiceController(SessionUserProvider sessionUserProvider) {
        this.sessionUserProvider = sessionUserProvider;
    }

    @GetMapping("/download/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable int orderId) {
    
    	System.out.println(orderId);
    	
    	int userId = sessionUserProvider.getCurrentUser().get().getUserId();
        List<OrderItemHistoryDTO> allHistory = orderHistoryService.getOrderHistoryByUserId(userId);
        System.out.println(allHistory + "helllooooooooooooooooooooo");
        List<OrderItemHistoryDTO> filtered = allHistory.stream()
                .filter(o -> o.getOrderId()== orderId)
                .collect(Collectors.toList());

        System.out.println(filtered);
        if (filtered.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] pdfContent = invoiceGenerator.generateInvoicePdf(orderId, filtered);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("Invoice_Order_" + orderId + ".pdf")
                .build());

        System.out.println("Hello world");
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
