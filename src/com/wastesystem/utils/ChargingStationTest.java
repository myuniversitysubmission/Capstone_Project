package com.wastesystem.utils;

import com.wastesystem.charging.ChargingStation;
import com.wastesystem.equipment.Robot;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChargingStationTest {

    @Test
    public void testRobotCharging() {
        ChargingStation station = new ChargingStation(1);
        Robot robot = new Robot("Robot-Test");

        robot.setBatteryLevel(20); // simulate low battery
        station.chargeRobot(robot);

        assertEquals(100, robot.getBatteryLevel());
    }
}
