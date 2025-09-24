
package com.example.prueba1.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(private val repo: ThemeRepository) : ViewModel() {

    private val _isDark = MutableStateFlow(repo.isDark())
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    fun toggleDark(enabled: Boolean) {
        _isDark.value = enabled
        repo.setDark(enabled)
    }

    companion object {
        fun factory(repo: ThemeRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ThemeViewModel(repo) as T
            }
        }
    }
}
