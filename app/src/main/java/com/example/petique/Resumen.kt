package com.example.petique

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaResumenCita(
    navController: NavController,
    documento: String,
    fecha: String,
    hora: String,
    servicio: String,
    sede: String
) {
    val duracion = when (servicio) {
        "Paquete 1" -> "40 minutos"
        "Paquete 2" -> "40 minutos"
        "Paquete 3" -> "60 minutos"
        "Vacunación" -> "20 minutos"
        "Chequeo de Rutina" -> "20 minutos"
        else -> "40 minutos"
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(Color(0xFFF8DCE6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD98AA0)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.Start)
                .width(90.dp)
        ) {
            Text("Volver", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Resumen de tu Cita",
            fontSize = 28.sp,
            color = Color(0xFF5B1F2E),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(25.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Mascota",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(35.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Servicio: $servicio", fontSize = 18.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Fecha: $fecha", fontSize = 18.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Hora: $hora", fontSize = 18.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Sede: $sede", fontSize = 18.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Duración: $duracion", fontSize = 18.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Veterinaria PETIQUE - Amor y Cuidados",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5B1F2E)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                modifier = Modifier.width(150.dp)
            ) {
                Text("Cancelar", color = Color.DarkGray)
            }

            // ⭐ BOTÓN CONFIRMAR - VA A PANTALLA CITAS
            Button(
                onClick = {
                    // Guardar la cita
                    CitasManager.guardarCita(
                        context = context,
                        documento = documento,
                        fecha = fecha,
                        hora = hora,
                        servicio = servicio,
                        sede = sede
                    )

                    // Mostrar confirmación
                    Toast.makeText(
                        context,
                        "✅ Cita agendada exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    // ⭐ IR A VER LAS CITAS
                    navController.navigate("citas/$documento") {
                        popUpTo("principal/$documento") { inclusive = false }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFD98AA0)),
                modifier = Modifier.width(150.dp)
            ) {
                Text("Confirmar", color = Color.DarkGray)
            }
        }
    }
}