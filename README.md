# Capstone_Project
Automated Waste Sorting &amp; Recycling System

🧠 Project Overview
The Automated Waste Sorting & Recycling System is a simulation-based Java application that models a smart waste management facility.
The goal is to automate the sorting and recycling process using robots, intelligent bins, and real-time task management.
It demonstrates how IoT and automation principles can optimize recycling operations — reducing manual labor and improving efficiency.
The system is built entirely in Java (JavaFX + OOP principles), with a clear modular architecture separating UI, task logic, and physical simulation components.

🎯 Key Features
🤖 Autonomous Robots — pick up and deposit waste items into correct bins.
⚙️ Smart Task Manager — dynamically assigns sorting tasks.
🔋 Charging Simulation — robots detect low battery and recharge automatically.
🗑️ Bin Monitoring — tracks fill levels and capacity in real-time.
🖥️ Interactive Dashboard (JavaFX) — displays robot activity, logs, and bin progress bars.
✅ Multi-threaded Operation — robots and task manager run concurrently.

🧩 System Architecture & Code Structure
The project follows a modular, domain-driven design, with clearly separated packages:
AutomatedWasteSortingSystem/
│
├── src/com/wastesystem/
│   ├── Main.java                        → Entry point for console simulation
│   │
│   ├── equipment/
│   │   └── Robot.java                   → Represents autonomous sorting robot
│   │
│   ├── charging/
│   │   └── ChargingStation.java         → Manages robot recharging logic
│   │
│   ├── tasks/
│   │   └── TaskManager.java             → Assigns waste items to robots & bins
│   │
│   ├── storage/
│   │   ├── StorageBin.java              → Represents bins storing sorted waste
│   │   └── WasteItem.java               → Represents individual waste items
│   │
│   ├── ui/
│   │   └── WasteManagementUI.java       → JavaFX-based control dashboard
│   │
│   └── utils/
│       └── (Optional helper classes)    → For logging, timing, etc.
│
└── README.md

🧠 Code Design Summary
| Layer         | Purpose                                                    |
| ------------- | ---------------------------------------------------------- |
| **Equipment** | Contains classes representing physical actors like `Robot` |
| **Storage**   | Represents bins and waste items                            |
| **Tasks**     | Handles coordination, assignment, and task distribution    |
| **Charging**  | Manages robot energy and recharging behavior               |
| **UI**        | Provides real-time visual feedback via JavaFX              |
| **Main**      | Entry point that brings all subsystems together            |
Each package is loosely coupled and highly cohesive, making the project easy to extend — e.g., adding new waste types, more robots, or IoT-based data logging.

🔄 Execution Flow
System Initialization — Robots, bins, and task manager are created.
Simulation Start — Tasks are randomly generated and queued.
Task Distribution — TaskManager assigns tasks to robots.
Waste Sorting — Robots pick up and deposit waste into appropriate bins.
Battery Handling — Robots auto-charge when low on power.
UI Monitoring — Dashboard updates logs and progress bars in real-time.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Domain Model — Automated Waste Sorting & Recycling System

<img width="587" height="600" alt="Automated Waste Sorting   Recycling System" src="https://github.com/user-attachments/assets/7d2a7ff4-678f-47fc-a48b-0029506dcff1" />

Overview
This project simulates a small automated waste sorting facility. The domain model (diagram) captures the real-world entities and relationships in the system: the central management system, the task coordinator, autonomous robots, storage bins, a charging station, and waste items. The diagram intentionally models the problem domain (what the system is about), not implementation details. 
The domain model drives the application logic: the UI / controller triggers the process, the TaskManager allocates tasks, robots pick and deposit waste items into the correct bins, and robots recharge as needed.

TaskManager
Role: coordinator / scheduler
Responsibility: maintain the queue of sorting tasks (each task wraps a WasteItem), register robots and bins, assign tasks to available robots, and coordinate task flow.
In code: com.wastesystem.tasks.TaskManager.
Key operations: addTask(WasteItem), registerRobot(Robot), registerBin(StorageBin), startProcessing().

Robot
Role: autonomous sorting agent
Responsibility: pick up a WasteItem, identify or use provided type, move/deposit the item into the correct StorageBin, run autonomously (threaded), monitor battery level, request charging when battery low.
In code: com.wastesystem.equipment.Robot (implements Runnable).
Key operations: pickAndSort(WasteItem, StorageBin), run(), getBatteryLevel(), stopRobot().

ChargingStation
Role: shared resource for recharging robots
Responsibility: manage limited charging slots, allow robots to request charge and queue them if necessary.
In code: com.wastesystem.charging.ChargingStation.
Key operation: requestCharge(Robot) (or chargeRobot(Robot) in earlier versions).

StorageBin
Role: receptacle for sorted waste of a specific type
Responsibility: accept items of the correct type only, track current fill level (sum of item weights), expose capacity and fill status for UI/progress bars, provide content listing for logs.
In code: com.wastesystem.storage.StorageBin.
Key operations: addItem(WasteItem), getCurrentSize() (total weight), getCapacity(), showContents().

WasteItem
Role: domain object representing a piece of waste
Responsibility: carry domain attributes such as type (PLASTIC, METAL, PAPER, ORGANIC) and weight (kg).
In code: com.wastesystem.storage.WasteItem with nested enum Type.
Key data: type: Type, weight: double.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Relationships & Multiplicities (conceptual)

WasteManagementSystem monitors TaskManager (1 → 1).
TaskManager assigns tasks to Robot (1 → * ; one manager coordinates many robots).
TaskManager directs waste to StorageBin (1 → * ; manager knows about multiple bins).
Robot picks up WasteItem (1 → * ; each robot processes many items over time).
Robot deposits items into StorageBin (* → 1 ; each item goes into one bin).
StorageBin contains WasteItem (1 → * ; a bin stores many items).
Robot charges at ChargingStation (* → 1 ; many robots use one or more charging ports).

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Typical Runtime Sequence (task flow)

Start: WasteManagementSystem (UI) triggers TaskManager to start simulation.
Task creation: the system generates WasteItems (random or from input) and calls TaskManager.addTask(item).
Assignment: TaskManager.startProcessing() polls the task queue, finds an available robot, and assigns the WasteItem.
Execution: The Robot receives the item and runs pickAndSort(item, targetBin) which calls targetBin.addItem(item). StorageBin updates its internal list and fill-level (sum of weights).
UI Update: After addItem() the TaskManager (or robot) triggers the UI to read StorageBin.getCurrentSize() and refresh progress bars.
Battery Handling: Robot periodically drains battery. If low, it requests the ChargingStation which queues and charges robots one-by-one or up to capacity.
Completion: Once the task queue is empty and robots finish work, TaskManager logs completion; the UI displays final bin contents.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Mapping Domain → Implementation (package & file mapping)

| Domain Class          | Java class / package                                              |
| --------------------- | ----------------------------------------------------------------- |
| WasteManagementSystem | `com.wastesystem.ui.WasteManagementUI` (+ `com.wastesystem.Main`) |
| TaskManager           | `com.wastesystem.tasks.TaskManager`                               |
| Robot                 | `com.wastesystem.equipment.Robot`                                 |
| ChargingStation       | `com.wastesystem.charging.ChargingStation`                        |
| StorageBin            | `com.wastesystem.storage.StorageBin`                              |
| WasteItem             | `com.wastesystem.storage.WasteItem`                               |

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Design decisions & rationale

Domain vs. Implementation separation: The domain diagram abstracts concepts (Robot, StorageBin, WasteItem) that remain stable even if implementation changes (e.g., switching JavaFX for a web UI).
TaskManager as coordinator: keeps robotics and resource scheduling centralized and extensible (supports priorities, load balancing, persistence later).
Robots as threads: modeling concurrent operation and resource contention (charging stations) simulates real-world parallelism and synchronization concerns.
Bins measure weight: fill-levels are measured by aggregate weight (kg), not item count — more realistic for capacity planning.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Extension ideas (future work)

Add sensors & classification: integrate an ML classifier for automatic waste type recognition.
Persist logs & metrics: write bin fill levels and robot uptime to a database for analytics.
Web dashboard: replace JavaFX with a web UI and WebSocket updates for remote monitoring.
Fault handling: simulate and handle robotic failures, stuck items, and bin overflow policies.
Scheduling improvements: implement prioritized or cost-aware task scheduling in TaskManager.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
How to run (quick)
1.Install JDK (match JavaFX SDK version used in the project).
2.In Eclipse: add JavaFX SDK to project build path and set VM args:

     --module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
3.Run com.wastesystem.ui.WasteManagementUI as Java Application to launch the dashboard (recommended for demo).
4.Optionally run com.wastesystem.Main to run a console-only simulation.

Testing notes

Verify StorageBin.showContents() prints expected contents after simulation.
Confirm WasteManagementUI progress bars move according to StorageBin.getCurrentSize() / capacity.
Test charging by forcing battery levels to drain quickly (adjust battery drain rates in Robot).

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
