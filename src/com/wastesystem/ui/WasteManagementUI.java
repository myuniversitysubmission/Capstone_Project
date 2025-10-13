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


    private TextArea logArea;
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

        // === Layout ===
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
        robotBox.getChildren().addAll(robot1Status, battery1Label, robot2Status, battery2Label);

        // --- Right: Bin Fill Levels ---
        VBox binBox = new VBox(10);
        binBox.setPadding(new Insets(10));
        binBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");
        binBox.getChildren().add(new Label("🗑️ Bin Fill Levels"));

        plasticProgress = new ProgressBar(0);
        metalProgress = new ProgressBar(0);
        paperProgress = new ProgressBar(0);
        organicProgress = new ProgressBar(0);

        binBox.getChildren().addAll(
                new Label("Plastic Bin"), plasticProgress,
                new Label("Metal Bin"), metalProgress,
                new Label("Paper Bin"), paperProgress,
                new Label("Organic Bin"), organicProgress
        );

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

        root.setLeft(robotBox);
        root.setCenter(logArea);
        root.setRight(binBox);
        root.setBottom(btnBox);

        Scene scene = new Scene(root, 950, 550);
        stage.setScene(scene);
        stage.show();

        setupSystem();

        // Button actions
        startBtn.setOnAction(e -> startSimulation());
        resetBtn.setOnAction(e -> resetSystem());
    }

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

    private void startSimulation() {
        log("🚦 Simulation Started...");
        robot1Status.setText("Robot-1: Active");
        robot2Status.setText("Robot-2: Active");

        // Generate random waste tasks continuously
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
            log("✅ Simulation Completed!");
        }).start();
    }

    private void updateBinsUI() {
        Platform.runLater(() -> {
            double plasticFill = plasticBin.getCurrentSize() / plasticBin.getCapacity();
            double metalFill = metalBin.getCurrentSize() / metalBin.getCapacity();
            double paperFill = paperBin.getCurrentSize() / paperBin.getCapacity();
            double organicFill = organicBin.getCurrentSize() / organicBin.getCapacity();
       
            plasticProgress.setProgress(Math.min(plasticFill, 1.0));
            metalProgress.setProgress(Math.min(metalFill, 1.0));
            paperProgress.setProgress(Math.min(paperFill, 1.0));
            organicProgress.setProgress(Math.min(organicFill, 1.0));
        });
    }
    public static void refreshBars() {
        if (instance != null) {
            instance.updateBinsUI();
        }
    }


    private void updateBatteryStatus() {
        Platform.runLater(() -> {
            battery1Label.setText("Battery: " + robot1.getBatteryLevel() + "%");
            battery2Label.setText("Battery: " + robot2.getBatteryLevel() + "%");
        });
    }

    private void resetSystem() {
        logArea.clear();
        plasticProgress.setProgress(0);
        metalProgress.setProgress(0);
        paperProgress.setProgress(0);
        organicProgress.setProgress(0);
        robot1Status.setText("Robot-1: Idle");
        robot2Status.setText("Robot-2: Idle");
        log("🔁 System Reset Complete.");
    }

    private void log(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
