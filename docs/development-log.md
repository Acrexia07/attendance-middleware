## DEVELOPMENT LOG — ATTENDANCE MIDDLEWARE

---
### TABLE OF CONTENTS
- [Day 1: Project Initialization](#day-1-project-initialization)

---
### Day 1: Project Initialization
**📅 Date:** October 20, 2025

**🎯 Objectives:**
- Create new project via `Spring Initialzr`.
- Implement docker via Dockerfile and `Docker Compose`.
- Connect Microsoft SQL Server to the project.
- Implement Spring Batch configuration starting with `ItemReader`.

**📝 Implementation Summary:**
- Created new project via `Spring Initialzr`.
- Implemented docker via Dockerfile and `Docker Compose`.
- Connect Microsoft SQL Server to the project.
- Implemented Spring Batch configuration starting with `ItemReader`.

**💭 My thought process:** Since I will be adding admin for this, I will be adding JWT Implementation first for now.

**📌 Next Step:** Preparation for JWT Implementation

---
### Day 2: Preparation for JWT Implementation
**📅 Date:** October 21, 2025

**🎯 Objectives:**
- Create `JwtService` to implement JWT.
- Add customize exception and user details service

**📝 Implementation Summary:**
- Created `JwtService` to implement JWT.
- Added customize exception and user details service.
- Redefine all my project plans and separate them by `md files`.
- Update entity and attributes from data folder after refining my project plans

**💭 My thought process:** I will continue to add JwtFilter after creating a custom user details service. 
I suddenly think about refining my project plans so that I can know right now as early about my 
limitations and features in this project. That's why, I add some statements on the `Implementation summary` that are 
not in the objectives and I will continue my project planning on the next day. 
**Update: ** I finished documentation.

**📌 Next Step:** Preparation for JWT Implementation - Continuation

---
### Day 3: Preparation for JWT Implementation - Last Part
**📅 Date:** October 24, 2025

**🎯 Objectives:**
- Create `JwtFilter` implementation.

**📝 Implementation Summary:**
- Created and implemented `JwtFilter` on my project.

**💭 My thought process:** I only have to do this for now because I will be complying with pre-employment requirements.

**📌 Next Step:** JWT Implementation on login operation and other necessities

---
### Day 3: JWT Implementation on login operation and other necessities
**📅 Date:** October 25, 2025

**🎯 Objectives:**
- Implement JWT on the system starting with Login operation.

**📝 Implementation Summary:**
- Initialized security with JWT Implementation.
- Added DTO and mapper implementation for `AdminAccount` entity.
- Added tested `createAdmin` service function implementation.
- Added customized global exception handler implementation for resource duplication.
- Added tested resource duplication validation for create admin operation.

**💭 My thought process:** After created `AuthController` and `AuthService` for login operations, 
I will be testing it tomorrow.

**📌 Next Step:** Login operation Slice Testing

---
### Day 3: Login operation Slice Testing
**📅 Date:** October 26, 2025

**🎯 Objectives:**
- Test and verify login operation.
- Implement also negative testing on all the controllers.

**📝 Implementation Summary:**
- Tested and confirmed no issues on login operation and expected response token on output.
- Implemented negative test cases on all controllers.

**💭 My thought process:** After created `AuthController` and `AuthService` for login operations,
I will be testing it tomorrow.

**📌 Next Step:** Implementation of `AdminAccount` services and controllers