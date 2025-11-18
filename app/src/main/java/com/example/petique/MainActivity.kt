package com.example.petique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar Firebase al arrancar la app
        FirebaseApp.initializeApp(this)
        setContent {
            PetiqueApp()
        }
    }
}

@Composable
fun PetiqueApp() {
    val navController = rememberNavController()

    Surface(color = Color.White) {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            // PANTALLA SPLASH
            composable("splash") { PantallaSplash(navController) }

            // PANTALLA INICIO (Login/Registro botones)
            composable("inicio") { PantallaInicio(navController) }

            // PANTALLA LOGIN
            composable("login") { PantallaLogin(navController) }

            // PANTALLA REGISTRO
            composable("registro") { PantallaRegistro(navController) }

            // ⭐ PRINCIPAL - RECIBE DOCUMENTO DEL USUARIO
            composable(
                route = "principal/{documento}",
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                PantallaPrincipal(navController, documento)
            }

            // ⭐ AGENDAR CITA 1 (Datos mascota) - PASAN DOCUMENTO
            composable(
                route = "agendar/{documento}",
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                AgendarCita(navController, documento)
            }

            // ⭐⭐⭐ AGENDAR CITA 2 (Fecha/Hora) - AHORA RECIBE DOCUMENTO Y ID DE CITA ⭐⭐⭐
            composable(
                route = "agendar2/{documento}/{idCita}", // <--- RUTA ACTUALIZADA
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType },
                    navArgument("idCita") { type = NavType.StringType } // <--- NUEVO ARGUMENTO
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                val idCita = backStackEntry.arguments?.getString("idCita") ?: "" // <--- RECUPERAMOS EL ID

                // Pasamos ambos datos a la pantalla
                AgendarCita2(navController, documento, idCita)
            }

            // ⭐ RUTA RESUMEN CITA - RECIBE DOCUMENTO Y DETALLES
            composable(
                route = "resumen/{documento}/{fecha}/{hora}/{servicio}/{sede}",
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType },
                    navArgument("fecha") { type = NavType.StringType },
                    navArgument("hora") { type = NavType.StringType },
                    navArgument("servicio") { type = NavType.StringType },
                    navArgument("sede") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                // Decodificamos los textos que pueden tener espacios o caracteres especiales
                val fecha = URLDecoder.decode(backStackEntry.arguments?.getString("fecha") ?: "", "UTF-8")
                val hora = URLDecoder.decode(backStackEntry.arguments?.getString("hora") ?: "", "UTF-8")
                val servicio = URLDecoder.decode(backStackEntry.arguments?.getString("servicio") ?: "", "UTF-8")
                val sede = URLDecoder.decode(backStackEntry.arguments?.getString("sede") ?: "", "UTF-8")

                PantallaResumenCita(
                    navController = navController,
                    documento = documento,
                    fecha = fecha,
                    hora = hora,
                    servicio = servicio,
                    sede = sede
                )
            }

            // ⭐ PERFIL
            composable(
                route = "perfil/{documento}",
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                PantallaPerfil(
                    navController = navController,
                    documento = documento
                )
            }

            // UBICACIÓN
            composable("ubicacion") { PantallaUbicacion(navController) }

            // ⭐ PANTALLA DE MIS CITAS
            composable(
                route = "citas/{documento}",
                arguments = listOf(
                    navArgument("documento") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val documento = backStackEntry.arguments?.getString("documento") ?: ""
                PantallaCitas(navController, documento)
            }

            // ⭐ INFORMACIÓN DE SERVICIO
            composable(
                route = "info/{servicio}",
                arguments = listOf(
                    navArgument("servicio") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val servicio = URLDecoder.decode(
                    backStackEntry.arguments?.getString("servicio") ?: "",
                    "UTF-8"
                )
                PantallaInfo(navController, servicio)
            }
        }
    }
}
