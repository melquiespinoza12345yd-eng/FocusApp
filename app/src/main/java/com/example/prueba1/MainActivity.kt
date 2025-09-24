
package com.example.prueba1 //


// Íconos
// Pantalla Estricto (cascarón)

// Tema (NUEVO)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prueba1.theme.FocusTheme
import com.example.prueba1.theme.ThemeRepository
import com.example.prueba1.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeRepo = ThemeRepository(applicationContext)

        setContent {
            // Tus VMs
            val vm: FocusViewModel = viewModel()
            val themeVm: ThemeViewModel = viewModel(factory = ThemeViewModel.factory(themeRepo))
            val dark by themeVm.isDark.collectAsState()

            FocusTheme(darkTheme = dark) {
                AppRoot(vm = vm, themeVm = themeVm)
            }
        }
    }
}

// -------------------- Navegación + BottomBar --------------------

data class NavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot(vm: FocusViewModel, themeVm: ThemeViewModel) {
    val nav = rememberNavController()
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val topRoutes = setOf("home", "stats", "settings", "strict")
    val showBottomBar = currentRoute?.substringBefore("/") in topRoutes

    val items = listOf(
        NavItem(route = "home",    label = "Home",     icon = Icons.Filled.Home),
        NavItem(route = "stats",   label = "Stats",    icon = Icons.Filled.BarChart),
        NavItem(route = "strict",  label = "Estricto", icon = Icons.Filled.Lock),
        NavItem(route = "settings",label = "Settings", icon = Icons.Filled.Settings),
    )

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("FocusApp") }) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    val destination = backStackEntry?.destination
                    items.forEach { item ->
                        val selected = destination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                nav.navigate(item.route) {
                                    popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            AppNav(nav = nav, vm = vm, themeVm = themeVm)
        }
    }
}

@Composable
fun AppNav(
    nav: NavHostController,
    vm: FocusViewModel,
    themeVm: ThemeViewModel
) {
    NavHost(navController = nav, startDestination = "home") {

        composable("home") {
            HomeScreen(
                count = vm.count.collectAsState().value,
                onClick = { vm.increment() },
                goDetail = { nav.navigate("detail/Hola desde Home") }
            )
        }

        // NUEVO destino: Estricto
        composable("strict") {
            StrictModeScreen()
        }

        composable("stats") {
            StatsScreen(
                totalClicks = vm.count.collectAsState().value,
                onReset = { vm.reset() }
            )
        }

        // SETTINGS con switch de modo oscuro
        composable("settings") {
            val dark by themeVm.isDark.collectAsState()
            SettingsScreen(
                darkEnabled = dark,
                onToggleDark = { themeVm.toggleDark(it) }
            )
        }

        // Pantalla sin BottomBar (queda dentro del NavHost)
        composable("detail/{message}") { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: "Sin mensaje"
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pantalla Detalle")
                Spacer(Modifier.height(8.dp))
                Text("Mensaje: $message")
                Spacer(Modifier.height(16.dp))
                Button(onClick = { nav.popBackStack() }) { Text("Volver") }
            }
        }
    }
}

// -------------------- Pantallas demo existentes --------------------

@Composable
fun HomeScreen(
    count: Int,
    onClick: () -> Unit,
    goDetail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello FocusApp 👋", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onClick) { Text("Tocaste $count veces") }
        Spacer(Modifier.height(16.dp))
        Button(onClick = goDetail) { Text("Ir a Detalle") }
    }
}

@Composable
fun StatsScreen(
    totalClicks: Int,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Estadísticas 📊", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Clicks acumulados: $totalClicks")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onReset) { Text("Reiniciar contador") }
    }
}

// Settings con toggle de modo oscuro
@Composable
fun SettingsScreen(
    darkEnabled: Boolean,
    onToggleDark: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Ajustes ⚙️", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tema oscuro", modifier = Modifier.weight(1f))
            Switch(checked = darkEnabled, onCheckedChange = onToggleDark)
        }
        Spacer(Modifier.height(8.dp))
        Text(if (darkEnabled) "Usando tema oscuro 🌙" else "Usando tema claro ☀️")
    }
}
