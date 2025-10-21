package com.marlonb.hr_middleware.test_data;

import com.marlonb.hr_middleware.model.data.Employee;

import java.time.LocalTime;

public class Employee1 {

    private static final Employee EMPLOYEE_DATA;

    private static final Long EMPLOYEE_ID = 1L;
    private static final String EMPLOYEE_NAME = "John Doe";
    private static final String EMPLOYEE_DEPARTMENT = "HR";
    private static final LocalTime EMPLOYEE_TIME_IN = LocalTime.of(7, 50, 0);
    private static final LocalTime EMPLOYEE_TIME_OUT = LocalTime.of(16, 55, 33);
    private static final String EMPLOYEE_STATUS = "ON TIME";

    static {
        EMPLOYEE_DATA = new Employee();

        EMPLOYEE_DATA.setId(EMPLOYEE_ID);
        EMPLOYEE_DATA.setName(EMPLOYEE_NAME);
        EMPLOYEE_DATA.setDepartment(EMPLOYEE_DEPARTMENT);
        EMPLOYEE_DATA.setTimeIn(EMPLOYEE_TIME_IN);
        EMPLOYEE_DATA.setTimeOut(EMPLOYEE_TIME_OUT);
        EMPLOYEE_DATA.setStatus(EMPLOYEE_STATUS);

    }

    public static Employee getEmployee1Data () {
        return EMPLOYEE_DATA;
    }
}
