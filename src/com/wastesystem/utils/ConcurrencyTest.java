package com.wastesystem.utils;

import com.wastesystem.recycling.RecyclingPlant;
import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import com.wastesystem.utils.SystemException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTest {

    public static void main(String[] args) {
        StorageBin paperBin = new StorageBin(WasteItem.Type.PAPER, 5.0);
        RecyclingPlant recycler = new RecyclingPlant();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("🔬 [TEST] ConcurrencyTest started...");

        for (int i = 1; i <= 3; i++) {
            int robotId = i;
            executor.submit(() -> {
                for (int j = 0; j < 3; j++) {
                    try {
                        WasteItem item = new WasteItem(WasteItem.Type.PAPER, 1.0 + (Math.random() * 0.5));
                        synchronized (paperBin) {
                            paperBin.addItem(item);
                        }
                        recycler.processRecycling(paperBin);
                    } catch (SystemException e) {
                        System.err.println("[Robot-" + robotId + "] ⚠ Error: " + e.getMessage());
                    }
                    try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                }
            });
        }

        executor.shutdown();
        System.out.println("✅ Submitted all concurrent tasks. Check logs for stability.");
    }
}
