package com.dubizzle.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/**
 * This class provides functionality to manage and update the application's locale.
 * It allows changing the locale programmatically and applying it to the application context.
 *
 * @property locale The target [Locale] to be applied.
 */
class Localization(var locale: Locale) {

    /**
     * Attaches a new base context with the specified locale.
     *
     * This function takes an existing context and modifies its configuration to use a new locale.
     * It then creates a new context based on the modified configuration. This is useful for
     * changing the application's language on the fly.
     *
     * The function sets the default locale for the JVM to the provided locale. Then, it creates
     * a new configuration based on the original context's configuration and overrides the locale
     * within that configuration. Finally, it uses `createConfigurationContext` to generate a
     * brand-new context using the updated configuration.
     *
     * @param context The original context. Can be null.
     * @return A new context with the specified locale, or the original context if the input was null.
     *         If the original context was not null but an error occur when creating the new context,
     *         it will return null.
     *
     * @see Context.createConfigurationContext
     * @see Configuration.setLocale
     * @see Locale.setDefault
     *
     * @sample
     * ```
     * // Example usage within an Activity or Application class:
     * override fun attachBaseContext(base: Context) {
     *     val newLocale = Locale("es", "ES") // Example: Spanish (Spain)
     *     val newContext = attachBaseContext(base, newLocale)
     *     super.attachBaseContext(newContext ?: base)
     * }
     *
     * // Example usage to get a new context:
     * val myNewContext: Context? = attachBaseContext(applicationContext, Locale("fr", "FR"))
     * ```
     */
    fun attachBaseContext(context: Context?): Context? {
        Locale.setDefault(locale);
        val config = Configuration(context?.resources?.configuration)
        config.setLocale(locale)
        return context?.createConfigurationContext(config)
    }

}