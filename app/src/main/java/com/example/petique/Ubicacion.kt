package com.example.petique

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaUbicacion(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Nuestras ubicaciones", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Sede Norte: Calle Falsa 123")
        Text("Sede Sur: Av. Siempre Viva 456")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Volver") }
    }
}
