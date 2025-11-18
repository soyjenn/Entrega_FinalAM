package com.example.petique
import android.app.Application
import com.google.firebase.FirebaseApp

class PetiqueAppFirebase : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializa Firebase cuando la app arranca
        FirebaseApp.initializeApp(this)
    }
}
