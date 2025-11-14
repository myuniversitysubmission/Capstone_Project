package com.wastesystem.recycling;

import com.wastesystem.storage.StorageBin;
import com.wastesystem.utils.LogHandler;
import javafx.application.Platform;
import com.wastesystem.ui.WasteManagementUI;

public class RecyclingPlant {

    public void processRecycling(StorageBin bin) {
        double fillRatio = bin.getCurrentSize() / bin.getCapacity();

        if (fillRatio >= 1.0) {
            String message = "♻️ " + bin.getBinType() + " bin reached full capacity — sent for recycling...";
            LogHandler.warn(message);

            // ✅ log to UI dashboard
            Platform.runLater(() -> {
                if (WasteManagementUI.instance != null)
                    WasteManagementUI.instance.log(message);
            });

            simulateRecycling(bin);
        }
        
    }

    private void simulateRecycling(StorageBin bin) {
        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    if (WasteManagementUI.instance != null)
                        WasteManagementUI.instance.log("⏳ Recycling in progress for " + bin.getBinType() + " materials...");
                });

                Thread.sleep(2500); // simulate delay

                synchronized (bin) {
                    bin.clearContents();

                    LogHandler.info("✅ " + bin.getBinType() + " materials recycled successfully. Bin cleared.");

                    Platform.runLater(() -> {
                        if (WasteManagementUI.instance != null) {
                            WasteManagementUI.instance.log("✅ " + bin.getBinType() + " materials recycled successfully. Bin cleared.");
                            WasteManagementUI.refreshBars();
                        }
                    });
                }
            } catch (InterruptedException e) {
                LogHandler.error("Recycling process interrupted for " + bin.getBinType(), e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    
}
