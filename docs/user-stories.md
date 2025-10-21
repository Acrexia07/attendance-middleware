# 🏬 User Stories
This section outlines the key user stories and goals that guide the development of the HR Attendance Middleware project.
It includes both learning objectives and functional goals that define what the system should achieve.

---
## 📖 Learning & Development Goals
- As a developer, I want to familiarize Spring Boot so that I can build backend systems confidently.
- As a developer, I want to understand Spring Batch so that I can process large data efficiently.
- As a developer, I want to practice implementing JWT authentication so that I can secure my APIs.
- As a developer, I want to learn how to containerize my application using Docker and Docker Compose so that
  I can deploy it easily.
---
## 🎯 Features Goals
### Authorization and Authentication
- As a developer, I want to allow access only to the HR Admin using their Authenticated admin credentials.

### 🚀 API Reliability & Performance
- As a developer, I want to limit on how many requests HR should be made and stop them for an
  amount of time temporarily.
- As an HR Admin, I want the system to prevent duplicate uploads so that the reprocessing the same file doesn't
  create duplicate data.
- As an HR Admin, I want to view a list of data that is limited to 20 rows per page.

### 📈 Data Processing & Management
- As an HR Admin (hypothetical user), I want to upload my attendance data (Excel/CSV) so that the system can process
  it automatically.
- As the system, I want to process uploaded data in batches so that I can handle large files efficiently.
- As an HR Admin, I want to check the summary of the latest processed data so that I can confirm successful uploads.
- As an HR Admin, I want to view all the processed data within the date range of the processed data.

### 📊 Analytics and Reports
- As an HR Admin, I want to check the employee's number of days present, late, absent, on leave.
- As an HR Admin, I want to view an employee's total working days, days present and attendance rate.
- As an HR Admin, I want to check the attendance rate per department to identify performance patterns.
- As an HR Admin, I want to view a list of employees with perfect attendance for recognition awards.

### ⚙️ System Monitoring
- As a developer, I want to view the logs for each batch job so that I can identify and fix errors easily.
