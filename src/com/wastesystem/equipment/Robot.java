package com.wastesystem.equipment;

import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import com.wastesystem.utils.SystemException;
import com.wastesystem.utils.LogHandler;

public class Robot implements Runnable {

    private String id;
    private int batteryLevel;
    private boolean active;

    public Robot(String id) {
        this.id = id;
        this.batteryLevel = 100; // start full
        this.active = true;
    }

    // Getters
    public String getId() {
        return id;
    }

    public synchronized int getBatteryLevel() {
        return batteryLevel;
    }

    public synchronized void setBatteryLevel(int batteryLevel) {
        if (batteryLevel < 0) batteryLevel = 0;
        if (batteryLevel > 100) batteryLevel = 100;
        this.batteryLevel = batteryLevel;
    }

    public void stopRobot() {
        active = false;
    }

    /** Simulates picking and sorting waste */
    public void pickAndSort(WasteItem item, StorageBin bin) {
        LogHandler.info("🤖 " + id + " picked up " + item.getType());
        try {
            bin.addItem(item);
            LogHandler.info(id + " successfully sorted " + item.getType());
        } catch (SystemException e) {
            LogHandler.error(id + " failed to sort " + item.getType() + ": " + e.getMessage(), e);
        }
    }

    /** Simulates gradual battery drain while running */
    private synchronized void drainBattery() {
        batteryLevel -= 10;
        if (batteryLevel < 0) batteryLevel = 0;

        com.wastesystem.ui.WasteManagementUI.refreshBars();
    }

    /** Simulates robot charging cycle */
    private void charge() {
        LogHandler.info("🔋 " + id + " is charging...");
        try {
            Thread.sleep(2000); // simulate charging delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        setBatteryLevel(100);
        LogHandler.info("✅ " + id + " is fully charged!");
        com.wastesystem.ui.WasteManagementUI.refreshBars();
    }

    /** Allows clean reinitialization during reset */
    public void reset() {
        active = false;
        setBatteryLevel(100);
        LogHandler.info("♻️ " + id + " reset to idle with full battery.");
    }

    /** Main robot thread loop */
    @Override
    public void run() {
        LogHandler.info("🚀 " + id + " started working...");
        while (active) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            drainBattery();

            if (getBatteryLevel() <= 10) {
                LogHandler.info("⚡ " + id + " battery critical, recharging...");
                charge();
            }
        }
        LogHandler.info("🛑 " + id + " stopped.");
    }
}


// Represents an autonomous robot that picks and sorts waste items into bins. Later, it will run as a thread (implements Runnable).