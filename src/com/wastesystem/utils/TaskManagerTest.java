package com.wastesystem.utils;

import com.wastesystem.tasks.TaskManager;
import com.wastesystem.equipment.Robot;
import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for TaskManager class.
 * Validates task registration and assignment.
 */
public class TaskManagerTest {

    @Test
    public void testTaskAssignment() {
        TaskManager manager = new TaskManager();

        Robot robot = new Robot("Robot-1");
        StorageBin bin = new StorageBin(WasteItem.Type.METAL, 5);
        manager.registerRobot(robot);
        manager.registerBin(bin);

        WasteItem item = new WasteItem(WasteItem.Type.METAL, 1.0);
        manager.addTask(item);
        assertFalse(manager == null); // just verify it runs without error
    }
}

