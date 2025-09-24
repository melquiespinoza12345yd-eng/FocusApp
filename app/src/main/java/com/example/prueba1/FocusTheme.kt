
package com.example.prueba1.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun FocusTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    MaterialTheme(
        colorScheme = scheme,
        content = content
    )
}
