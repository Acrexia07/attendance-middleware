package com.marlonb.hr_middleware.test_data;

import com.marlonb.hr_middleware.model.data.AttendanceData;

import java.time.LocalTime;

public class Employee1 {

    private static final AttendanceData ATTENDANCE_DATA_DATA;

    private static final Long EMPLOYEE_ID = 1L;
    private static final String EMPLOYEE_NAME = "John Doe";
    private static final String EMPLOYEE_DEPARTMENT = "HR";
    private static final LocalTime EMPLOYEE_TIME_IN = LocalTime.of(7, 50, 0);
    private static final LocalTime EMPLOYEE_TIME_OUT = LocalTime.of(16, 55, 33);
    private static final String EMPLOYEE_STATUS = "ON TIME";

    static {
        ATTENDANCE_DATA_DATA = new AttendanceData();

        ATTENDANCE_DATA_DATA.setId(EMPLOYEE_ID);
        ATTENDANCE_DATA_DATA.setEmployeeName(EMPLOYEE_NAME);
        ATTENDANCE_DATA_DATA.setDepartment(EMPLOYEE_DEPARTMENT);
        ATTENDANCE_DATA_DATA.setTimeIn(EMPLOYEE_TIME_IN);
        ATTENDANCE_DATA_DATA.setTimeOut(EMPLOYEE_TIME_OUT);
        ATTENDANCE_DATA_DATA.setStatus(EMPLOYEE_STATUS);

    }

    public static AttendanceData getEmployee1Data () {
        return ATTENDANCE_DATA_DATA;
    }
}
