package com.marlonb.hr_middleware.utils.excel;

import org.apache.poi.ss.usermodel.*;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExcelCellValueHelper {

    // Helper: If cells exists, makes cell value automatically  a String data type
    public static String getCellValueAsString(Cell cell) {

        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> null;
        };
    }

    // Helper: If cells exists, makes cell value automatically  a LocalDateTime data type
    public static LocalTime getCellValueAsLocalTime(Cell cell) {

        if (cell == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm[:ss]");

        return switch (cell.getCellType()) {
            case STRING -> LocalTime.parse(cell.getStringCellValue().trim(), formatter);
            case NUMERIC -> {
                // Excel date values can be converted to LocalDate first
                Date date = cell.getDateCellValue();
                yield LocalTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            }
            default -> null;
        };
    }
}
