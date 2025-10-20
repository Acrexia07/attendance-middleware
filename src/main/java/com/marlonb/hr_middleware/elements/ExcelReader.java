package com.marlonb.hr_middleware.elements;

import com.marlonb.hr_middleware.model.user.Employee;
import com.marlonb.hr_middleware.utils.ExcelCellValueSetter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class ExcelReader implements ItemReader<Employee> {

    // Initialize Attributes
    private final String filePath;
    private final Workbook workbook;
    private final Sheet sheet;
    private int currentRow = 1;
    private final int lastRow;

    // Constructors
    public ExcelReader(String filePath) {
        this.filePath = filePath;

        // Refer objects to excel
        try {
            // Instantiate FileInputStream Object
            FileInputStream fileInput = new FileInputStream(filePath);

            // Referring attributes to Excel
            this.workbook = new XSSFWorkbook(fileInput);
            this.sheet = workbook.getSheetAt(0);
            this.lastRow = sheet.getLastRowNum();

        }
        catch (IOException e) {
            log.error("Failed to load Excel file: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    // Read operation
    @Override
    public Employee read() throws Exception {

        // Close workbook if there's no data
        if (currentRow > lastRow) {
            workbook.close();
            return null;
        }

        // Read all data by row
        Row row = sheet.getRow(currentRow++);

        // Skip every empty row
        if (row == null) {
            return read();
        }

        // If not empty row, proceed to mapping excel to employee entity
        Employee data = ExcelCellValueSetter.mapRowToEmployee(row);

        // Display message with name info per mapped row
        log.info("Read row {}: {}", currentRow - 1, data.getName());
        return data;
    }
}
