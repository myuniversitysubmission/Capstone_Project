
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

// 👉 Purpose: Entry point of the whole project.