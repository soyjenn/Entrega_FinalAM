package com.example.petique

import android.content.Context

object CitasManager {
    private const val PREFS_NAME = "petique_citas"

    fun guardarCita(
        context: Context,
        documento: String,
        fecha: String,
        hora: String,
        servicio: String,
        sede: String
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val citasActuales = obtenerCitas(context, documento).toMutableList()

        // Agregar nueva cita como string separado por "|"
        val nuevaCita = "$fecha|$hora|$servicio|$sede"
        citasActuales.add(nuevaCita)

        // Guardar todas las citas separadas por "||"
        val todasCitas = citasActuales.joinToString("||")
        prefs.edit().putString("citas_$documento", todasCitas).apply()
    }

    fun obtenerCitas(context: Context, documento: String): List<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val citasString = prefs.getString("citas_$documento", "") ?: ""

        return if (citasString.isEmpty()) {
            emptyList()
        } else {
            citasString.split("||")
        }
    }

    fun eliminarCita(context: Context, documento: String, cita: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val citasActuales = obtenerCitas(context, documento).toMutableList()
        citasActuales.remove(cita)

        val todasCitas = citasActuales.joinToString("||")
        prefs.edit().putString("citas_$documento", todasCitas).apply()
    }

    // Método auxiliar para parsear una cita
    fun parsearCita(citaString: String): Map<String, String> {
        val partes = citaString.split("|")
        return if (partes.size == 4) {
            mapOf(
                "fecha" to partes[0],
                "hora" to partes[1],
                "servicio" to partes[2],
                "sede" to partes[3]
            )
        } else {
            emptyMap()
        }
    }
}