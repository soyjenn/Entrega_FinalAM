---

# 🎥 Video Explicativo  
👉 **(https://drive.google.com/file/d/1jMdwBeaRVHLaSXAmQEvm5JMe7Y_6n2zH/view?usp=drivesdk)**

---

# 👥 Integrantes del Equipo

| Integrante | Rol en el Proyecto |
|------------|--------------------|
| **Jennifer Salazar-407092**  | Conexión y configuración de **Firebase**, Firestore y autenticación |
| **Natalia Sanjuan-407165** | Desarrollo  de la **aplicación Android** (pantallas, funcionalidades y lógica) |
| **Kimberly Caicedo-407072** | **Diseño UI**, presentación visual y **documentación** del proyecto |
| **María del mar Alvarez-407412** | **Testing**, validación de funcionalidades y elaboración del video |

---

# 📱 Petique

## 🐾 Descripción General
Petique es una aplicación móvil desarrollada en **Android (Kotlin + Jetpack Compose)** diseñada para gestionar servicios veterinarios y estéticos para mascotas.  
Permite a los dueños registrarse, agendar citas personalizadas y administrar su perfil de usuario de manera fácil y rápida.

---

## ✨ Funcionalidades Principales

### 🔐 Autenticación en la Nube
- **Registro:**  
  Los usuarios pueden crear una cuenta proporcionando nombre, documento, correo y contraseña.  
  Los datos se almacenan de forma segura en **Cloud Firestore**.

- **Inicio de sesión:**  
  Se validan las credenciales directamente contra la base de datos en la nube.

---

### 📅 Gestión de Citas
El usuario puede crear una cita ingresando:

- Nombre, raza, edad, tamaño y tipo de manejo  
- Selección de:
  - Fecha (validando días hábiles)  
  - Hora  
  - Tipo de servicio (Vacunación, Baño, Paquetes, etc.)  
  - Sede de atención  

Toda la información se guarda en tiempo real en **Firestore**, vinculada al dueño.

---

### 👤 Perfil y Gestión de Usuario
- Visualización de la información almacenada en Firestore  
- Acceso al historial **Mis Citas**  
- Cerrar sesión de forma segura  

---

## 🛠️ Arquitectura Técnica
- **UI con Jetpack Compose:** Interfaz moderna, modular y reactiva  
- **Navigation Compose:** Manejo de pantallas con paso de argumentos  
- **Firebase Firestore:** Base de datos NoSQL en tiempo real  

---

# 🚀 Cómo Ejecutar la Aplicación Petique

Puedes ejecutar la app de dos formas:

1. **Desde Android Studio (modo desarrollo)**  
2. **Instalando el archivo APK (modo usuario)**  

---

## 📱 Opción 1: Ejecutar Desde Android Studio

### 📌 Prerrequisitos
- Android Studio instalado  
- Conexión a Internet  

### ▶️ Pasos

#### 1️⃣ Abrir el Proyecto
- Abre Android Studio → **Open**
- Selecciona la carpeta `ParcialAMFinalPetique`
- Espera a que **Gradle** sincronice

#### 2️⃣ Configurar el Dispositivo
**Dispositivo físico:**
- Conecta el celular por USB  
- Activa **Depuración USB**  
- Verifica que Android Studio lo detecte  

#### 3️⃣ Compilar y Ejecutar
- Haz clic en el botón **Run** (verde)  
- La app se instalará automáticamente  

#### 4️⃣ Flujo Inicial
- **Registro:** crear cuenta  
- **Login:** iniciar sesión  
- **Agendar cita:** ingresar datos → fecha/hora → confirmar  
- **Perfil:** ver datos descargados de Firestore  

---

## 📱 Opción 2: Instalar Desde el Archivo APK

### 📌 Prerrequisitos
- Dispositivo Android  
- Archivo **Petique.apk**  
- Conexión a Internet  

### ▶️ Pasos

#### 1️⃣ Transferir el APK
- Por WhatsApp, Drive, correo o cable USB  

#### 2️⃣ Instalar la Aplicación
- Busca `Petique.apk`  
- Tócala para instalar  
- Acepta **"Fuentes desconocidas"** si aparece  
- Presiona **Instalar**  

#### 3️⃣ Abrir la App
- Busca el ícono **Petique** en el menú de aplicaciones  
- Tócalo para abrir  

#### 4️⃣ Primer Uso
- **Registrarse:** ingresar datos  
- **Iniciar sesión**  
- ¡Listo! Puedes agendar citas veterinarias  

