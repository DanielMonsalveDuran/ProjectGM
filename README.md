# Guía Extendida: Descarga y Ejecución de ProjectGM (LibGDX/Gradle)

Este documento describe el proceso para importar, configurar y ejecutar el proyecto ProjectGM, que utiliza la estructura estándar de LibGDX y el sistema de construcción Gradle.

## PLATAFORMAS Y MÓDULOS
- core: Módulo principal con la lógica de la aplicación.
- lwjgl3: Módulo de plataforma de escritorio (Desktop Launcher).

## PASO 1: REQUISITOS PREVIOS
1. **Java JDK:** Asegúrese de tener instalado el Java Development Kit (JDK), versión 8 o superior.
2. **Eclipse IDE:** Utilice Eclipse con el plugin Buildship (Gradle Integration) instalado y activo.
3. **Descarga:** Descargue el proyecto ProjectGM desde GitHub (clonando el repositorio o bajando el ZIP).

## PASO 2: IMPORTACIÓN EN ECLIPSE 
El método más sencillo es usar la herramienta de importación de Gradle de Eclipse:

1.  Abra Eclipse.
2.  Vaya a **File** (Archivo) > **Import...** (Importar...).
3.  Expanda la carpeta **Gradle** y seleccione **Existing Gradle Project** (Proyecto Gradle Existente).
4.  Haga clic en **Next**.
5.  En "Project root directory", haga clic en **Browse...** y seleccione la **carpeta principal** de ProjectGM (la que contiene 'build.gradle').
6.  Haga clic en **Finish**.
    * **NOTA:** Gradle resolverá las dependencias (LibGDX, LWJGL3, etc.). Este proceso puede tardar unos minutos la primera vez.

## PASO 3: EJECUCIÓN DEL PROYECTO

### Opción A: Ejecución desde Eclipse (Recomendada)
Para ejecutar la aplicación de escritorio:
1.  En el **Package Explorer**, navegue al proyecto **lwjgl3**.
2.  Busque la clase principal de inicio (generalmente **Lwjgl3Launcher.java** o similar).
3.  Haga clic derecho sobre la clase y seleccione **Run As** (Ejecutar como) > **Java Application** (Aplicación Java).

### Opción B: Ejecución Directa con el Wrapper de Gradle
Desde la terminal o CMD, navegue a la carpeta raíz del proyecto y use el comando de ejecución:
- **Comando:** ./gradlew lwjgl3:run

## COMANDOS DE GRADLE ÚTILES
Estos comandos se ejecutan desde la terminal en la carpeta raíz del proyecto.
- `cleanEclipse`: remueve los archivos de configuración de Eclipse.
- `eclipse`: genera los archivos de configuración de Eclipse.
- `build`: construye todas las fuentes y archivos del proyecto.
- `clean`: elimina las carpetas 'build' (clases compiladas y archivos).
- `lwjgl3:jar`: construye el archivo .jar ejecutable (encontrado en 'lwjgl3/build/libs').
- `lwjgl3:run`: inicia la aplicación de escritorio.
