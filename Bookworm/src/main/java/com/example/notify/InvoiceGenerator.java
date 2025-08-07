package com.example.notify;



import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.dto.OrderItemHistoryDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Component
public class InvoiceGenerator {

    public byte[] generateInvoicePdf(int orderId, List<OrderItemHistoryDTO> items) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Invoice for Order ID: " + orderId).setFontSize(18));
        document.add(new Paragraph(" ")); // empty line

        Table table = new Table(6);
        table.addHeaderCell("Product ID");
        table.addHeaderCell("Title");
        table.addHeaderCell("Type");
        table.addHeaderCell("Price Paid");
        table.addHeaderCell("Purchase Date");
        table.addHeaderCell("Rental Duration");

        for (OrderItemHistoryDTO item : items) {
            table.addCell(String.valueOf(item.getProductId()));
            table.addCell(item.getProduct().getTitle());
            table.addCell(item.getProductType());
            table.addCell(String.valueOf(item.getPricePaid()));
            table.addCell(item.getPurchaseDate() != null ? item.getPurchaseDate().toString() : "-");
            String rentalPeriod = item.getRentalStart() != null && item.getRentalEnd() != null
                    ? item.getRentalStart() + " to " + item.getRentalEnd()
                    : "-";
            table.addCell(rentalPeriod);
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}
