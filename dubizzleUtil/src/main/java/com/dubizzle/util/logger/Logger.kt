package com.dubizzle.util.logger

/**
 * Interface for logging messages with different severity levels.
 *
 * This interface defines methods for logging error, debug, and informational messages.
 * Implementations of this interface can be used to send logs to various destinations,
 * such as the console, a file, or a remote logging service.
 */
interface Logger {
    fun error(tag: String, e: Throwable, message: String)
    fun debug(tag: String, message: String)
    fun info(tag: String, message: String)
}