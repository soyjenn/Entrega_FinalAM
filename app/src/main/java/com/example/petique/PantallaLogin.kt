package com.example.petique

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Importa componentes de Material3 (Button, Text, TextField, etc.)
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
// Importación estándar de Firestore
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaLogin(navController: NavController) {

    var documento by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Para mostrar carga mientras busca

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Botón Volver
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                modifier = Modifier.width(100.dp)
            ) {
                Text("Volver")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Inicia Sesión", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(30.dp))

        // DOCUMENTO
        OutlinedTextField(
            value = documento,
            onValueChange = { nuevo ->
                if (nuevo.all { it.isDigit() } && nuevo.length <= 10) {
                    documento = nuevo
                }
            },
            label = { Text("Documento (máx. 10 números)") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        // CONTRASEÑA
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (errorMensaje.isNotEmpty()) {
            Text(errorMensaje, color = Color.Red, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
        }

        // BOTÓN INGRESAR
        Button(
            onClick = {
                // Validaciones locales básicas
                if (documento.isEmpty()) {
                    errorMensaje = "Debes ingresar tu documento."
                    return@Button
                }
                if (contrasena.isEmpty()) {
                    errorMensaje = "Debes ingresar una contraseña."
                    return@Button
                }

                // Iniciamos proceso con Firebase
                isLoading = true
                errorMensaje = "" // Limpiar error anterior

                validarLoginFirestore(
                    documento = documento,
                    contrasena = contrasena,
                    onSuccess = {
                        // Login exitoso
                        isLoading = false
                        // Navegamos a la pantalla principal pasando el documento
                        navController.navigate("principal/$documento") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onFailure = { mensaje ->
                        // Login fallido
                        isLoading = false
                        errorMensaje = mensaje
                    }
                )
            },
            enabled = !isLoading, // Bloquear botón mientras carga
            colors = ButtonDefaults.buttonColors(Color(0xFFFFB6C1))
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Ingresar", color = Color.White)
            }
        }
    }
}


// ⭐ LOGIN USANDO FIREBASE FIRESTORE
// Esta función busca al usuario en la nube en lugar de localmente
fun validarLoginFirestore(
    documento: String,
    contrasena: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    // Buscamos en la colección "usuarios" donde el campo "documento" coincida
    db.collection("usuarios")
        .whereEqualTo("documento", documento)
        .get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                // No se encontró ningún usuario con ese documento
                onFailure("Documento no registrado.")
            } else {
                // Encontramos al usuario. Verificamos la contraseña.
                val usuario = documents.documents[0] // Tomamos el primer resultado
                val passGuardada = usuario.getString("password")

                if (passGuardada == contrasena) {
                    Log.d("Firestore", "Login exitoso para: $documento")
                    onSuccess()
                } else {
                    onFailure("Contraseña incorrecta.")
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Error en login", exception)
            onFailure("Error de conexión. Intenta de nuevo.")
        }
}

