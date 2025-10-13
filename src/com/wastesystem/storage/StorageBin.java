package com.wastesystem.storage;

import java.util.ArrayList;
import java.util.List;

public class StorageBin {

    private WasteItem.Type binType;
    private double capacity;
    private List<WasteItem> contents;

    public StorageBin(WasteItem.Type type, double capacity) {
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

    public void addItem(WasteItem item) {
        if (item.getType() == binType) {
            contents.add(item);
            System.out.println("✅ Added " + item + " to " + binType + " bin.");
        } else {
            System.out.println("❌ Wrong bin! " + item.getType() + " cannot go in " + binType + " bin.");
        }
    }

    public double getCurrentSize() {
        double total = 0;
        for (WasteItem w : contents) {
            total += w.getWeight();
        }
        return total;
    }


    public void showContents() {
        System.out.println("📦 " + binType + " Bin contains: " + contents.size() + " items.");
        for (WasteItem item : contents) {
            System.out.println("  - " + item);
        }
    }
}


//StorageBin stores similar waste items