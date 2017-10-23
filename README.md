# Damas

Este proyecto forma parte de una tarea de la materia Inteligencia Artificial 
de la carrera Ingeniería en Informática de la Universidad Nacional de Asunción.

El objetivo fue aplicar los métodos Minimax, Minimax con poda alfa-beta y
Aprendizaje por refuerzo al juego de Damas, compararlos, realizar mediciones y 
formular una conclusión. 

El paper correspondiente está disponible en [Overleaf](https://www.overleaf.com/read/vvbhxcdnskph).

## Integrantes

- Daniel Min
- Daniel Wohlgemuth

## Instrucciones

El proyecto se desarrolló en Ubuntu 16.04 con Java 8.
El comando para instalarlos es:

    sudo apt install openjdk-8-jdk

### Para ejecutar la interfaz gráfica
Dentro del directorio *src*

    javac TableroGUI.java Jugador.java Random.java Minimax.java AlphaBeta.java RL.java Tablero.java
    java TableroGUI

![Damas estado inicial](images/damas_inicial.png)

En el campo deserializar se puede introducir un tablero serializado del archivo de aprendizaje.

### Para obtener los resultados estadísticos o para generar los archivos de aprendizaje
Dentro del directorio *src*

    javac Main.java Jugador.java Random.java Minimax.java AlphaBeta.java RL.java Tablero.java
    java Main
    
Las líneas 34 a 45 del archivo Main.java son las relevantes para modificar el comportamiento.

La variable `cantEntrenamientos` controla la cantidad de juegos de entrenamiento 
o aprendizaje que se realizarán en caso de que haya un jugador RL. El archivo de aprendizaje
se almacenará con el nombre `DatosRL.txt`.

La variable `cantJuegos` controla la cantidad de juegos que se realizarán, en los cuales
se calcularán los datos estadísticos.

El jugador negro puede ser elegido descomentando una de las opciones de línea 37 a 40.

El jugador blanco puede ser una de las opciones de línea 42 a 45.
    
## Acreditación 

Código de [Códigos de ejemplo - Inteligencia Artificial](https://github.com/jpaciello/ia)

Figuras para las fichas de [Wikimedia](https://commons.wikimedia.org/wiki/Category:Draughts_tiles).
