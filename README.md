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
