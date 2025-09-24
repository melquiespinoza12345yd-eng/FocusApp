package com.example.prueba1


object SessionRepository {
    private val sessions = mutableListOf<Session>()
    fun logSession(session: Session) { sessions.add(session) }
    fun all(): List<Session> = sessions.toList()
}
