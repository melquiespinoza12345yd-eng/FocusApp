package com.example.prueba1


enum class FocusMode { POMODORO, STRICT }

data class Session(
    val id: Long = System.currentTimeMillis(),
    val mode: FocusMode,
    val minutes: Int,
    val startedAt: Long = System.currentTimeMillis(),
    val endedAt: Long? = null
)
