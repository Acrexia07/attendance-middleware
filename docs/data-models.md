# 📊 Data Models
This document defines the conceptual data model for the HR Attendance Middleware system.
It represents entities, attributes, and relationships without being tied to a specific 
technology stack or database engine.

---
## Model and Attributes
I will only these models: HR Admin model, Attendance Data model, Employee Profile Model, and Department Model.

### 👤 HR Admin Model
Represents an authenticated admin individual who has the authority to 
upload attendance data and view attendance analytics.
#### Attributes:
- `id` - unique and auto-generated for each individual.
- `username` - login username.
- `password` - securely stored password in hashed. 
- `createdAt` - date and time when the account was created.

### 👤 Attendance Data Model
Represents a processed attendance record of an employee in a given date.
#### Attributes:
- `id` - unique and auto-generated for each individual.
- `recordId` - unique id for records.
- `employeeName` - full name of an employee.
- `department` - department name where an employee belongs.
- `attendanceDate` - date when attendance was recorded.
- `timeIn` - time when an employee started work.
- `timeOut` - time when an employee finished work.
- `status` - categorized the work status of an employee like (Present, Absent, Late)
- `uploadedBy` - admin who uploaded the record capturing admin's username
- `sourceFileName` - file name of the uploaded attendance record.
- `processedAt` - date and time when the uploading record happen.

### 👔 Employee Profile Model
Represents an employee profile.
#### Attributes:
- `id` - unique and auto-generated for each individual.
- `employeeId` - unique id intended for one employee each.
- `fullName` - full name of an employee.
- `department` - department name where an employee belongs.
- `position` - job title of an employee.
- `status` - employment status of the employee.

---
## Data Transfer Object
These represent the data structures used to transfer processed or aggregated information to the API consumer, 
without exposing the full entity structure.

**📝 Note:** Standard request/response DTOs for CRUD operations 
(e.g., AdminRequestDto, AdminResponseDto) are assumed and are not listed here. 
Only specialized DTOs used for analytics, summaries, or unique transformations are detailed below.

### EmployeeAttendanceSummaryDto
- `employeeId` - employee identifier
- `fullName` - full name of the employee
- `department` - employee department
- `totalWorkingDays` - total days in the period
- `daysPresent` - number of days present
- `daysAbsent` - number of days absent
- `daysLate` - number of days late
- `attendaceRate` - computed attendance rate
- `recentRecords` - list of the lastest 2 attendance records (`date`, `status`)

### DepartmentSummaryDto
- `department` - department name
- `totalEmployees` - number of employees in department
- `averageAttendanceRate` - average attendance across employees.