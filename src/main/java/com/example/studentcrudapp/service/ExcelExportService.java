package com.example.studentcrudapp.service;

import com.example.studentcrudapp.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportStudentsToExcel(List<Student> students) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Create header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Create data style
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "First Name", "Middle Name", "Last Name", "Age", "Location"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Create data rows
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            
            Cell idCell = row.createCell(0);
            idCell.setCellValue(student.getId());
            idCell.setCellStyle(dataStyle);
            
            Cell firstNameCell = row.createCell(1);
            firstNameCell.setCellValue(student.getFirstName());
            firstNameCell.setCellStyle(dataStyle);
            
            Cell middleNameCell = row.createCell(2);
            middleNameCell.setCellValue(student.getMiddleName() != null ? student.getMiddleName() : "");
            middleNameCell.setCellStyle(dataStyle);
            
            Cell lastNameCell = row.createCell(3);
            lastNameCell.setCellValue(student.getLastName());
            lastNameCell.setCellStyle(dataStyle);
            
            Cell ageCell = row.createCell(4);
            ageCell.setCellValue(student.getAge());
            ageCell.setCellStyle(dataStyle);
            
            Cell locationCell = row.createCell(5);
            locationCell.setCellValue(student.getLocation());
            locationCell.setCellStyle(dataStyle);
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to byte array instead of file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}