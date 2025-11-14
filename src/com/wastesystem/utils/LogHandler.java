package com.wastesystem.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LogHandler - Unified logging utility for the Waste Sorting System.
 * Supports timestamped console + file logging with severity levels.
 * Thread-safe and auto-formatted for readability.
 */
public class LogHandler {

    private static final String LOG_FILE = "system_log.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Private constructor prevents instantiation
    private LogHandler() {}

    /**
     * Generic log writer (thread-safe).
     */
    public static synchronized void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logMessage = "[" + timestamp + "] [" + level.toUpperCase() + "] " + message;
        System.out.println(logMessage);
        writeToFile(logMessage);
    }

    /**
     * Information logs — system events.
     */
    public static void info(String message) {
        log("INFO", message);
    }

    /**
     * Warning logs — non-critical issues.
     */
    public static void warn(String message) {
        log("WARN", message);
    }

    /**
     * Error logs — critical failures or exceptions.
     */
    public static void error(String message, Exception e) {
        log("ERROR", message + " | " + e.getMessage());
    }

    /**
     * Writes logs to an external file.
     */
    private static void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("[LogHandler] Failed to write log: " + e.getMessage());
        }
    }
}

// LogHandler: Manages all log printing or file logging.
