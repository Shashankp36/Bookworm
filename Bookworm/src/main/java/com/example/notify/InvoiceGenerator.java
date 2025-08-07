package com.example.notify;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.configuration.SessionUserProvider;
import com.example.dto.OrderItemHistoryDTO;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Component
public class InvoiceGenerator {

    private static final String COMPANY_NAME = "BookWorm Inc.";
    private static final String COMPANY_ADDRESS = "123 Library Lane, Knowledge City, India";
    private static final String COMPANY_EMAIL = "bookworm12344321@gmail.com";
    private static final String LOGO_PATH = "src/main/resources/static/logo.png"; // 📌 Change this to your logo path

    @Autowired
    private  SessionUserProvider provider;
    
    
    public byte[] generateInvoicePdf(int orderId, List<OrderItemHistoryDTO> items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4, false);

            // Add logo
            try {
                Image logo = new Image(ImageDataFactory.create(LOGO_PATH));
                logo.setHeight(50);
                logo.setAutoScale(true);
                document.add(logo);
            } catch (MalformedURLException e) {
                System.out.println("Logo not found: " + e.getMessage());
            }

            // Company Info
            Paragraph companyInfo = new Paragraph(COMPANY_NAME)
                    .setFontSize(18)
                    .setMarginBottom(2);
            companyInfo.add("\n" + COMPANY_ADDRESS);
            companyInfo.add("\n" + COMPANY_EMAIL);
            document.add(companyInfo);

            document.add(new Paragraph("\nINVOICE")
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    );

            // Order Info
            document.add(new Paragraph("Order ID: " + orderId)
                    .setFontSize(12)
                    .setMarginTop(10));
            document.add(new Paragraph("Customer ID: " + provider.getCurrentUser().get().getUserId()))
                    .setFontSize(12);
            document.add(new Paragraph("Date: " + items.get(0).getPurchaseDate())
                    .setFontSize(12));
            document.add(new Paragraph("\n"));

            // Table setup
            Table table = new Table(new float[]{2, 4, 2, 2, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));
            String[] headers = {"Product ID", "Title", "Type", "Price", "Purchase Date", "Rental Duration"};

            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            BigDecimal total = BigDecimal.ZERO;

            for (OrderItemHistoryDTO item : items) {
                table.addCell(String.valueOf(item.getProductId()));
                table.addCell(item.getProduct().getTitle());
                table.addCell(item.getProductType());
                table.addCell("₹" + item.getPricePaid());
                table.addCell(item.getPurchaseDate() != null ? item.getPurchaseDate().toString() : "-");
                String rentalPeriod = item.getRentalStart() != null && item.getRentalEnd() != null
                        ? item.getRentalStart() + " to " + item.getRentalEnd()
                        : "-";
                table.addCell(rentalPeriod);

                total = total.add(item.getPricePaid());
            }

            // Total row
            Cell totalLabel = new Cell(1, 5).add(new Paragraph("Total Amount"));
            totalLabel.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(totalLabel);
            table.addCell(new Paragraph("₹" + total.toPlainString()));
            
            PdfFont italicFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
            document.add(table);
            document.add(new Paragraph("\nThank you for your purchase!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(italicFont)
                    .setFontSize(12));
                    
            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
