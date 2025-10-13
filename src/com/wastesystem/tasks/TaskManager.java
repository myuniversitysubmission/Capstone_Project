package com.wastesystem.tasks;
import com.wastesystem.ui.WasteManagementUI;

import com.wastesystem.equipment.Robot;
import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskManager {
    private Queue<WasteItem> taskQueue;
    private List<Robot> robots;
    private List<StorageBin> bins;

    public TaskManager() {
        taskQueue = new LinkedList<>();
        robots = new LinkedList<>();
        bins = new LinkedList<>();
    }

    public void registerRobot(Robot robot) {
        robots.add(robot);
    }

    public void registerBin(StorageBin bin) {
        bins.add(bin);
    }

    public void addTask(WasteItem item) {
        taskQueue.add(item);
        System.out.println("📝 New task added: " + item);
    }

    public void startProcessing() {
        System.out.println("🚦 Task Manager started assigning tasks...");
        new Thread(() -> {
            while (!taskQueue.isEmpty()) {
                WasteItem item = taskQueue.poll();
                if (item == null) continue;

                // find a free robot
                for (Robot robot : robots) {
                    // pick the right bin
                    StorageBin targetBin = bins.stream()
                            .filter(b -> b.getBinType() == item.getType())
                            .findFirst()
                            .orElse(null);

                    if (targetBin != null) {
                        robot.pickAndSort(item, targetBin);

                        // 🔄 Update the JavaFX progress bars
                        WasteManagementUI.refreshBars();

                        break; // assign next task to next robot
                    }

                }

                try {
                    Thread.sleep(1000); // simulate assignment delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("✅ All tasks completed!");
        }).start();
    }
    
    
    
}

// Assigns sorting tasks to robots. Controls concurrency (robots working in parallel). Coordinates interactions between robots and storage bins.