package com.wastesystem.equipment;

import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;

public class Robot implements Runnable {

    private String id;
    private int batteryLevel;
    private boolean active;

    public Robot(String id) {
        this.id = id;
        this.batteryLevel = 100; // start full
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void stopRobot() {
        active = false;
    }

    private void drainBattery() {
        batteryLevel -= 10;
        if (batteryLevel < 0) batteryLevel = 0;
    }

    private void charge() {
        System.out.println("🔋 " + id + " is charging...");
        try {
            Thread.sleep(2000); // simulate charging delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        batteryLevel = 100;
        System.out.println("✅ " + id + " is fully charged!");
    }

    public void pickAndSort(WasteItem item, StorageBin bin) {
        System.out.println("🤖 " + id + " picked up " + item.getType());
        bin.addItem(item);
    }

    @Override
    public void run() {
        System.out.println("🚀 " + id + " started working...");
        while (active) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            batteryLevel -= 5;
            if (batteryLevel <= 10) {
                System.out.println("⚡ " + id + " battery critical, recharging...");
                charge();
            }
        }
        System.out.println("🛑 " + id + " stopped.");
    }
}


// Represents an autonomous robot that picks and sorts waste items into bins. Later, it will run as a thread (implements Runnable).