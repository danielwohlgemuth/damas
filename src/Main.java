/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Daniel Min
 * @author Daniel Wohlgemuth
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Estadísticas
        double tiempoEntrenamiento = 0;
        double[] tiempoMetodo = new double[2];
        double[] expandidos = new double[2];
        double victoriasJugador0Acum = 0;
        double empatesJugador0Acum = 0;
        double perdidasJugador0Acum = 0;
        double jugadasProm = 0;

        int turnoJuego = Tablero.JUGADOR_NEGRO;
        int[][] tablero;

        Jugador[] jugadores = new Jugador[2];

        int cantEntrenamientos = 100000;
        int cantJuegos = 100;

        jugadores[Tablero.JUGADOR_NEGRO] = new Random(Tablero.JUGADOR_NEGRO);
//        jugadores[Tablero.JUGADOR_NEGRO] = new RL(Tablero.JUGADOR_NEGRO);
//        jugadores[Tablero.JUGADOR_NEGRO] = new Minimax(Tablero.JUGADOR_NEGRO, 2);
//        jugadores[Tablero.JUGADOR_NEGRO] = new AlphaBeta(Tablero.JUGADOR_NEGRO, 2);

//        jugadores[Tablero.JUGADOR_BLANCO] = new Random(Tablero.JUGADOR_BLANCO);
//        jugadores[Tablero.JUGADOR_BLANCO] = new RL(Tablero.JUGADOR_BLANCO);
//        jugadores[Tablero.JUGADOR_BLANCO] = new Minimax( Tablero.JUGADOR_BLANCO, 2);
        jugadores[Tablero.JUGADOR_BLANCO] = new AlphaBeta(Tablero.JUGADOR_BLANCO, 2);

        //noinspection ConstantConditions
        if (cantEntrenamientos > 0 && (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL || jugadores[Tablero.JUGADOR_BLANCO] instanceof RL)) {

            long startTime = System.currentTimeMillis();

            if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).entrenar = true;
//                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).recuperarTablaDeBusqueda();
            }
            if (jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).entrenar = true;
//                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).recuperarTablaDeBusqueda();
            }

            for (int x = 0; x < cantEntrenamientos; x++) {

                tablero = Tablero.crearTablero();

                int turno = turnoJuego;
                int cantJugadas = 0;
                int estado = Tablero.JUEGO_CONTINUA;
                int pasosSinCaptura = 0;
                boolean captura;
//                Tablero.imprimirTablero(tablero);
//                System.out.println("Turno: "+turno);

                jugadores[Tablero.JUGADOR_NEGRO].setJugador(turno);
                jugadores[Tablero.JUGADOR_BLANCO].setJugador(Tablero.jugadorOpuesto(turno));

                while (estado == Tablero.JUEGO_CONTINUA && pasosSinCaptura < 100) {
                    Object[] resultado = jugadores[turno].mover(tablero);
                    tablero = (int[][]) resultado[0];
                    estado = (int) resultado[1];
                    captura = (boolean) resultado[2];

                    if (!captura) {
                        pasosSinCaptura++;
                    } else {
                        pasosSinCaptura = 0;
                    }

                    turno = Tablero.jugadorOpuesto(turno);
//                    cantJugadas++;
//                    System.out.println(cantJugadas);
//                    Tablero.imprimirTablero(tablero);
                }
//                Tablero.imprimirTablero(tablero);

                if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL) {
                    ((RL) jugadores[Tablero.JUGADOR_NEGRO]).finalizar(estado);
                }
                if (jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {
                    ((RL) jugadores[Tablero.JUGADOR_BLANCO]).finalizar(estado);
                }

                turnoJuego = Tablero.jugadorOpuesto(turnoJuego);
            }

            // Guarda y desactiva el aprendizaje RL
            if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).entrenar = false;
//                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).imprimirTablaDeBusqueda();
                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).guardarTablaDeBusqueda();
            }
            if (jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).entrenar = false;
//                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).imprimirTablaDeBusqueda();
                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).guardarTablaDeBusqueda();
            }

            long endTime = System.currentTimeMillis();
            tiempoEntrenamiento = endTime - startTime;
        }

        int victoriasJugador0 = 0;
        int empatesJugador0 = 0;
        int perdidasJugador0 = 0;
        turnoJuego = Tablero.JUGADOR_NEGRO;

        for (int x = 0; x < cantJuegos; x++) {
            tablero = Tablero.crearTablero();

            int turno = turnoJuego;
            int cantJugadas = 0;
            int estado = Tablero.JUEGO_CONTINUA;
            int pasosSinCaptura = 0;
            boolean captura;
//            Tablero.imprimirTablero(tablero);

//            System.out.println("Turno: "+turno);

            jugadores[Tablero.JUGADOR_NEGRO].setJugador(turno);
            jugadores[Tablero.JUGADOR_BLANCO].setJugador(Tablero.jugadorOpuesto(turno));

            double[] tiempoMetodoPorJuego = new double[2];
            int[] expandidosPorJuego = new int[2];

            while (estado == Tablero.JUEGO_CONTINUA && pasosSinCaptura < 100) {
                long startTime = System.currentTimeMillis();
                Object[] resultado = jugadores[turno].mover(tablero);
                long endTime = System.currentTimeMillis();
                tiempoMetodoPorJuego[turno == turnoJuego ? turnoJuego : Tablero.jugadorOpuesto(turnoJuego)] += endTime - startTime;
                if (jugadores[turno] instanceof Minimax) {
                    expandidosPorJuego[turno == turnoJuego ? turnoJuego : Tablero.jugadorOpuesto(turnoJuego)] += ((Minimax) jugadores[turno]).expandidos;
                } else if (jugadores[turno] instanceof AlphaBeta) {
                    expandidosPorJuego[turno == turnoJuego ? turnoJuego : Tablero.jugadorOpuesto(turnoJuego)] += ((AlphaBeta) jugadores[turno]).expandidos;
                }

                tablero = (int[][]) resultado[0];
                estado = (int) resultado[1];
                captura = (boolean) resultado[2];

                if (!captura) {
                    pasosSinCaptura++;
                } else {
                    pasosSinCaptura = 0;
                }

                turno = Tablero.jugadorOpuesto(turno);
                cantJugadas++;
//                System.out.println(cantJugadas);
//                Tablero.imprimirTablero(tablero);
            }
//            Tablero.imprimirTablero(tablero);

            if (estado == turnoJuego) {
                victoriasJugador0++;
            } else if (estado == Tablero.jugadorOpuesto(turnoJuego)) {
                perdidasJugador0++;
                // 50-moves rule
            } else {
                empatesJugador0++;
            }

            jugadasProm += (double) cantJugadas / cantJuegos;
            tiempoMetodo[0] += tiempoMetodoPorJuego[0] / cantJuegos;
            tiempoMetodo[1] += tiempoMetodoPorJuego[1] / cantJuegos;
            expandidos[0] += (double) expandidosPorJuego[0] / cantJuegos;
            expandidos[1] += (double) expandidosPorJuego[1] / cantJuegos;
            turnoJuego = Tablero.jugadorOpuesto(turnoJuego);
        }

        victoriasJugador0Acum += (double) victoriasJugador0 / cantJuegos;
        empatesJugador0Acum += (double) empatesJugador0 / cantJuegos;
        perdidasJugador0Acum += (double) perdidasJugador0 / cantJuegos;

        System.out.println("Tiempo entrenamiento (ms): " + tiempoEntrenamiento);
        System.out.println("Ratio " + jugadores[0] + ": " + victoriasJugador0Acum);
        System.out.println("Tiempo promedio (ms) " + tiempoMetodo[0]);
        System.out.println("Expandidos " + expandidos[0]);
        System.out.println("Ratio empates: " + empatesJugador0Acum);
        System.out.println("Ratio " + jugadores[1] + ": " + perdidasJugador0Acum);
        System.out.println("Tiempo promedio (ms) " + tiempoMetodo[1]);
        System.out.println("Expandidos " + expandidos[1]);
        System.out.println("Jugadas promedio: " + (int) jugadasProm);
    }
}


//        int [][] tablero = new int[][] {
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//                {4,4,4,4,4,4,4,4},
//        };


