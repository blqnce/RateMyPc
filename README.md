# RateMyPc

Aplicación Android de tipo red social para descubrir, calificar y compartir opiniones sobre componentes de computador.

## Descripción

RateMyPc está desarrollada con Kotlin y Jetpack Compose. Actualmente utiliza datos locales para demostrar los flujos principales de la aplicación:

- Explorar componentes en el feed.
- Filtrar y buscar componentes por nombre o categoría.
- Consultar favoritos y compatibilidad.
- Gestionar el perfil y sus configuraciones.
- Crear una reseña con calificación, comentario, tienda y foto.
- Consultar notificaciones de follows, comentarios y likes.

## Funcionalidades actuales

### Feed

El feed muestra componentes locales en una `LazyColumn`, con búsqueda y categorías dinámicas.

### Reviews

La pantalla de reviews está organizada siguiendo un patrón de pantalla principal más componentes reutilizables:

- Resumen del producto.
- Selector interactivo de cinco estrellas.
- Campo de texto para la reseña.
- Selector de tienda.
- Carga de una imagen de demostración.
- Botón de envío habilitado cuando la información requerida está completa.

Los archivos se encuentran en `app/src/main/java/movil/ratemypc/ui/screens/review/`.

Por ahora es una pantalla independiente de UI. Todavía no guarda reseñas en un repositorio ni está conectada a la navegación.

### Notificaciones

La pantalla de notificaciones incluye:

- Diez notificaciones locales de ejemplo.
- Filtros `All`, `Follows`, `Comments` y `Likes`.
- Contador de notificaciones no leídas.
- Acción para marcar todas como leídas.
- Acción para marcar una notificación individual como leída.
- Estado vacío cuando un filtro no tiene resultados.
- Diferenciación visual entre notificaciones leídas y no leídas.

La información está separada de la UI en `data/local/LocalNotificationsProvider.kt` y el estado se administra mediante `NotificationViewModel`.

Por decisión de alcance, esta pantalla aún no está registrada en `NavHost` ni en la barra inferior.

## Arquitectura y estructura

El proyecto sigue una organización por capas y el patrón MVVM:

```text
RateMyPc/
├── app/
│   ├── src/main/
│   │   ├── java/movil/ratemypc/
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   │   ├── local/                  # Proveedores de datos locales
│   │   │   │   ├── Componente.kt           # Modelo de componentes
│   │   │   │   └── NotificationItem.kt     # Modelo de notificaciones
│   │   │   ├── ui/
│   │   │   │   ├── navigation/             # NavHost, rutas y barra inferior
│   │   │   │   ├── screens/                 # Pantallas de la aplicación
│   │   │   │   │   ├── auth/
│   │   │   │   │   ├── compatibilidad/
│   │   │   │   │   ├── favoritos/
│   │   │   │   │   ├── feed/
│   │   │   │   │   ├── notifications/
│   │   │   │   │   ├── review/
│   │   │   │   │   ├── perfil/
│   │   │   │   │   └── settings/
│   │   │   │   └── theme/                   # Colores, tema y tipografía
│   │   │   └── viewmodel/                   # Estado y lógica de presentación
│   │   └── res/values/strings.xml           # Recursos de texto
│   └── build.gradle.kts
├── gradle/libs.versions.toml
├── build.gradle.kts
└── README.md
```

Las pantallas siguen, cuando aplica, la separación entre `Screen` y `ScreenContent`. Los componentes hijos reciben estado y callbacks; no controlan directamente la navegación ni mantienen el estado de la pantalla.

## Requisitos técnicos

- Android Studio actualizado.
- Java 11 o superior.
- Android SDK 37.
- Android API 26 como mínimo.

## Stack tecnológico

| Tecnología | Uso |
| --- | --- |
| Kotlin 2.2.10 | Lenguaje principal |
| Jetpack Compose | Interfaz declarativa |
| Material 3 | Componentes y tema visual |
| Navigation Compose | Navegación existente de la aplicación |
| Coil | Carga de imágenes remotas |
| Lifecycle ViewModel | Gestión del estado de pantalla |
| Gradle | Sistema de compilación |

## Cómo ejecutar

Desde Android Studio:

1. Abrir la carpeta `RateMyPc`.
2. Sincronizar el proyecto con Gradle.
3. Seleccionar un emulador o dispositivo con Android API 26 o superior.
4. Ejecutar la configuración `app`.

Desde la terminal, ubicada en la raíz `RateMyPc`:

```bash
# Compilar la aplicación
./gradlew build

# Instalar la versión debug
./gradlew installDebug

# Ejecutar las pruebas
./gradlew test
```

En Windows también se puede usar `gradlew.bat`.

## Estado del proyecto

- Implementación visual y local de las pantallas principales.
- Reviews y notificaciones disponibles como pantallas independientes.
- Sin backend ni persistencia permanente.
- Las notificaciones no usan push notifications reales.
- La navegación de notificaciones y reviews queda pendiente para una etapa posterior.
- Las pruebas automatizadas específicas de UI todavía son limitadas.

## Información académica

- Curso: Computación Móvil 2026-3
- Institución: Pontificia Universidad Javeriana

Última actualización: 2026-08-25
