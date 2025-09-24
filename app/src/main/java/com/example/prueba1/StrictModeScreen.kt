package com.example.prueba1



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrictModeScreen() {
    var selected by remember { mutableStateOf(25) }
    var running by remember { mutableStateOf(false) }
    var remaining by remember { mutableStateOf(selected * 60) }

    // Si cambias el tiempo, resetea el contador (solo si no está corriendo)
    LaunchedEffect(selected) {
        if (!running) remaining = selected * 60
    }

    // Tick del temporizador (1s) mientras esté corriendo
    LaunchedEffect(running) {
        while (running && remaining > 0) {
            delay(1000)
            remaining -= 1
        }
        if (remaining == 0) {
            running = false // terminó
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Modo estricto") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Evita distracciones. El bloqueo real de apps llegará pronto.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // Chips de tiempo
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf(25, 50, 90).forEach { m ->
                    FilterChip(
                        selected = selected == m,
                        onClick = { if (!running) selected = m },
                        label = { Text("$m min") }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Reloj mm:ss
            val mm = "%02d".format(remaining / 60)
            val ss = "%02d".format(remaining % 60)
            Text("$mm:$ss", style = MaterialTheme.typography.displayMedium)

            Spacer(Modifier.height(16.dp))

            // Controles
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { if (!running && remaining > 0) running = true }, enabled = !running && remaining > 0) {
                    Text(if (remaining == 0) "Completado" else "Iniciar")
                }
                OutlinedButton(onClick = { running = false }, enabled = running) {
                    Text("Pausar")
                }
                OutlinedButton(onClick = {
                    running = false
                    remaining = selected * 60
                }) {
                    Text("Reiniciar")
                }
            }

            Spacer(Modifier.height(24.dp))

            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Bloqueo de aplicaciones", style = MaterialTheme.typography.titleMedium)
                    Text("Próximamente: cierre automático de apps prohibidas, DND y anti-salida.")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {}, enabled = false) { Text("Activar (próximamente)") }
                }
            }
        }
    }
}
