package com.marlonb.hr_middleware.model.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "employee_data",
       uniqueConstraints = @UniqueConstraint(columnNames = {"recordId"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Attributes
    private String recordId;
    private String employeeName;
    private String department;
    private LocalDate attendanceDate;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private String status;
    private String uploadedBy;
    private String sourceFileName;
    private LocalDateTime processedAt;

}
