package com.example.petique

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Calendar
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCita2(navController: NavController, documento: String, idCita: String) { // <--- AÑADIDO idCita

    val rosaTenue = Color(0xFFFFE7F0)
    val textoRosa = Color(0xFF7C0E39)

    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var servicio by remember { mutableStateOf("") }
    var sede by remember { mutableStateOf("") }

    var errorFecha by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Estado de carga

    val context = LocalContext.current

    fun obtenerFechaDisponible(): LongArray {
        val calendar = Calendar.getInstance()
        val fechasValidas = mutableListOf<Long>()
        var diasAgregados = 0
        while (diasAgregados < 5) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {
                fechasValidas.add(calendar.timeInMillis)
                diasAgregados++
            }
        }
        return fechasValidas.toLongArray()
    }

    val fechasValidas = obtenerFechaDisponible()

    val horasDisponibles = listOf(
        "08:00 AM", "08:30 AM",
        "09:00 AM", "09:30 AM",
        "10:00 AM", "10:30 AM",
        "11:00 AM", "11:30 AM",
        "12:00 PM", "12:30 PM",
        "01:00 PM", "01:30 PM",
        "02:00 PM", "02:30 PM",
        "03:00 PM", "03:30 PM",
        "04:00 PM", "04:30 PM"
    )

    val servicios = listOf("Paquete 1", "Paquete 2", "Paquete 3", "Vacunación", "Chequeo de Rutina")
    val sedes = listOf("Sede Norte", "Sede Sur")

    var expandedHora by remember { mutableStateOf(false) }
    var expandedServicio by remember { mutableStateOf(false) }
    var expandedSede by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(rosaTenue)
            .padding(20.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(90.dp)
        ) { Text("Volver") }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Agendar Cita 2", fontSize = 26.sp, color = textoRosa, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(20.dp))

        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                val cal = Calendar.getInstance()
                cal.set(year, month, day)
                val dia = cal.get(Calendar.DAY_OF_WEEK)
                if (dia == Calendar.SATURDAY || dia == Calendar.SUNDAY) {
                    errorFecha = "No se pueden agendar citas sábados ni domingos"
                    fecha = ""
                } else {
                    fecha = "$day/${month + 1}/$year"
                    errorFecha = ""
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = fechasValidas.first()
        datePicker.datePicker.maxDate = fechasValidas.last()

        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            readOnly = true,
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = textoRosa,
                unfocusedBorderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            trailingIcon = {
                TextButton(onClick = { datePicker.show() }) { Text("Elegir", color = textoRosa) }
            }
        )

        if (errorFecha.isNotEmpty()) { Text(errorFecha, color = Color.Red) }

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(expanded = expandedHora, onExpandedChange = { expandedHora = !expandedHora }) {
            OutlinedTextField(
                value = hora,
                onValueChange = {},
                readOnly = true,
                label = { Text("Hora") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHora) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expandedHora, onDismissRequest = { expandedHora = false }) {
                horasDisponibles.forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = { hora = opcion; expandedHora = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(expanded = expandedServicio, onExpandedChange = { expandedServicio = !expandedServicio }) {
            OutlinedTextField(
                value = servicio,
                onValueChange = {},
                readOnly = true,
                label = { Text("Servicio Solicitado") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedServicio) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expandedServicio, onDismissRequest = { expandedServicio = false }) {
                servicios.forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = { servicio = opcion; expandedServicio = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(expanded = expandedSede, onExpandedChange = { expandedSede = !expandedSede }) {
            OutlinedTextField(
                value = sede,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sede") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSede) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expandedSede, onDismissRequest = { expandedSede = false }) {
                sedes.forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = { sede = opcion; expandedSede = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {
                if (fecha.isEmpty()) {
                    errorFecha = "Debes seleccionar un día hábil (lunes a viernes)"
                    return@Button
                }
                if (hora.isEmpty() || servicio.isEmpty() || sede.isEmpty()) return@Button

                isLoading = true // Activar carga

                // Llamamos a la función para actualizar Firebase
                actualizarCitaEnFirestore(
                    idCita = idCita,
                    fecha = fecha,
                    hora = hora,
                    servicio = servicio,
                    sede = sede,
                    onSuccess = {
                        isLoading = false
                        // Navegamos al resumen pasando todos los datos codificados
                        val ef = URLEncoder.encode(fecha, StandardCharsets.UTF_8.toString())
                        val eh = URLEncoder.encode(hora, StandardCharsets.UTF_8.toString())
                        val es = URLEncoder.encode(servicio, StandardCharsets.UTF_8.toString())
                        val esd = URLEncoder.encode(sede, StandardCharsets.UTF_8.toString())

                        navController.navigate("resumen/${URLEncoder.encode(documento, StandardCharsets.UTF_8.toString())}/$ef/$eh/$es/$esd")
                    },
                    onFailure = { error ->
                        isLoading = false
                        Log.e("Firestore", "Error actualizando cita: $error")
                        // Aquí podrías mostrar un mensaje de error con un Toast o un Text
                    }
                )
            },
            enabled = !isLoading, // Deshabilitar botón mientras carga
            colors = ButtonDefaults.buttonColors(textoRosa),
            modifier = Modifier.align(Alignment.CenterHorizontally).width(220.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Confirmar", color = Color.White)
            }
        }
    }
}

// --- FUNCIÓN PARA ACTUALIZAR LA CITA EN FIRESTORE ---
fun actualizarCitaEnFirestore(
    idCita: String,
    fecha: String,
    hora: String,
    servicio: String,
    sede: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    // Datos a agregar al documento existente
    val actualizaciones = hashMapOf<String, Any>(
        "fecha" to fecha,
        "hora" to hora,
        "servicio" to servicio,
        "sede" to sede,
        "estado" to "confirmada" // Cambiamos el estado porque ya está completa
    )

    // Usamos update en lugar de add porque el documento ya existe
    db.collection("citas").document(idCita)
        .update(actualizaciones)
        .addOnSuccessListener {
            Log.d("Firestore", "Cita actualizada correctamente")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error al actualizar cita", e)
            onFailure(e.message ?: "Error desconocido")
        }
}
