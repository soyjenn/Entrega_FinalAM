package com.example.petique

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaCitas(navController: NavController, documento: String) {

    val context = LocalContext.current
    val rosa = Color(0xFFFAD3E1)
    val textoRosa = Color(0xFF7C0E39)
    val rosaTenue = Color(0xFFFFF8FA)

    // Estado para refrescar cuando se elimine una cita
    var citas by remember { mutableStateOf(CitasManager.obtenerCitas(context, documento)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(rosaTenue)
            .padding(20.dp)
    ) {

        // Botón Volver
        Button(
            onClick = { navController.navigate("principal/$documento") {
                popUpTo("principal/$documento") { inclusive = false }
            }},
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(90.dp)
        ) {
            Text("Volver", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Mis Citas",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = textoRosa,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (citas.isEmpty()) {
            // No hay citas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No tienes citas agendadas",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { navController.navigate("agendar/$documento") },
                        colors = ButtonDefaults.buttonColors(rosa)
                    ) {
                        Text("Agendar una cita", color = Color.White)
                    }
                }
            }
        } else {
            // Lista de citas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(citas) { citaString ->
                    val citaData = CitasManager.parsearCita(citaString)
                    if (citaData.isNotEmpty()) {
                        CitaCard(
                            fecha = citaData["fecha"] ?: "",
                            hora = citaData["hora"] ?: "",
                            servicio = citaData["servicio"] ?: "",
                            sede = citaData["sede"] ?: "",
                            rosa = rosa,
                            textoRosa = textoRosa,
                            onEliminar = {
                                CitasManager.eliminarCita(context, documento, citaString)
                                citas = CitasManager.obtenerCitas(context, documento)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CitaCard(
    fecha: String,
    hora: String,
    servicio: String,
    sede: String,
    rosa: Color,
    textoRosa: Color,
    onEliminar: () -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = servicio,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textoRosa
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("📅 $fecha", fontSize = 16.sp, color = Color.DarkGray)
                Text("🕐 $hora", fontSize = 16.sp, color = Color.DarkGray)
                Text("📍 $sede", fontSize = 16.sp, color = Color.DarkGray)
            }

            IconButton(onClick = { mostrarDialogo = true }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFE57373),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Cancelar Cita") },
            text = { Text("¿Estás seguro de que deseas cancelar esta cita?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar()
                        mostrarDialogo = false
                    }
                ) {
                    Text("Sí, cancelar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("No")
                }
            }
        )
    }
}