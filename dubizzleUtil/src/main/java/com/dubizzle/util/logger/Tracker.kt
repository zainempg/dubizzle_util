package com.dubizzle.util.logger

/**
 * Interface for tracking events within the application.
 *
 * This interface defines a contract for sending tracking data to an analytics
 * or logging system. Implementations of this interface are responsible for
 * handling the actual transmission of the tracked events.
 */
interface Tracker {
    fun trackEvent(eventName: String,params:Map<String, Any>)
}