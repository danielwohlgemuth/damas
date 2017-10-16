/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int cantEntrenamientos = 10000;
        int cantJuegos = 10;
        int cantExperimentos = 10;
        double victoriasJugador0Acum = 0;
        double empatesJugador0Acum = 0;
        double perdidasJugador0Acum = 0;
        double jugadasProm = 0;
        int turnoJuego = Tablero.JUGADOR_NEGRO;
        int[][] tablero;// = Tablero.crearTablero();

        Jugador[] jugadores = new Jugador[2];

//        jugadores[Tablero.JUGADOR_NEGRO] = new Random(Tablero.JUGADOR_NEGRO);
//        jugadores[Tablero.JUGADOR_NEGRO] = new RL(Tablero.JUGADOR_NEGRO);
//        jugadores[Tablero.JUGADOR_NEGRO] = new Minimax(Tablero.JUGADOR_NEGRO, 3);
        jugadores[Tablero.JUGADOR_NEGRO] = new AlphaBeta(Tablero.JUGADOR_NEGRO, 2);
//        jugadores[Tablero.JUGADOR_BLANCO] = new Random(Tablero.JUGADOR_BLANCO);
        jugadores[Tablero.JUGADOR_BLANCO] = new RL(Tablero.JUGADOR_BLANCO);
//        jugadores[Tablero.JUGADOR_BLANCO] = new Minimax( Tablero.JUGADOR_BLANCO, 3);
//        jugadores[Tablero.JUGADOR_BLANCO] = new AlphaBeta(Tablero.JUGADOR_BLANCO, 2);

        //noinspection ConstantConditions
        if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL || jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {

            // Solo carga la tabla de busqueda de uno
            if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).entrenar = true;
                ((RL) jugadores[Tablero.JUGADOR_NEGRO]).recuperarTablaDeBusqueda();
            }
            if (jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {
                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).entrenar = true;
                ((RL) jugadores[Tablero.JUGADOR_BLANCO]).recuperarTablaDeBusqueda();
            }

            for (int x = 0; x < cantEntrenamientos; x++) {

                tablero = Tablero.crearTablero();

//            jugadores[Tablero.JUGADOR_NEGRO].resetear(false);
//            jugadores[Tablero.JUGADOR_BLANCO].resetear(false);

                int turno = turnoJuego;
//                int cantJugadas = 0;
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

//                if (estado == turnoJuego) {
//                    if (jugadores[Tablero.jugadorOpuesto(turnoJuego)] instanceof RL) {
//                        ((RL) jugadores[Tablero.jugadorOpuesto(turnoJuego)]).finalizar();
//                    }
                // El oponente gano o 50-moves rule
//                } else {
                if (jugadores[Tablero.JUGADOR_NEGRO] instanceof RL) {
                    ((RL) jugadores[Tablero.JUGADOR_NEGRO]).finalizar(estado);
                }
                if (jugadores[Tablero.JUGADOR_BLANCO] instanceof RL) {
                    ((RL) jugadores[Tablero.JUGADOR_BLANCO]).finalizar(estado);
                }
//                }

//                jugadasProm += (double) cantJugadas / cantJuegos;
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
        }

        int victoriasJugador0 = 0;
        int empatesJugador0 = 0;
        int perdidasJugador0 = 0;
        turnoJuego = Tablero.JUGADOR_NEGRO;

//        if (jugadores[0] instanceof RL) {
//            System.out.println("RL0");
//        }
//        if (jugadores[1] instanceof RL) {
//            System.out.println("RL1");
//        }

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
                cantJugadas++;
//                System.out.println(cantJugadas);
//                Tablero.imprimirTablero(tablero);
            }
            Tablero.imprimirTablero(tablero);

            if (estado == turnoJuego) {
                victoriasJugador0++;
            } else if (estado == Tablero.jugadorOpuesto(turnoJuego)) {
                perdidasJugador0++;
                // 50-moves rule
            } else {
                empatesJugador0++;
            }

            jugadasProm += (double) cantJugadas / cantJuegos;
            turnoJuego = Tablero.jugadorOpuesto(turnoJuego);
        }

        victoriasJugador0Acum += (double) victoriasJugador0 / cantJuegos;
        empatesJugador0Acum += (double) empatesJugador0 / cantJuegos;
        perdidasJugador0Acum += (double) perdidasJugador0 / cantJuegos;

        System.out.println("Ratio " + jugadores[0] + ": " + victoriasJugador0Acum);
        System.out.println("Ratio empates: " + empatesJugador0Acum);
        System.out.println("Ratio " + jugadores[1] + ": " + perdidasJugador0Acum);
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


//        Tablero.printTablero(tablero);
//        ArrayList<int [][]> posiblesTableros = Tablero.generarMovimientos(tablero, Tablero.JUGADOR_NEGRO);
//        for (int i = 0; i < posiblesTableros.size(); i++) {
//            Tablero.printTablero(posiblesTableros.get(i));
//        }
