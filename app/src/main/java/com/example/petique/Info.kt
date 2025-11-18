package com.example.petique

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.annotation.DrawableRes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

// ⭐⭐⭐ DATA CLASS PARA DEFINIR LA INFORMACIÓN DE CADA SERVICIO ⭐⭐⭐
// Esta clase agrupa el título, descripción, duración y la ID de la imagen
// de un servicio. Se define aquí, fuera de cualquier Composable.
data class ServicioInfo(
    val titulo: String,
    val descripcion: String,
    val duracion: String,
    @DrawableRes val imagenId: Int // Anotación para indicar que es un ID de recurso drawable
)

// Definición de colores (puedes poner esto en tu archivo Theme.kt o mantenerlo aquí si es específico)
val rosaSuave = Color(0xFFFDE8ED) // Un rosa más claro para el fondo de las tarjetas
val textoRosaOscuro = Color(0xFF7C0E39) // Color de texto principal

@OptIn(ExperimentalMaterial3Api::class) // Necesario para componentes como Card si usas Material3
@Composable
fun PantallaInfo(navController: NavController, servicio: String) {

    // ⭐ MAPA QUE CONTIENE LA INFORMACIÓN DETALLADA DE TODOS LOS SERVICIOS ⭐
    // La clave es el nombre del servicio (String), y el valor es un objeto ServicioInfo.
    val detallesServicios = mapOf(
        "Paquete 1" to ServicioInfo(
            titulo = "Paquete 1",
            descripcion = "Corte ✂️ + Baño 🛁",
            duracion = "40 min",
            imagenId = R.drawable.pqt1 // Asegúrate de que este drawable exista en res/drawable
        ),
        "Paquete 2" to ServicioInfo(
            titulo = "Paquete 2",
            descripcion = "Corte de pelo ✂️ + Corte de uñas 🐾 + Baño 🛁",
            duracion = "40 min",
            imagenId = R.drawable.pqte2 // Asegúrate de que este drawable exista
        ),
        "Paquete 3" to ServicioInfo(
            titulo = "Paquete 3",
            descripcion = "Baño 🛁 + Peinado 💖 + Corte de pelo ✂️ + Corte de uñas 🐾 + Spa",
            duracion = "60 min",
            imagenId = R.drawable.pqte3 // Asegúrate de que este drawable exista
        ),
        "Vacunación" to ServicioInfo(
            titulo = "Vacunación",
            descripcion = "Vacunación 💉",
            duracion = "20 min",
            imagenId = R.drawable.vacpet // Asegúrate de que este drawable exista
        ),
        "Chequeo Rutina" to ServicioInfo(
            titulo = "Chequeo de Rutina",
            descripcion = "Chequeo de Rutina 🩺",
            duracion = "20 min",
            imagenId = R.drawable.usual0 // Asegúrate de que este drawable exista
        )
    )

    // ⭐⭐ AQUÍ OBTENEMOS SÓLO LA INFORMACIÓN DEL SERVICIO CLICADO ⭐⭐
    val currentServicioInfo = detallesServicios[servicio]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAD3E1)) // Fondo rosa claro para toda la pantalla
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Añadimos scroll por si el contenido es largo
    ) {
        // Botón Volver
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver", color = textoRosaOscuro, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Título "Descripción del Servicio" (o el que quieras)
        Text(
            text = "Descripción del Servicio", // Ahora este título es más genérico
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = textoRosaOscuro,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ⭐⭐⭐ MOSTRAR SÓLO LA TARJETA DEL SERVICIO CLICADO ⭐⭐⭐
        if (currentServicioInfo != null) {
            ServicioCard(servicioInfo = currentServicioInfo)
        } else {
            // Mensaje si el servicio no se encuentra (por si se pasa un servicio que no está en el mapa)
            Text(
                text = "Información no disponible para '$servicio'.",
                fontSize = 18.sp,
                color = textoRosaOscuro,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // ❌❌❌ ESTE BLOQUE SE COMENTA (NO SE MUESTRA) SI SOLO QUIERES EL SERVICIO CLICADO ❌❌❌
        /*
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            detallesServicios.forEach { (_, info) ->
                ServicioCard(servicioInfo = info)
            }
        }
        */
    }
}

// ⭐⭐⭐ COMPOSABLE PARA DIBUJAR UNA TARJETA DE SERVICIO INDIVIDUAL ⭐⭐⭐
// Este Composable recibe un objeto ServicioInfo y lo usa para rellenar su UI.
@OptIn(ExperimentalMaterial3Api::class) // Necesario para Card en Material3
@Composable
fun ServicioCard(servicioInfo: ServicioInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = rosaSuave) // Fondo rosa suave para la tarjeta
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen pequeña al lado del título
            Image(
                painter = painterResource(id = servicioInfo.imagenId),
                contentDescription = "Imagen de ${servicioInfo.titulo}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = servicioInfo.titulo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textoRosaOscuro
                )
                Text(
                    text = servicioInfo.descripcion,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Duración: ${servicioInfo.duracion}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}