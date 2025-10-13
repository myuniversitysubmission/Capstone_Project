package com.wastesystem.charging;

import com.wastesystem.equipment.Robot;
import java.util.LinkedList;
import java.util.Queue;

public class ChargingStation {

    private int capacity; // number of charging slots
    private Queue<Robot> queue;

    public ChargingStation(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    // synchronized to avoid conflicts between threads
    public synchronized void requestCharge(Robot robot) {
        System.out.println("⚡ " + robot.getId() + " requested charging...");

        if (queue.size() < capacity) {
            queue.add(robot);
            System.out.println("🔋 " + robot.getId() + " connected to charger.");
            performCharge(robot);
            queue.remove(robot);
        } else {
            System.out.println("⏳ No free charger! " + robot.getId() + " waiting...");
            try {
                wait(2000); // wait 2s before retry
                requestCharge(robot); // retry
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void performCharge(Robot robot) {
        try {
            Thread.sleep(2000); // simulate charging
            System.out.println("✅ " + robot.getId() + " fully charged!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


// Manages robots that need to recharge. Will have logic for queueing robots and restoring their battery levels.