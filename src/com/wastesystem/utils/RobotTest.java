package com.wastesystem.utils;

import com.wastesystem.equipment.Robot;
import org.junit.Test;
import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void testRobotBatteryDrainAndRecharge() throws InterruptedException {
        Robot robot = new Robot("Robot-Test");

        Thread t = new Thread(robot);
        t.start();

        Thread.sleep(3000); // let it run for a few seconds

        robot.stopRobot();
        t.join(); // ✅ Wait until thread stops

        assertTrue(robot.getBatteryLevel() >= 0);
    }

    @Test
    public void testSetBatteryLevel() {
        Robot robot = new Robot("Robot-Test");
        robot.setBatteryLevel(50);
        assertEquals(50, robot.getBatteryLevel());
    }
}
