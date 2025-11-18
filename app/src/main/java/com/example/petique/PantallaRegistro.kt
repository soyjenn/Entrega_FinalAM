package com.example.petique

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// Eliminamos imports duplicados de runtime
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
// --- CAMBIO IMPORTANTE AQUÍ ---
// Importamos la clase principal de Firestore en lugar de la extensión KTX antigua
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaRegistro(navController: NavController) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var documento by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena1 by remember { mutableStateOf("") }
    var contrasena2 by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }
    var mensajeColor by remember { mutableStateOf(Color.Red) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            modifier = Modifier.width(100.dp)
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Regístrate", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Campos de texto (igual que antes)
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = documento,
            onValueChange = { nuevo -> if (nuevo.length <= 10 && nuevo.all { it.isDigit() }) { documento = nuevo } },
            label = { Text("Documento (máx 10 dígitos)") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo (Gmail)") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = contrasena1, onValueChange = { contrasena1 = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = contrasena2, onValueChange = { contrasena2 = it }, label = { Text("Repite la Contraseña") }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(25.dp))

        if (mensaje.isNotEmpty()) {
            Text(mensaje, color = mensajeColor, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                val errorValidacion = validarCampos(nombre, apellido, documento, email, contrasena1, contrasena2)
                if (errorValidacion.isNotEmpty()) {
                    mensaje = errorValidacion
                    mensajeColor = Color.Red
                } else {
                    isLoading = true
                    mensaje = ""

                    registrarUsuarioEnFirestore(
                        nombre = nombre,
                        apellido = apellido,
                        documento = documento,
                        email = email,
                        contrasena = contrasena1,
                        onSuccess = {
                            isLoading = false
                            mensaje = "Registro exitoso ✔"
                            mensajeColor = Color(0xFF00C853)
                        },
                        onFailure = { errorMsg ->
                            isLoading = false
                            mensaje = errorMsg
                            mensajeColor = Color.Red
                        }
                    )
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFB6C1))
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Registrarse", color = Color.White)
            }
        }
    }
}

// ------------------------------
// VALIDACIONES
// ------------------------------
fun validarCampos(nombre: String, apellido: String, documento: String, email: String, contrasena1: String, contrasena2: String): String {
    return when {
        nombre.isBlank() -> "Ingrese un nombre."
        apellido.isBlank() -> "Ingrese un apellido."
        nombre.any { !it.isLetter() } -> "El nombre solo puede contener letras."
        apellido.any { !it.isLetter() } -> "El apellido solo puede contener letras."
        documento.isEmpty() -> "Debe ingresar un documento."
        documento.length > 10 -> "Máximo 10 dígitos en el documento."
        !email.endsWith("@gmail.com", ignoreCase = true) -> "El correo debe terminar en @gmail.com."
        contrasena1 != contrasena2 -> "Las contraseñas no coinciden."
        contrasena1.length < 8 -> "La contraseña debe tener mínimo 8 caracteres."
        !contrasena1.any { it.isUpperCase() } -> "La contraseña necesita una mayúscula."
        !contrasena1.any { it.isLowerCase() } -> "La contraseña necesita una minúscula."
        !contrasena1.any { it.isDigit() } -> "La contraseña necesita un número."
        !contrasena1.any { !it.isLetterOrDigit() } -> "La contraseña necesita un símbolo."
        else -> ""
    }
}

// -----------------------------------------------------
// FUNCIÓN PARA GUARDAR USUARIO EN FIRESTORE
// -----------------------------------------------------
fun registrarUsuarioEnFirestore(
    nombre: String,
    apellido: String,
    documento: String,
    email: String,
    contrasena: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    // --- CAMBIO IMPORTANTE AQUÍ ---
    // Obtenemos la instancia de la forma estándar
    val db = FirebaseFirestore.getInstance()

    db.collection("usuarios")
        .whereEqualTo("documento", documento)
        .get()
        .addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                onFailure("El número de documento ya está registrado.")
                return@addOnSuccessListener
            }

            db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { emailDocuments ->
                    if (!emailDocuments.isEmpty) {
                        onFailure("La dirección de correo ya está registrada.")
                        return@addOnSuccessListener
                    }

                    val usuario = hashMapOf(
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "documento" to documento,
                        "email" to email,
                        "password" to contrasena
                    )

                    db.collection("usuarios")
                        .add(usuario)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Usuario registrado con éxito: ${it.id}")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error al escribir el documento", e)
                            onFailure("Error al registrar el usuario. Inténtalo de nuevo.")
                        }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al verificar email", e)
                    onFailure("Error de conexión. Inténtalo de nuevo.")
                }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error al verificar documento", e)
            onFailure("Error de conexión. Inténtalo de nuevo.")
        }
}


