package com.example.studentcrudapp.service;

import com.example.studentcrudapp.entity.Student;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfExportService {

    public byte[] exportStudentsToPdf(List<Student> students) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set font and font size
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        // Page settings
        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;

        // Table settings
        float[] columnWidths = {50f, 100f, 100f, 100f, 50f, 120f}; // Column widths
        float rowHeight = 20f;
        // float tableHeight = rowHeight * (students.size() + 1); // +1 for header

        // Draw table header
        String[] headers = {"ID", "First Name", "Middle Name", "Last Name", "Age", "Location"};
        
        // Header background (simulate)
        contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f); // Light gray
        contentStream.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
        contentStream.fill();
        
        contentStream.setNonStrokingColor(0f, 0f, 0f); // Black text
        
        float xPosition = margin;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(headers[i]);
            contentStream.endText();
            xPosition += columnWidths[i];
        }

        yPosition -= rowHeight;

        // Set regular font for data
        contentStream.setFont(PDType1Font.HELVETICA, 10);

        // Draw table data
        for (Student student : students) {
            xPosition = margin;
            
            // ID
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(String.valueOf(student.getId()));
            contentStream.endText();
            xPosition += columnWidths[0];

            // First Name
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(student.getFirstName());
            contentStream.endText();
            xPosition += columnWidths[1];

            // Middle Name
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(student.getMiddleName() != null ? student.getMiddleName() : "");
            contentStream.endText();
            xPosition += columnWidths[2];

            // Last Name
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(student.getLastName());
            contentStream.endText();
            xPosition += columnWidths[3];

            // Age
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(String.valueOf(student.getAge()));
            contentStream.endText();
            xPosition += columnWidths[4];

            // Location
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, yPosition - 15);
            contentStream.showText(student.getLocation());
            contentStream.endText();

            yPosition -= rowHeight;

            // Check if we need a new page
            if (yPosition < margin + rowHeight) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = yStart;
                contentStream.setFont(PDType1Font.HELVETICA, 10);
            }
        }

        // Draw table borders
        drawTableBorders(contentStream, margin, yStart, columnWidths, rowHeight, students.size() + 1);

        contentStream.close();
        
        // Convert to byte array instead of saving to file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        return outputStream.toByteArray();
    }

    private void drawTableBorders(PDPageContentStream contentStream, float margin, float yStart, 
                                 float[] columnWidths, float rowHeight, int numberOfRows) throws IOException {
        
        // Calculate total table width
        float tableWidth = 0;
        for (float width : columnWidths) {
            tableWidth += width;
        }

        // Draw horizontal lines
        for (int i = 0; i <= numberOfRows; i++) {
            contentStream.moveTo(margin, yStart - i * rowHeight);
            contentStream.lineTo(margin + tableWidth, yStart - i * rowHeight);
            contentStream.stroke();
        }

        // Draw vertical lines
        float xPosition = margin;
        for (int i = 0; i <= columnWidths.length; i++) {
            contentStream.moveTo(xPosition, yStart);
            contentStream.lineTo(xPosition, yStart - numberOfRows * rowHeight);
            contentStream.stroke();
            if (i < columnWidths.length) {
                xPosition += columnWidths[i];
            }
        }
    }
}