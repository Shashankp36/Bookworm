package com.example.notify;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
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

            // Fonts
            PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont italicFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            // ===== HEADER TABLE =====
            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{2, 5, 3}))
                    .useAllAvailableWidth();

            // Logo Cell
            try {
                Image logo = new Image(ImageDataFactory.create(LOGO_PATH));
                logo.setAutoScale(true);
                headerTable.addCell(new Cell().add(logo)
                        .setBorder(null)
                        .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
            } catch (MalformedURLException e) {
                headerTable.addCell(new Cell().add(new Paragraph("Logo").setFont(boldFont)).setBorder(null));
            }

            // Company Info Cell
            Paragraph companyInfo = new Paragraph(COMPANY_NAME)
                    .setFont(boldFont)
                    .setFontSize(14);
            companyInfo.add("\n" + COMPANY_ADDRESS);
            companyInfo.add("\n" + COMPANY_EMAIL);

            headerTable.addCell(new Cell().add(companyInfo)
                    .setBorder(null)
                    .setFont(normalFont)
                    .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));

            // Invoice Title Cell
            Paragraph invoiceTitle = new Paragraph("INVOICE")
                    .setFont(boldFont)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);
            headerTable.addCell(new Cell().add(invoiceTitle)
                    .setBorder(null)
                    .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            // ===== ORDER INFO =====
            Table orderInfoTable = new Table(UnitValue.createPercentArray(new float[]{3, 7}))
                    .useAllAvailableWidth();

            orderInfoTable.addCell(new Cell().add(new Paragraph("Order ID:").setFont(boldFont)).setBorder(null));
            orderInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(orderId)).setFont(normalFont)).setBorder(null));

            orderInfoTable.addCell(new Cell().add(new Paragraph("Customer ID:").setFont(boldFont)).setBorder(null));
            orderInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(provider.getCurrentUser().get().getUserId())).setFont(normalFont)).setBorder(null));

            orderInfoTable.addCell(new Cell().add(new Paragraph("Date:").setFont(boldFont)).setBorder(null));
            orderInfoTable.addCell(new Cell().add(new Paragraph(
                    items.get(0).getPurchaseDate() != null ? items.get(0).getPurchaseDate().format(dateFormatter) : "-"
            ).setFont(normalFont)).setBorder(null));

            document.add(orderInfoTable);
            document.add(new Paragraph("\n"));

            // ===== ITEMS TABLE =====
            Table table = new Table(new float[]{1, 4, 2, 3, 3, 2});
            table.setWidth(UnitValue.createPercentValue(100));
            String[] headers = {"S.No", "Product Title", "Type", "Date", "Valid Upto Date", "Price"};

            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setFont(boldFont))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            BigDecimal total = BigDecimal.ZERO;
            int serialNo = 1;
            for (OrderItemHistoryDTO item : items) {
                table.addCell(new Paragraph(String.valueOf(serialNo++)).setFont(normalFont));
                table.addCell(new Paragraph(item.getProduct().getTitle()).setFont(normalFont));
                table.addCell(new Paragraph(item.getProductType()).setFont(normalFont));

                String dateStr = item.getPurchaseDate() != null ? item.getPurchaseDate().format(dateFormatter) : "-";
                String validUpto = "-";

                if ("rental".equalsIgnoreCase(item.getProductType()) && item.getRentalStart() != null && item.getRentalEnd() != null) {
                    dateStr = item.getRentalStart().format(dateFormatter);
                    validUpto = item.getRentalEnd().format(dateFormatter);
                }

                table.addCell(new Paragraph(dateStr).setFont(normalFont));
                table.addCell(new Paragraph(validUpto).setFont(normalFont));
                table.addCell(new Paragraph("₹" + item.getPricePaid()).setFont(normalFont));

                total = total.add(item.getPricePaid());
            }

            // Total row
            Cell totalLabel = new Cell(1, 5).add(new Paragraph("Total Amount").setFont(boldFont))
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(totalLabel);
            table.addCell(new Paragraph("₹" + total.toPlainString()).setFont(boldFont));

            document.add(table);

            // ===== FOOTER =====
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
