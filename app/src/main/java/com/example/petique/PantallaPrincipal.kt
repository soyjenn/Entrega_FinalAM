package com.example.petique

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PantallaPrincipal(navController: NavController, documento: String) {

    val rosa = Color(0xFFFAD3E1)
    val textoRosa = Color(0xFF7C0E39)

    val items = listOf("Paquete 1", "Paquete 2", "Paquete 3", "Vacunación", "Chequeo Rutina")
    val imagenes = listOf(
        R.drawable.pqt1,
        R.drawable.pqte2,
        R.drawable.pqte3,
        R.drawable.vacpet,
        R.drawable.usual0
    )

    var index by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        // 🔝 TOP BAR — PEGADO ARRIBA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(90.dp)
            ) {
                Text("Volver", fontSize = 14.sp)
            }

            Image(
                painter = painterResource(id = R.drawable.logopetique),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )

            // ⭐ PERFIL - AHORA PASA EL DOCUMENTO
            IconButton(onClick = {
                navController.navigate("perfil/$documento")
            }) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = textoRosa,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // ⭐ Línea rosa PRINCIPAL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(rosa)
        )

        // ---- NUESTROS SERVICIOS ----
        Text(
            text = "Nuestros Servicios",
            color = textoRosa,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 15.dp)
        )

        // ⭐⭐⭐ CARRUSEL ⭐⭐⭐
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Anterior",
                tint = textoRosa,
                modifier = Modifier
                    .size(55.dp)
                    .clickable {
                        index = if (index > 0) index - 1 else items.lastIndex
                    }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                val servicioActual = URLEncoder.encode(items[index], StandardCharsets.UTF_8.toString())

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = imagenes[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .clickable {
                                navController.navigate("info/$servicioActual")
                            }
                    )
                    Text(items[index], color = textoRosa, fontSize = 16.sp)
                }

                val nextIndex = (index + 1) % items.size
                val servicioSiguiente = URLEncoder.encode(items[nextIndex], StandardCharsets.UTF_8.toString())

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = imagenes[nextIndex]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .clickable {
                                navController.navigate("info/$servicioSiguiente")
                            }
                    )
                    Text(items[nextIndex], color = textoRosa, fontSize = 16.sp)
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Siguiente",
                tint = textoRosa,
                modifier = Modifier
                    .size(55.dp)
                    .clickable {
                        index = (index + 1) % items.size
                    }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // ⭐ Penúltima línea rosa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(rosa)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Agenda tu cita",
            color = textoRosa,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("agendar/$documento") },
            colors = ButtonDefaults.buttonColors(rosa),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
        ) {
            Text("Agendar", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(90.dp))

        // ---- CONOCE NUESTRAS UBICACIONES ----
        Text(
            text = "Conoce nuestras ubicaciones",
            color = textoRosa,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ir",
                tint = textoRosa,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(32.dp))

            Image(
                painter = painterResource(id = R.drawable.locationp),
                contentDescription = "Ir a Ubicación",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { navController.navigate("ubicacion") }
            )
        }
    }
}