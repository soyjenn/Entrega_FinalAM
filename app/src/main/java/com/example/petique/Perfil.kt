package com.example.petique

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun PantallaPerfil(navController: NavController, documento: String) {
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    LaunchedEffect(documento) {
        val prefs = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
        nombre = prefs.getString("nombre_$documento", "") ?: ""
        apellido = prefs.getString("apellido_$documento", "") ?: ""
        correo = prefs.getString("email_$documento", "") ?: ""
    }

    val rosaBorde = Color(0xFFE5A8C7)
    val rosaTitulo = Color(0xFFA54A6A)
    val rosaBoton = Color(0xFFE5A8C7)
    val grisTexto = Color(0xFF4A4A4A)

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.fillMaxHeight().width(12.dp).align(Alignment.CenterStart).background(rosaBorde))
        Box(modifier = Modifier.fillMaxHeight().width(12.dp).align(Alignment.CenterEnd).background(rosaBorde))

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 35.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver", fontSize = 18.sp, color = grisTexto)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text("Perfil", fontSize = 34.sp, fontWeight = FontWeight.Bold, color = rosaTitulo)
            Spacer(modifier = Modifier.height(50.dp))
            Text("Nombre: $nombre $apellido", fontSize = 22.sp, color = grisTexto)
            Spacer(modifier = Modifier.height(15.dp))
            Text("Correo: $correo", fontSize = 22.sp, color = grisTexto)
            Spacer(modifier = Modifier.height(15.dp))
            Text("Documento: $documento", fontSize = 22.sp, color = grisTexto)
            Spacer(modifier = Modifier.height(70.dp))

            // ⭐ CORREGIDO: "citas" en lugar de "vicitas"
            Button(
                onClick = { navController.navigate("citas/$documento") },
                modifier = Modifier.width(200.dp).height(55.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = rosaBoton)
            ) {
                Text("Citas", fontSize = 18.sp, color = grisTexto)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("splash")
                    }
                },
                modifier = Modifier.width(200.dp).height(55.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = rosaBoton)
            ) {
                Text("Log Out", fontSize = 18.sp, color = grisTexto)
            }
        }
    }
}