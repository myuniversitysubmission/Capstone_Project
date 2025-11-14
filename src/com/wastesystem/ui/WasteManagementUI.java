package com.wastesystem.ui;

import com.wastesystem.equipment.Robot;
import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import com.wastesystem.tasks.TaskManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

    public class WasteManagementUI extends Application {
    public static WasteManagementUI instance;
    private ProgressBar batteryBar1, batteryBar2;
    private TextArea logArea;
    private Label plasticWeightLabel, metalWeightLabel, paperWeightLabel, organicWeightLabel;
    private ProgressBar plasticProgress, metalProgress, paperProgress, organicProgress;
    private Label robot1Status, robot2Status, battery1Label, battery2Label;
    private StorageBin plasticBin, metalBin, paperBin, organicBin;
    private Robot robot1, robot2;
    private TaskManager taskManager;
    private Random random = new Random();

    @Override
    public void start(Stage stage) {
        instance = this;

        stage.setTitle("♻️ Smart Waste Sorting & Recycling System");

        // === Main layout ===
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        Label title = new Label("Automated Waste Sorting Dashboard");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        root.setTop(title);
        BorderPane.setMargin(title, new Insets(10, 0, 15, 0));

        // --- Left: Robot Info ---
        VBox robotBox = new VBox(10);
        robotBox.setPadding(new Insets(10));
        robotBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");
        robotBox.getChildren().add(new Label("🤖 Robot Status"));

        robot1Status = new Label("Robot-1: Idle");
        robot2Status = new Label("Robot-2: Idle");
        battery1Label = new Label("Battery: 100%");
        battery2Label = new Label("Battery: 100%");
        batteryBar1 = new ProgressBar(1.0);
        batteryBar2 = new ProgressBar(1.0);

        batteryBar1.setStyle("-fx-accent: #32CD32;");
        batteryBar2.setStyle("-fx-accent: #32CD32;");

        robotBox.getChildren().addAll(
                robot1Status, battery1Label, batteryBar1,
                robot2Status, battery2Label, batteryBar2
        );

        // --- Right: Bin Fill Levels ---
        VBox binBox = new VBox(10);
        binBox.setPadding(new Insets(10));
        binBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");
        binBox.getChildren().add(new Label("🗑️ Bin Fill Levels"));

        // progress bars (will fill dynamically)
        plasticProgress = new ProgressBar(0);
        metalProgress = new ProgressBar(0);
        paperProgress = new ProgressBar(0);
        organicProgress = new ProgressBar(0);

        // bin colors
        plasticProgress.setStyle("-fx-accent: #00bfff;");
        metalProgress.setStyle("-fx-accent: #ffcc00;");
        paperProgress.setStyle("-fx-accent: #00cc66;");
        organicProgress.setStyle("-fx-accent: #996633;");

        // --- Center: Log Area ---
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: lightgreen;");
        logArea.setPrefHeight(300);
        log("System Initialized...");

        // --- Bottom: Buttons ---
        Button startBtn = new Button("▶ Start Simulation");
        Button resetBtn = new Button("🔁 Reset");
        HBox btnBox = new HBox(10, startBtn, resetBtn);
        btnBox.setPadding(new Insets(10));

        // layout setup
        root.setLeft(robotBox);
        root.setCenter(logArea);
        root.setRight(binBox);
        root.setBottom(btnBox);

        Scene scene = new Scene(root, 950, 550);
        stage.setScene(scene);
        stage.show();

        // ✅ Initialize system first (creates bins)
        setupSystem();

        // ✅ Add bin weight labels dynamically after bins exist
        initializeWeightLabels(binBox);

        // Button actions
        startBtn.setOnAction(e -> startSimulation());
        resetBtn.setOnAction(e -> resetSystem());
    }
    
    /** Creates bins, robots, and connects them to the task manager */
    private void setupSystem() {
        plasticBin = new StorageBin(WasteItem.Type.PLASTIC, 5);
        metalBin = new StorageBin(WasteItem.Type.METAL, 5);
        paperBin = new StorageBin(WasteItem.Type.PAPER, 5);
        organicBin = new StorageBin(WasteItem.Type.ORGANIC, 5);

        robot1 = new Robot("Robot-1");
        robot2 = new Robot("Robot-2");

        taskManager = new TaskManager();
        taskManager.registerRobot(robot1);
        taskManager.registerRobot(robot2);
        taskManager.registerBin(plasticBin);
        taskManager.registerBin(metalBin);
        taskManager.registerBin(paperBin);
        taskManager.registerBin(organicBin);

        new Thread(robot1).start();
        new Thread(robot2).start();

        log("✅ System Ready. Press 'Start Simulation' to begin.");
    }

    /** Initializes weight labels after bins are created */
    private void initializeWeightLabels(VBox binBox) {
        plasticWeightLabel = new Label(String.format("0.00 / %.2f kg", plasticBin.getCapacity()));
        metalWeightLabel   = new Label(String.format("0.00 / %.2f kg", metalBin.getCapacity()));
        paperWeightLabel   = new Label(String.format("0.00 / %.2f kg", paperBin.getCapacity()));
        organicWeightLabel = new Label(String.format("0.00 / %.2f kg", organicBin.getCapacity()));

        binBox.getChildren().addAll(
                new Label("Plastic Bin"), plasticProgress, plasticWeightLabel,
                new Label("Metal Bin"),   metalProgress,   metalWeightLabel,
                new Label("Paper Bin"),   paperProgress,   paperWeightLabel,
                new Label("Organic Bin"), organicProgress, organicWeightLabel
        );
    }

    /** Starts the waste sorting simulation */
    private void startSimulation() {
        log("🚦 Simulation Started...");
        robot1Status.setText("Robot-1: Active");
        robot2Status.setText("Robot-2: Active");

        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                WasteItem.Type type = WasteItem.Type.values()[random.nextInt(WasteItem.Type.values().length)];
                double weight = 0.5 + random.nextDouble() * 1.5;
                WasteItem item = new WasteItem(type, weight);
                taskManager.addTask(item);
                log("📝 Added Task: " + type + " (" + String.format("%.2f", weight) + " kg)");
                try {
                    Thread.sleep(1500);
                    updateBinsUI();
                    updateBatteryStatus();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            taskManager.startProcessing();
            log("✅ Simulation Completed!");
        }).start();
    }

   /** Updates bin progress and labels dynamically */
    private void updateBinsUI() {

    Platform.runLater(() -> {
        double plasticFill = plasticBin.getCurrentSize() / plasticBin.getCapacity();
        double metalFill   = metalBin.getCurrentSize() / metalBin.getCapacity();
        double paperFill   = paperBin.getCurrentSize() / paperBin.getCapacity();
        double organicFill = organicBin.getCurrentSize() / organicBin.getCapacity();

        // --- update bar fill levels ---
        plasticProgress.setProgress(Math.min(plasticFill, 1.0));
        metalProgress.setProgress(Math.min(metalFill, 1.0));
        paperProgress.setProgress(Math.min(paperFill, 1.0));
        organicProgress.setProgress(Math.min(organicFill, 1.0));

        // --- update colors dynamically ---
        plasticProgress.setStyle("-fx-accent:" + getBinColor(plasticFill) + ";");
        metalProgress.setStyle("-fx-accent:" + getBinColor(metalFill) + ";");
        paperProgress.setStyle("-fx-accent:" + getBinColor(paperFill) + ";");
        organicProgress.setStyle("-fx-accent:" + getBinColor(organicFill) + ";");

        // --- update numeric labels ---
        plasticWeightLabel.setText(String.format("%.2f / %.2f kg", plasticBin.getCurrentSize(), plasticBin.getCapacity()));
        metalWeightLabel.setText(String.format("%.2f / %.2f kg", metalBin.getCurrentSize(), metalBin.getCapacity()));
        paperWeightLabel.setText(String.format("%.2f / %.2f kg", paperBin.getCurrentSize(), paperBin.getCapacity()));
        organicWeightLabel.setText(String.format("%.2f / %.2f kg", organicBin.getCurrentSize(), organicBin.getCapacity()));
    });

    // --- ♻️ After UI update, check recycling readiness ---
    com.wastesystem.recycling.RecyclingPlant plant = new com.wastesystem.recycling.RecyclingPlant();
    plant.processRecycling(plasticBin);
    plant.processRecycling(metalBin);
    plant.processRecycling(paperBin);
    plant.processRecycling(organicBin);
  }


    private String getBinColor(double fillRatio) {
        if (fillRatio < 0.6) return "#32CD32";  // green
        else if (fillRatio < 0.9) return "#FFD700";  // yellow
        else return "#FF4500";  // red
    }

    public static void refreshBars() {
        if (instance != null) {
            instance.updateBinsUI();
        }
    }

    private void updateBatteryStatus() {
        Platform.runLater(() -> {
            int b1 = robot1.getBatteryLevel();
            int b2 = robot2.getBatteryLevel();

            battery1Label.setText("Battery: " + b1 + "% / 100%");
            battery2Label.setText("Battery: " + b2 + "% / 100%");

            batteryBar1.setProgress(b1 / 100.0);
            batteryBar2.setProgress(b2 / 100.0);

            batteryBar1.setStyle("-fx-accent: " + getBatteryColor(b1) + ";");
            batteryBar2.setStyle("-fx-accent: " + getBatteryColor(b2) + ";");
        });
    }

    private String getBatteryColor(int level) {
        if (level > 60) return "#32CD32";
        else if (level > 30) return "#FFD700";
        else return "#FF4500";
    }

    private void resetSystem() {
    log("🔄 Resetting system...");

    // Stop old robots
    robot1.stopRobot();
    robot2.stopRobot();

    // Wait a bit to ensure threads stop cleanly
    try { Thread.sleep(500); } catch (InterruptedException ignored) {}

    // Clear log and reset bins
    logArea.clear();
    plasticBin = new StorageBin(WasteItem.Type.PLASTIC, plasticBin.getCapacity());
    metalBin   = new StorageBin(WasteItem.Type.METAL, metalBin.getCapacity());
    paperBin   = new StorageBin(WasteItem.Type.PAPER, paperBin.getCapacity());
    organicBin = new StorageBin(WasteItem.Type.ORGANIC, organicBin.getCapacity());

    // Create NEW robot objects (fresh battery, inactive)
    robot1 = new Robot("Robot-1");
    robot2 = new Robot("Robot-2");

    // Reset task manager
    taskManager = new TaskManager();
    taskManager.registerRobot(robot1);
    taskManager.registerRobot(robot2);
    taskManager.registerBin(plasticBin);
    taskManager.registerBin(metalBin);
    taskManager.registerBin(paperBin);
    taskManager.registerBin(organicBin);

    // Reset visual UI
    plasticProgress.setProgress(0);
    metalProgress.setProgress(0);
    paperProgress.setProgress(0);
    organicProgress.setProgress(0);

    batteryBar1.setProgress(1.0);
    batteryBar2.setProgress(1.0);
    batteryBar1.setStyle("-fx-accent: #32CD32;");
    batteryBar2.setStyle("-fx-accent: #32CD32;");
    battery1Label.setText("Battery: 100% / 100%");
    battery2Label.setText("Battery: 100% / 100%");
    robot1Status.setText("Robot-1: Idle");
    robot2Status.setText("Robot-2: Idle");

    plasticWeightLabel.setText(String.format("0.00 / %.2f kg", plasticBin.getCapacity()));
    metalWeightLabel.setText(String.format("0.00 / %.2f kg", metalBin.getCapacity()));
    paperWeightLabel.setText(String.format("0.00 / %.2f kg", paperBin.getCapacity()));
    organicWeightLabel.setText(String.format("0.00 / %.2f kg", organicBin.getCapacity()));

    // Log confirmation
    log("🔁 System Reset Complete. Robots reinitialized at 100% battery and all bins cleared.");
    }

 // ✅ Central UI logger — accessible from all classes
    public void log(String message) {
        Platform.runLater(() -> {
            if (logArea != null) {
                logArea.appendText(message + "\n");
            } else {
                System.out.println("[UI LOG FALLBACK] " + message);
            }
        });
    }




    public static void main(String[] args) {
        launch(args);
    }
}
