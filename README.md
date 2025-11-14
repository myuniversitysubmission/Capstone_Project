# Automated Waste Sorting & Recycling System

## 🧭 Overview
The **Automated Waste Sorting & Recycling System** is a simulation-based JavaFX project designed to emulate how an intelligent waste management system can automatically **sort**, **store**, and **recycle** waste using autonomous robots.

The project focuses on **sustainability through automation**, demonstrating how AI-powered robotic systems can optimize waste segregation, manage recycling operations, and maintain efficient system performance in real time.

---

## 🎯 Project Aim
To design and simulate an **intelligent waste management system** that:
- Sorts different types of waste automatically.
- Manages bin capacities and triggers recycling processes.
- Demonstrates concurrency and synchronization between multiple autonomous agents (robots).
- Provides a visual dashboard to monitor system operations.

---

## 🧩 Objectives
1. **Automation:** Enable robots to pick and sort waste items into correct bins.
2. **Simulation:** Use JavaFX to display real-time waste levels and robot activities.
3. **Recycling Logic:** Automatically recycle bins once they reach full capacity.
4. **Concurrency:** Implement multi-threading for robots, recycling, and UI updates.
5. **Logging & Tracking:** Maintain a centralized logging system for all activities.
6. **System Resilience:** Implement controlled reset, reinitialization, and error handling.

---

## 🏗️ Project Structure

AutomatedWasteSortingSystem/
│
├── src/
│ ├── com.wastesystem.charging/
│ │ └── ChargingStation.java
│ │
│ ├── com.wastesystem.equipment/
│ │ └── Robot.java
│ │
│ ├── com.wastesystem.recycling/
│ │ └── RecyclingPlant.java
│ │
│ ├── com.wastesystem.storage/
│ │ ├── StorageBin.java
│ │ └── WasteItem.java
│ │
│ ├── com.wastesystem.tasks/
│ │ └── TaskManager.java
│ │
│ ├── com.wastesystem.ui/
│ │ ├── WasteManagementUI.java
│ │ └── TestUI.java
│ │
│ └── com.wastesystem.utils/
│ ├── LogHandler.java
│ ├── SystemException.java
│ ├── LogHandlerTest.java
│ ├── ChargingStationTest.java
│ ├── RecyclingTest.java
│ ├── StorageBinTest.java
│ ├── RobotTest.java
│ ├── TaskManagerTest.java
│ └── ConcurrencyTest.java
│
├── system_log.txt
├── README.md
└── .gitignore


---

## ⚙️ System Components

### 🧠 1. **Task Management Subsystem**
**Class:** `TaskManager.java`  
Handles task allocation and robot coordination using queues.  
Implements threading to simulate real-time task execution.

---

### 🤖 2. **Robotic Equipment Subsystem**
**Class:** `Robot.java`  
Represents autonomous robots that pick, sort, and recharge.  
Each robot operates on its own thread and interacts with bins.

---

### 🔋 3. **Charging Subsystem**
**Class:** `ChargingStation.java`  
Handles robot recharging using synchronized queues and capacity limits.

---

### 🗑️ 4. **Storage Subsystem**
**Classes:** `StorageBin.java`, `WasteItem.java`  
- Bins store categorized waste (Plastic, Metal, Paper, Organic).  
- Automatically trigger recycling when full.

---

### 🔄 5. **Recycling Subsystem**
**Class:** `RecyclingPlant.java`  
Handles the recycling process once bins reach capacity.  
Uses background threads for simulation and UI updates.

---

### 🖥️ 6. **User Interface**
**Class:** `WasteManagementUI.java`  
A JavaFX-based dashboard showing:
- Robot battery levels
- Bin fill progress
- System logs
- Start & Reset controls

---

### 🧩 7. **Utilities**
**Classes:**  
- `LogHandler.java`: Centralized logging utility for console and file output.  
- `SystemException.java`: Custom exception for system-level error handling.  
- `*Test.java`: JUnit-based test files for modular and concurrency testing.

---

## 🧪 Testing Modules
Located under `com.wastesystem.utils/`:
| Test File | Purpose |
|------------|----------|
| `LogHandlerTest.java` | Validates logging outputs and error handling |
| `RobotTest.java` | Tests sorting and battery cycles |
| `StorageBinTest.java` | Tests capacity, recycling triggers, and exceptions |
| `TaskManagerTest.java` | Validates task queueing and assignment logic |
| `RecyclingTest.java` | Ensures recycling flow and synchronization |
| `ConcurrencyTest.java` | Tests thread safety and deadlock prevention |
| `ChargingStationTest.java` | Checks multi-robot charging management |

---

## 🧱 Technologies Used
- **Java 21 (JDK 21)**
- **JavaFX 21** — User Interface
- **JUnit 5** — Testing Framework
- **Git & GitHub** — Version Control
- **PlantUML / Visual Paradigm** — UML Diagrams
- **OOP Concepts:** Encapsulation, Inheritance, Polymorphism, Multithreading

---

## 🧩 UML Class Diagram:

<img width="2324" height="1723" alt="Automated Waste Sorting   Recycling System (1)" src="https://github.com/user-attachments/assets/7944494d-b699-4b63-95ef-2f9fdb6c6bc2" />

---

## 🚀 Running the Project

### Prerequisites
- Java 21+
- JavaFX SDK installed
- IDE: Eclipse / IntelliJ with JavaFX configured
- JUnit 5 plugin (for testing)

### Steps
1. **Clone Repository**
   ```bash
   git clone https://github.com/myuniversitysubmission/Capstone_Project.git

2. Open in Eclipse
   Import as Java Project.
   Add JavaFX libraries to module path.

3. Run the Simulation
    Run WasteManagementUI.java
    Click Start Simulation to begin.

4. Check Logs
   System logs are generated in the console and saved in system_log.txt.

---

🧑‍💻 Team Members
Role	Focus Area

Adesh: Project Lead & Team Lead | Architecture & Dashboard (WasteManagementUI (JavaFX screens, progress bars, logging panel)) 
Dnyaneshwar: Core logic (TaskManager, Robot behaviour)
Harshavarthan: Storage & recycling (StorageBin, WasteItem, capacity logic)
Anil: Utilities & testing (RecyclingPlant ,battery simulation ,LogHandler, SystemException, JUnit tests, concurrency demos)

---
🗂️ Future Improvements
    
Integration with real sensor data via IoT.
AI-based sorting optimization.
Cloud-based analytics dashboard.
Enhanced recycling efficiency simulation.

---

Video Explanation Link: https://drive.google.com/file/d/1tCvLB15QJ8EPPtAIdbMkw14zO3BlMuai/view?usp=sharing

---
📘 License
This project is developed for academic purposes under university guidelines.
All rights reserved © 2025 Smart Waste Management Team.
