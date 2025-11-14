package com.wastesystem.utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Unit tests for LogHandler class.
 * Verifies file writing, formatting, and exception handling.
 */
public class LogHandlerTest {

    private static final String LOG_FILE = "system_log.txt";

    @Test
    public void testInfoLogWritesToFile() throws IOException {
        // Clean up any old log file
        File file = new File(LOG_FILE);
        if (file.exists()) file.delete();

        // Log a message
        LogHandler.info("Test log message");

        // Check file exists
        assertTrue("Log file should exist", file.exists());

        // Read last line from log file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String lastLine = null;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            assertNotNull("Log file should contain content", lastLine);
            assertTrue("Log entry should contain INFO tag", lastLine.contains("[INFO]"));
            assertTrue("Log entry should contain test message", lastLine.contains("Test log message"));
        }
    }

    @Test
    public void testErrorLogFormatting() throws IOException {
        Exception sampleException = new Exception("Simulated error");
        LogHandler.error("Error occurred while processing waste", sampleException);

        File file = new File(LOG_FILE);
        assertTrue("Log file should exist", file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[ERROR]") && line.contains("Simulated error")) {
                    found = true;
                    break;
                }
            }
            assertTrue("Error log should be formatted with [ERROR] tag and exception message", found);
        }
    }

    @Test
    public void testWarnLogFormatting() throws IOException {
        LogHandler.warn("Charging station slot occupied");

        File file = new File(LOG_FILE);
        assertTrue("Log file should exist", file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[WARN]") && line.contains("Charging station")) {
                    found = true;
                    break;
                }
            }
            assertTrue("Warning log should contain [WARN] and proper message", found);
        }
    }
}
