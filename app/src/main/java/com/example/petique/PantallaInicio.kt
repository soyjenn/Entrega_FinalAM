package com.example.petique

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaInicio(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo PETIQUE
        Image(
            painter = painterResource(id = R.drawable.logopetique),
            contentDescription = "Logo PETIQUE",
            modifier = Modifier.size(160.dp)
        )

        // Botones principales
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFB6C1)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Inicia Sesión", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = { navController.navigate("registro") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Regístrate", fontSize = 18.sp)
            }
        }

        // Texto inferior
        Text(
            text = "Conoce nuestras ubicaciones calle carrera avenida diagonal intersección rotonda",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(10.dp),
            color = Color.Gray
        )
    }
}
