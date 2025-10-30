package com.marlonb.hr_middleware.utils.excel;

import com.marlonb.hr_middleware.model.data.AttendanceData;
import org.apache.poi.ss.usermodel.Row;

import static com.marlonb.hr_middleware.utils.excel.ExcelCellValueHelper.*;

public class ExcelCellValueSetter {

    public static AttendanceData mapRowToEmployee (Row row) {

        AttendanceData data = new AttendanceData();

        data.setEmployeeName(getCellValueAsString(row.getCell(0)));
        data.setDepartment(getCellValueAsString(row.getCell(1)));
        data.setTimeIn(getCellValueAsLocalTime(row.getCell(2)));
        data.setTimeOut(getCellValueAsLocalTime(row.getCell(3)));
        data.setStatus(getCellValueAsString(row.getCell(4)));

        return data;
    }
}
