package com.wastesystem.utils;

/**
 * Custom exception class for the Waste Sorting System.
 * Used to handle domain-specific errors like wrong bin type or full capacity.
 */
public class SystemException extends Exception {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

// SystemException: Custom exception class for project-related errors.