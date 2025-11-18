package com.example.petique

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCita(navController: NavController, documento: String) {

    val rosaTenue = Color(0xFFFFE7F0)
    val textoRosa = Color(0xFF7C0E39)

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("Mini") }
    var manejo by remember { mutableStateOf("Agresivo") }

    var errorMensaje by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Para bloquear botón mientras guarda

    val listaTamanos = listOf("Mini", "Pequeño", "Mediano", "Grande")
    val listaManejo = listOf("Agresivo", "Pacífico", "Indeterminado")

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
        ) {
            Text("Volver", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Agendar Cita",
            fontSize = 26.sp,
            color = textoRosa,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // --- CAMPOS DE TEXTO (Igual que antes) ---
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de tu mascota") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = raza,
            onValueChange = { raza = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = edad,
            onValueChange = { new ->
                if (new.matches(Regex("^[0-9]*[.]?[0-9]*$"))) {
                    edad = new
                }
            },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- DROPDOWNS (Igual que antes) ---
        var expandTamano by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandTamano,
            onExpandedChange = { expandTamano = it }
        ) {
            TextField(
                value = tamano,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tamaño") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandTamano) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black
                )
            )
            ExposedDropdownMenu(
                expanded = expandTamano,
                onDismissRequest = { expandTamano = false }
            ) {
                listaTamanos.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { tamano = it; expandTamano = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        var expandManejo by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandManejo,
            onExpandedChange = { expandManejo = it }
        ) {
            TextField(
                value = manejo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de manejo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandManejo) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black
                )
            )
            ExposedDropdownMenu(
                expanded = expandManejo,
                onDismissRequest = { expandManejo = false }
            ) {
                listaManejo.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { manejo = it; expandManejo = false })
                }
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        if (errorMensaje.isNotEmpty()) {
            Text(
                text = errorMensaje,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // --- BOTÓN SIGUIENTE CON LOGICA FIREBASE ---
        Button(
            onClick = {
                // 1. Validaciones
                if (nombre.isBlank() || nombre.any { it.isDigit() }) {
                    errorMensaje = "El nombre no puede estar vacío ni contener números."
                    return@Button
                }
                if (apellido.isBlank() || apellido.any { it.isDigit() }) {
                    errorMensaje = "El apellido no puede estar vacío ni contener números."
                    return@Button
                }
                if (raza.isBlank() || raza.any { it.isDigit() }) {
                    errorMensaje = "La raza no puede estar vacía ni contener números."
                    return@Button
                }
                if (edad.isBlank()) {
                    errorMensaje = "La edad no puede estar vacía."
                    return@Button
                }

                errorMensaje = ""
                isLoading = true // Mostramos carga

                // 2. Llamada a Firebase
                // Nota: Pasamos 'documento' (dueño) para relacionar la mascota con el usuario
                guardarMascotaTemporalmente(
                    duenoDocumento = documento,
                    nombre = nombre,
                    apellido = apellido,
                    raza = raza,
                    edad = edad,
                    tamano = tamano,
                    manejo = manejo,
                    onSuccess = { idCita ->
                        isLoading = false
                        // 3. Navegación: Pasamos el ID de la cita recién creada a la siguiente pantalla
                        // para que Agendar2 pueda actualizar ese mismo documento con la fecha y hora.
                        navController.navigate("agendar2/$documento/$idCita")
                        // NOTA: Tendrás que actualizar tu NavHost para aceptar este nuevo parámetro idCita
                    },
                    onFailure = { e ->
                        isLoading = false
                        errorMensaje = "Error al guardar: $e"
                    }
                )
            },
            enabled = !isLoading, // Deshabilitar si está cargando
            colors = ButtonDefaults.buttonColors(textoRosa),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(220.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text(
                    text = "Siguiente",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// --- FUNCIÓN PARA GUARDAR DATOS DE LA MASCOTA ---
fun guardarMascotaTemporalmente(
    duenoDocumento: String,
    nombre: String,
    apellido: String,
    raza: String,
    edad: String,
    tamano: String,
    manejo: String,
    onSuccess: (String) -> Unit, // Devuelve el ID del documento creado
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    // Creamos el objeto de la cita/mascota
    // Dejamos campos vacíos para fecha/hora/servicio que se llenarán en Agendar2
    val citaData = hashMapOf(
        "dueno_documento" to duenoDocumento,
        "mascota_nombre" to nombre,
        "mascota_apellido" to apellido,
        "mascota_raza" to raza,
        "mascota_edad" to edad,
        "mascota_tamano" to tamano,
        "mascota_manejo" to manejo,
        "estado" to "pendiente_detalles" // Marca para saber que falta la parte 2
    )

    db.collection("citas")
        .add(citaData)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Cita iniciada con ID: ${documentReference.id}")
            // Pasamos el ID del documento creado para usarlo en la siguiente pantalla
            onSuccess(documentReference.id)
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error al iniciar cita", e)
            onFailure(e.message ?: "Error desconocido")
        }
}
