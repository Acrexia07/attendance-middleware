package com.marlonb.hr_middleware.elements;

import com.marlonb.hr_middleware.model.data.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelReaderUnitTests {

    private ExcelReader reader;

    @BeforeEach
    void initialSetup () {
        reader = new ExcelReader("src/main/resources/data/employee-attendance.xlsx");
    }

    @Nested
    class PositiveTests {

        @Test
        @DisplayName("Batch(Reader): Should read excel data successfully")
        void shouldReadExcelDataSuccessfully () throws Exception {

            Employee item = reader.read();

            assertNotNull(item);
            assertEquals("Chris Smith", item.getName());
        }
    }

    @Nested
    class NegativeTests {

        @Test
        @DisplayName("Batch(Reader): Should fail if excel file path is invalid")
        void shouldFailIfExcelFilePathIsInvalid () throws Exception {

            RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
               ExcelReader invalidReader = new ExcelReader("invalid-file-path.xlsx");
               invalidReader.read();
            });

            assertInstanceOf(IOException.class, thrown.getCause());
        }
    }
}
