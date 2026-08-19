# RateMyPc 💻

Una aplicación de red social móvil para compartir y descubrir reviews de componentes de computador. Los usuarios pueden calificar, comentar y seguir opiniones de otros sobre hardware de PC.

## 📋 Descripción del Proyecto

**RateMyPc** es una aplicación Android nativa desarrollada en **Kotlin** con **Jetpack Compose** que permite a los usuarios:

- Explorar un feed de reviews de componentes de PC (procesadores, tarjetas gráficas, RAM, etc.)
- Calificar y comentar reviews de otros usuarios
- Guardar reviews favoritos
- Gestionar su perfil de usuario
- Descubrir tendencias en hardware

---

## 🎯 Características Principales

- **Feed Social** - Visualiza reviews de otros usuarios sobre componentes de PC
- **Sistema de Ratings** - Califica reviews con estrellas y comentarios
- **Favoritos** - Guarda tus reviews favoritos para acceso rápido
- **Perfil de Usuario** - Gestiona tu información personal y tus reviews
- **Navegación Fluida** - Bottom navigation bar para acceso rápido a principales secciones
- **Interfaz Moderna** - Diseño limpio y responsivo con Jetpack Compose

---

## Requisitos Técnicos

### Requisitos Previos

- **Android Studio** versión más reciente
- **Java 11** o superior
- **Android SDK 37** (Target SDK)
- **Android API 26** o superior (Mínimo)

### Dependencias Principales

- **Jetpack Compose** - Framework de UI declarativo
- **Jetpack Navigation Compose** - Navegación entre pantallas
- **Material 3** - Componentes UI de Material Design 3
- **Coil** - Librería para carga de imágenes
- **Lifecycle Runtime** - Gestión del ciclo de vida

---

## 📁 Estructura del Proyecto

```
RateMyPc/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/movil/ratemypc/
│   │       │   ├── MainActivity.kt           # Actividad principal
│   │       │   ├── data/                     # Capa de datos (modelos)
│   │       │   ├── ui/
│   │       │   │   ├── screens/              # Pantallas de la app
│   │       │   │   ├── navigation/           # Configuración de navegación
│   │       │   │   ├── theme/                # Temas y estilos
│   │       │   │   └── utils/                # Utilidades UI
│   │       │   └── viewmodel/                # ViewModels (lógica de negocio)
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml                    # Gestión centralizada de versiones
├── build.gradle.kts
└── README.md
```

---

## Cómo Ejecutar

### Desde Android Studio

1. **Clonar/Abrir el proyecto** en Android Studio
2. **Sincronizar Gradle** (Build → Sync Now)
3. **Seleccionar dispositivo o emulador** Android
4. **Ejecutar** (Shift + F10 o Play Button)

### Desde Terminal

```bash
# Compilar la aplicación
./gradlew build

# Instalar en dispositivo/emulador
./gradlew installDebug

# Ejecutar tests
./gradlew test
```

---

## Stack Tecnológico

| Tecnología        | Versión | Uso                   |
| ------------------ | -------- | --------------------- |
| Kotlin             | 2.x      | Lenguaje principal    |
| Jetpack Compose    | Latest   | Framework UI          |
| Android API        | 37       | Target SDK            |
| Material Design    | 3        | Componentes UI        |
| Navigation Compose | Latest   | Enrutamiento          |
| Coil               | Latest   | Gestión de imágenes |
| Gradle             | 8.x      | Build system          |

---

## Pantallas Principales

### 1. **Feed Home**

- Visualización de reviews de componentes
- Sistema de ratings
- Comentarios de usuarios

### 2. **Favoritos**

- Colección personal de reviews favoritos
- Acceso rápido a contenido guardado

### 3. **Perfil**

- Información personal del usuario
- Historial de reviews publicados
- Configuración de perfil

---

## Información Académica

- **Curso:** Computación Móvil 2026-3
- **Institución:** Pontificia Universidad Javeriana

---

## Notas de Desarrollo

- El proyecto utiliza composables de Jetpack Compose para toda la interfaz
- La navegación está centralizada en el componente `RateMyPcNavHost`
- Se implementa el patrón MVVM para separación de responsabilidades
- Los estilos y temas se encuentran en el módulo `ui/theme`

---

---

**Última actualización:** 2026-08-19
