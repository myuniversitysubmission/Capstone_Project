package com.wastesystem.charging;

import com.wastesystem.equipment.Robot;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ChargingStation
 * Handles charging requests from robots, manages a limited number of charging slots,
 * and restores robot battery levels in a synchronized and thread-safe manner.
 */
public class ChargingStation {

    private int capacity; // Number of charging slots
    private Queue<Robot> queue;

    public ChargingStation(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    /**
     * Main method used by Robot or TaskManager to request charging.
     */
    public synchronized void chargeRobot(Robot robot) {
        System.out.println("⚡ " + robot.getId() + " requested charging...");

        if (queue.size() < capacity) {
            queue.add(robot);
            System.out.println("🔋 " + robot.getId() + " connected to charger.");
            performCharge(robot);
            queue.remove(robot);
            notifyAll(); // Notify waiting robots that a slot is free
        } else {
            System.out.println("⏳ No free charger! " + robot.getId() + " waiting...");
            try {
                wait(2000); // Wait for 2s before retry
                chargeRobot(robot); // Retry charging
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("⚠️ " + robot.getId() + " interrupted while waiting for charging.");
            }
        }
    }

    /**
     * Simulates actual charging delay and restores battery to 100%.
     */
    private void performCharge(Robot robot) {
        try {
            Thread.sleep(2000); // Simulate charging time
            robot.setBatteryLevel(100); // Restore battery
            System.out.println("✅ " + robot.getId() + " fully charged!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⚠️ Charging interrupted for " + robot.getId());
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getQueueSize() {
        return queue.size();
    }
}


// Manages robots that need to recharge. Will have logic for queueing robots and restoring their battery levels.