/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int cantEntrenamientos = 0;
        int cantJuegos = 2;
        int cantExperimentos = 10;
        double victoriasJugador0Acum = 0;
        double empatesJugador0Acum = 0;
        double perdidasJugador0Acum = 0;
        double jugadasProm = 0;

        Tablero t = new Tablero();
        int[][] tablero;// = Tablero.crearTablero();
//        Tablero.printTablero(tablero);

        Jugador[] jugadores = new Jugador[2];

//        jugadores[0] = new Random(Tablero.JUGADOR_NEGRO);
//        jugadores[0] = new RL(Tablero.JUGADOR_NEGRO);
//        jugadores[0] = new Minimax(Tablero.JUGADOR_NEGRO, 3);
        jugadores[0] = new AlphaBeta(Tablero.JUGADOR_NEGRO, 2);
//        jugadores[1] = new Random(Tablero.JUGADOR_BLANCO);
//        jugadores[1] = new RL(Tablero.JUGADOR_BLANCO);
//        jugadores[1] = new Minimax( Tablero.JUGADOR_BLANCO, 3);
        jugadores[1] = new AlphaBeta(Tablero.JUGADOR_BLANCO, 2);

//        for (int x = 0; x < cantEntrenamientos; x++) {
//            t.resetear();
//            tablero = Tablero.crearTablero();

//            int turno = Tablero.JUGADOR_NEGRO;
//            int i = 0;
//            t.imprimirTablero();

//            while (estado == Tablero.JUEGO_CONTINUA) {
//                jugadores[turno].mover();
//                turno = (turno + 1) % 2;
//                i++;
//                System.out.println(i);
//                t.imprimirTablero();
//            }
//
//            if (estado == Tablero.JUGADOR_BLANCO) {
//                jugadores[0].finalizar();
//            } else {
//                jugadores[1].finalizar();
//            }
//        }

        int victoriasJugador0 = 0;
        int empatesJugador0 = 0;
        int perdidasJugador0 = 0;
        int turnoJuego = Tablero.JUGADOR_NEGRO;

//        if (jugadores[0] instanceof RL) {
//            System.out.println("RL0");
//        }
//        if (jugadores[1] instanceof RL) {
//            System.out.println("RL1");
//        }

        for (int x = 0; x < cantJuegos; x++) {
//            t.resetear();
            tablero = Tablero.crearTablero();

//            jugadores[0].resetear(false);
//            jugadores[1].resetear(false);

            int turno = turnoJuego;
            int cantJugadas = 0;
            int estado = Tablero.JUEGO_CONTINUA;
            int pasosSinCaptura = 0;
            boolean captura;
//            Tablero.imprimirTablero(tablero);

//            System.out.println("Turno: "+turno);

            jugadores[0].setJugador(turno);
            jugadores[1].setJugador(Tablero.jugadorOpuesto(turno));


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

            if (estado == Tablero.JUGADOR_NEGRO) {
                victoriasJugador0++;
            } else if (estado == Tablero.JUGADOR_BLANCO) {
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
