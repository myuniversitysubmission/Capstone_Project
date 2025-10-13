//package com.wastesystem;
//
//import com.wastesystem.storage.WasteItem;
//import com.wastesystem.storage.StorageBin;
//
//public class Main {
//    public static void main(String[] args) {
//
//        StorageBin plasticBin = new StorageBin(WasteItem.Type.PLASTIC, 3);
//        StorageBin metalBin = new StorageBin(WasteItem.Type.METAL, 2);
//
//        WasteItem item1 = new WasteItem(WasteItem.Type.PLASTIC, 0.5);
//        WasteItem item2 = new WasteItem(WasteItem.Type.METAL, 1.0);
//        WasteItem item3 = new WasteItem(WasteItem.Type.PAPER, 0.3);
//
//        plasticBin.addItem(item1); // ✅ Correct
//        metalBin.addItem(item2);   // ✅ Correct
//        plasticBin.addItem(item3); // ❌ Wrong bin
//
//        plasticBin.showContents();
//        metalBin.showContents();
//    }
//}
 // above was for Represents a waste storage bin that can only accept one waste type. It is thread-safe, so multiple robots can use it concurrently later.
//----------------------------------------
//package com.wastesystem;
//
//import com.wastesystem.equipment.Robot;
//import com.wastesystem.storage.StorageBin;
//import com.wastesystem.storage.WasteItem;
//
//public class Main {
//    public static void main(String[] args) {
//
//        StorageBin plasticBin = new StorageBin(WasteItem.Type.PLASTIC, 5);
//        StorageBin metalBin = new StorageBin(WasteItem.Type.METAL, 5);
//
//        Robot robot1 = new Robot("Robot-1");
//        Robot robot2 = new Robot("Robot-2");
//
//        WasteItem item1 = new WasteItem(WasteItem.Type.PLASTIC, 0.3);
//        WasteItem item2 = new WasteItem(WasteItem.Type.METAL, 0.8);
//
//        // Run robots as threads
//        Thread t1 = new Thread(robot1);
//        Thread t2 = new Thread(robot2);
//        t1.start();
//        t2.start();
//
//        // Simulate sorting
//        robot1.pickAndSort(item1, plasticBin);
//        robot2.pickAndSort(item2, metalBin);
//
//        plasticBin.showContents();
//        metalBin.showContents();
//
//        // stop threads (optional for now)
//        robot1.stopRobot();
//        robot2.stopRobot();
//    }
//}
// above was for Step 2: equipment → the Robot class The robot will: Act as a thread (each robot runs in parallel). Pick up waste items and drop them into the correct StorageBin. Have a battery level and recharge when low. Later, interact with the ChargingStation and TaskManager.
//------------------------------------------------
//package com.wastesystem;
//
//import com.wastesystem.equipment.Robot;
//import com.wastesystem.storage.StorageBin;
//import com.wastesystem.storage.WasteItem;
//import com.wastesystem.charging.ChargingStation;
//
//public class Main {
//    public static void main(String[] args) {
//
//        StorageBin plasticBin = new StorageBin(WasteItem.Type.PLASTIC, 3);
//        StorageBin metalBin = new StorageBin(WasteItem.Type.METAL, 3);
//
//        Robot robot1 = new Robot("Robot-1");
//        Robot robot2 = new Robot("Robot-2");
//
//        ChargingStation station = new ChargingStation(1); // only 1 charger
//
//        WasteItem item1 = new WasteItem(WasteItem.Type.PLASTIC, 0.3);
//        WasteItem item2 = new WasteItem(WasteItem.Type.METAL, 0.5);
//
//        Thread t1 = new Thread(robot1);
//        Thread t2 = new Thread(robot2);
//        t1.start();
//        t2.start();
//
//        robot1.pickAndSort(item1, plasticBin);
//        robot2.pickAndSort(item2, metalBin);
//
//        // Simulate low battery
//        station.requestCharge(robot1);
//        station.requestCharge(robot2);
//
//        plasticBin.showContents();
//        metalBin.showContents();
//
//        robot1.stopRobot();
//        robot2.stopRobot();
//    }
//}
// above was for 🔋 Next Step: ChargingStation.java (in com.wastesystem.charging) Now that robots can lose charge, we’ll simulate a shared charging station where: Multiple robots can queue up when battery is low. Only a limited number of charging slots are available at once. Robots must wait if all chargers are busy (synchronization).
//-----------------------------------------------------
package com.wastesystem;

import com.wastesystem.charging.ChargingStation;
import com.wastesystem.equipment.Robot;
import com.wastesystem.storage.*;
import com.wastesystem.tasks.TaskManager;

public class Main {
    public static void main(String[] args) {

        // Create bins
        StorageBin plasticBin = new StorageBin(WasteItem.Type.PLASTIC, 3);
        StorageBin metalBin = new StorageBin(WasteItem.Type.METAL, 3);

        // Create robots
        Robot robot1 = new Robot("Robot-1");
        Robot robot2 = new Robot("Robot-2");

        Thread t1 = new Thread(robot1);
        Thread t2 = new Thread(robot2);
        t1.start();
        t2.start();

        // Create charging station (optional for now)
        ChargingStation station = new ChargingStation(1);

        // Create task manager
        TaskManager manager = new TaskManager();
        manager.registerRobot(robot1);
        manager.registerRobot(robot2);
        manager.registerBin(plasticBin);
        manager.registerBin(metalBin);

        // Add tasks dynamically
        manager.addTask(new WasteItem(WasteItem.Type.PLASTIC, 0.4));
        manager.addTask(new WasteItem(WasteItem.Type.METAL, 0.6));
        manager.addTask(new WasteItem(WasteItem.Type.PLASTIC, 0.5));

        // Start processing
        manager.startProcessing();

        // Wait before stopping robots
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        plasticBin.showContents();
        metalBin.showContents();

        robot1.stopRobot();
        robot2.stopRobot();
    }
}

/// above code is for 🧠 Step 4 → Next Module: TaskManager.java Now it’s time to automate task handling — instead of manually calling pickAndSort() for each robot, we’ll make a central Task Manager that: Keeps a queue of sorting tasks Assigns tasks automatically to available robots Handles multiple robots working in parallel Uses threads to simulate continuous operation
//-------------------------------------------


// 👉 Purpose: Entry point of the whole project.