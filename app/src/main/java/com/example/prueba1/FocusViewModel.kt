package com.example.prueba1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FocusViewModel(
    private val savedState: SavedStateHandle
) : ViewModel() {

    private companion object {
        const val KEY_COUNT = "count"
    }

    private val mutableCount = MutableStateFlow(savedState.get<Int>(KEY_COUNT) ?: 0)
    val count: StateFlow<Int> = mutableCount

    fun increment() {
        val next = mutableCount.value + 1
        mutableCount.value = next
        savedState[KEY_COUNT] = next
    }

    fun reset() {
        mutableCount.value = 0
        savedState[KEY_COUNT] = 0
    }
}

