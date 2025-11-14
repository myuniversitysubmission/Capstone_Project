package com.wastesystem.utils;

import com.wastesystem.recycling.RecyclingPlant;
import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import com.wastesystem.utils.SystemException;

public class RecyclingTest {

    public static void main(String[] args) throws SystemException {
        StorageBin metalBin = new StorageBin(WasteItem.Type.METAL, 2.0);
        RecyclingPlant recyclingPlant = new RecyclingPlant();

        System.out.println("🔬 [TEST] RecyclingTest started...");

        WasteItem item1 = new WasteItem(WasteItem.Type.METAL, 1.2);
        WasteItem item2 = new WasteItem(WasteItem.Type.METAL, 0.8);

        metalBin.addItem(item1);
        recyclingPlant.processRecycling(metalBin); // not full yet
        metalBin.addItem(item2);
        recyclingPlant.processRecycling(metalBin); // triggers recycling

        try {
            Thread.sleep(3000); // allow async recycle to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (metalBin.getCurrentSize() == 0)
            System.out.println("✅ Recycling process completed and bin cleared.");
        else
            System.out.println("❌ Recycling process failed. Bin not cleared.");

        System.out.println("🔬 [TEST] RecyclingTest finished.");
    }
}
