📱 Petique 

🐾 Descripción General

Petique es una aplicación móvil desarrollada en Android (Kotlin + Jetpack Compose) diseñada para gestionar servicios veterinarios y estéticos para mascotas.
Permite a los dueños registrarse, agendar citas personalizadas y administrar su perfil de usuario de manera fácil y rápida.

✨ Funcionalidades Principales

🔐 Autenticación en la Nube

-Registro:
Los nuevos usuarios pueden crear una cuenta proporcionando nombre, documento, correo y contraseña.
Los datos se validan y se almacenan de forma segura en Cloud Firestore.

-Inicio de sesión (Login):
Se validan las credenciales directamente contra la base de datos en la nube, permitiendo el acceso solo a usuarios registrados.

📅 Gestión de Citas (Agendamiento)

-El usuario puede crear una cita para su mascota, ingresando: nombre, raza, edad, tamaño y tipo de manejo.

Selección de:

-Fecha (validando días hábiles)

-Hora

-Tipo de servicio (Vacunación, Baño, Paquetes especiales, etc.)

-Sede de atención

-Toda la información se guarda en tiempo real en Firestore, asociando la mascota y la cita con el dueño.

👤 Perfil y Gestión de Usuario

-Visualización de la información personal almacenada en Firestore.

-Acceso al historial de Mis Citas.

-Opción de Cerrar sesión de forma segura.

🛠️ Arquitectura Técnica

-UI con Jetpack Compose:
Interfaz moderna, reactiva y modular.

-Navigation Compose:
Manejo de navegación entre pantallas, pasando argumentos como número de documento o IDs de citas.

-Base de datos Firebase Firestore:
Backend NoSQL en tiempo real que reemplaza la necesidad de almacenar datos localmente.


🚀 Cómo Ejecutar la Aplicación Petique

La aplicación Petique puede ejecutarse de dos formas:

1. Desde Android Studio (modo desarrollo)

2. Instalando el archivo APK directamente en el celular (modo usuario)

A continuación se explican ambas:

📱 Opción 1: Ejecutar Desde Android Studio (Modo Desarrollo)
📌 Prerrequisitos

Android Studio instalado.

Conexión a internet activa.

▶️ Pasos
1️⃣ Abrir el Proyecto

Abre Android Studio → Open.

Selecciona la carpeta ParcialAMFinalPetique.

Espera la sincronización de Gradle.

2️⃣ Configurar el Dispositivo

Dispositivo físico:

Conecta el celular por USB.

Activa Depuración USB.

Verifica que Android Studio lo reconozca.

3️⃣ Compilar y Ejecutar

Haz clic en Run (botón verde).

La app se instalará automáticamente en el dispositivo elegido.

4️⃣ Flujo Inicial de Prueba

Registro: Crear cuenta con los datos.

Login: Iniciar sesión con documento y contraseña.

Agendar Cita: Ingresar datos de mascota → seleccionar fecha/hora → confirmar.

Perfil: Ver datos cargados desde Firestore.


📱 Opción 2: Ejecutar Desde el Archivo APK (Modo Usuario)
📌 Prerrequisitos

Un dispositivo Android.

El archivo Petique.apk.

Conexión a Internet.

▶️ Pasos
1️⃣ Transferir el APK al Celular

Envía el archivo por WhatsApp, correo, Drive, o cable USB.

2️⃣ Instalar la Aplicación

Busca el archivo Petique.apk (Descargas o chat donde lo recibiste).

Tócalo para instalar.

Si aparece el aviso de "Fuentes desconocidas", permitir instalación.

Presiona Instalar.

3️⃣ Abrir la App

Busca el ícono Petique en tu menú de aplicaciones.

Tócalo para abrir.

4️⃣ Primer Uso

Registrarse: Crear cuenta con los datos.

Iniciar Sesión: Acceder con documento y contraseña.

¡Listo! Ya puedes agendar citas veterinarias.
