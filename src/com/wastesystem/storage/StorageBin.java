package com.wastesystem.storage;

import java.util.ArrayList;
import java.util.List;
import com.wastesystem.utils.SystemException;
import com.wastesystem.utils.LogHandler;

public class StorageBin {

    private WasteItem.Type binType;
    private double capacity;
    private List<WasteItem> contents;

    public StorageBin(WasteItem.Type type, double capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Bin capacity must be positive!");
        }
        this.binType = type;
        this.capacity = capacity;
        this.contents = new ArrayList<>();
    }

    public WasteItem.Type getBinType() {
        return binType;
    }

    public double getCapacity() {
        return capacity;
    }

    /**
     * Adds a waste item to the bin if the type matches and capacity allows.
     * @throws SystemException if wrong type or bin is full
     */
    
    public void addItem(WasteItem item) throws SystemException {
        if (item.getType() != binType) {
            LogHandler.error("Wrong bin type! Expected " + binType + " but got " + item.getType(),
                    new SystemException("Type mismatch"));
            throw new SystemException("Wrong bin type! Expected " + binType + " but got " + item.getType());
        }

        double totalWeight = getCurrentSize() + item.getWeight();
        if (totalWeight > capacity) {
            LogHandler.warn("♻️ " + binType + " bin reached full capacity. Sending for recycling...");

            // ✅ Trigger recycling automatically when full
            com.wastesystem.recycling.RecyclingPlant plant = new com.wastesystem.recycling.RecyclingPlant();
            plant.processRecycling(this);

            // reset bin after recycling simulation — safe fallback
            return;
        }

        contents.add(item);
        LogHandler.info("✅ Added " + item.getWeight() + " kg of " + item.getType() +
                " to " + binType + " bin (" + String.format("%.2f", getCurrentSize()) + "/" + capacity + " kg)");
    }


    public double getCurrentSize() {
        double total = 0;
        for (WasteItem w : contents) {
            total += w.getWeight();
        }
        return total;
    }

    public void showContents() {
        LogHandler.info("📦 " + binType + " Bin contains: " + contents.size() + " items.");
        for (WasteItem item : contents) {
            LogHandler.info("  - " + item);
        }
    }

    public void reset() {
        contents.clear();
        LogHandler.info("♻️ " + binType + " bin has been emptied.");
    }
    
    public synchronized void clearContents() {
        contents.clear();
    }

}

// StorageBin stores similar waste items
