
package com.example.prueba1.theme

import android.content.Context
import android.content.Context.MODE_PRIVATE

class ThemeRepository(context: Context) {
    private val prefs = context.getSharedPreferences("theme_prefs", MODE_PRIVATE)
    fun isDark(): Boolean = prefs.getBoolean("dark", false)
    fun setDark(value: Boolean) { prefs.edit().putBoolean("dark", value).apply() }
}
