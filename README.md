# HardScratch
Interfaz de programación visual para la iniciación en lenguajes de descripción de hardware. Diseño, implementación y simulación mediante drag&amp;drop, con posibilidad de generar código VHDL real y compilar para una FPGA.

HardScratch viene a facilitar y hacer más accesible el modelado de hardware. No se requieren conocimientos previos de VHDL, aunque se recomienda una base en lenguajes programación.

### Autoría y lecencia
Este proyecto ha sido realizado por Borja López, alias B0vE, para el concurso universitario de software libre 2019. https://concursosoftwarelibre.org

La interfaz HardScratch y sus componentes VMESS y VME2 son freeware, open source, bajo licencia GNU GPL. Por tanto, el código fuente puede ser descargado libremente y su distribución o modificación se regulan bajo Copyleft.

## Instrucciones de uso
HardScratch requiere una instalación de Java Rountime Enviroment 11 o superior.

Puedes descargar la una versión estable desde las releases de github, o compilar la última versión disponible desde el código fuente.

Es recomendable elegir la versión que contiene el JRE 11, puesto que asegura un funcionamiento correcto.

Una vez descargado y descomprimido, ejecuta el archivo HardScratch.exe. La primera vez buscará por si solo el JDK o JRE 11, si no lo encuentra te preguntará si deseas descargarlo o indicar dónde está instalado.

## Desarrollo, implementación y simulación
La inuitiva interfaz de usuario te guiará por desde el proceso de diseño hasta la elaboración final de código ejecutable.

Manual en español: https://github.com/BorjaLive/hardscratch/blob/master/Manual.md

Blog del proyecto: https://hardscratchb0ve.blogspot.com/

### Usuario de GNU Linux
No todas las caracteristicas han sido portadas la versión para linux. La simulación se encuentra desactivada por no poder integrar directamente sus componentes.

## Clonado
Compilar HardScratch requiere:
+ Java OpenJDK 11 o superior
+ Librería LWJGL 3.2.1 y sus componentes GLFW, JMALLOC, OPENGL y STB.
+ Autoit 3.1 (Para construir VMESS)

Se recomienda usar Netbeans 10.
