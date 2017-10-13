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
        int cantJuegos = 1;
        int cantExperimentos = 10;
        double victoriasJugador0Acum = 0;
        double perdidasJugador0Acum = 0;
        double pasosProm = 0;

        Tablero t = new Tablero();
        int[][] tablero = Tablero.crearTablero();
//        Tablero.printTablero(tablero);

        Jugador[] jugadores = new Jugador[2];
//        jugadores[0] = new Random(Tablero.JUGADOR_NEGRO);
//        jugadores[0] = new RL(Tablero.JUGADOR_NEGRO);
//        jugadores[0] = new Minimax(Tablero.JUGADOR_NEGRO);
        jugadores[0] = new AlphaBeta(Tablero.JUGADOR_NEGRO);
        jugadores[1] = new Random(Tablero.JUGADOR_BLANCO);
//        jugadores[1] = new RL(Tablero.JUGADOR_BLANCO);
//        jugadores[1] = new Minimax( Tablero.JUGADOR_BLANCO);
//        jugadores[1] = new AlphaBeta(Tablero.JUGADOR_BLANCO);


        for (int x = 0; x < cantEntrenamientos; x++) {
//            t.resetear();
            tablero = Tablero.crearTablero();
//            jugadores[0].resetear(true);
//            jugadores[1].resetear(true);

            int turno = Tablero.JUGADOR_NEGRO;
//            int i = 0;
//            t.imprimirTablero();

//            while (estado == Tablero.JUEGO_CONTINUA) {
//                jugadores[turno].mover();
                turno = (turno + 1) % 2;
//                System.out.println(i);
//                t.imprimirTablero();
//                i++;
//            }
//
//            if (estado == Tablero.JUGADOR_BLANCO) {
//                jugadores[0].finalizar();
//            } else {
//                jugadores[1].finalizar();
//            }
        }

        int victoriasJugador0 = 0;
        int perdidasJugador0 = 0;
        int pasos = 0;

        for (int x = 0; x < cantJuegos; x++) {
//            t.resetear();
            tablero = Tablero.crearTablero();

//            jugadores[0].resetear(false);
//            jugadores[1].resetear(false);

            int turno = Tablero.JUGADOR_NEGRO;
            int i = 0;
            int estado = Tablero.JUEGO_CONTINUA;
//            Tablero.imprimirTablero(tablero);


            while (estado == Tablero.JUEGO_CONTINUA) {
//                jugadores[turno].mover(tablero);

                Object[] resultado = jugadores[turno].mover(tablero);
                tablero = (int[][]) resultado[0];
                estado = (int) resultado[1];

                turno = (turno + 1) % 2;
//                System.out.println(i);
//                Tablero.imprimirTablero(tablero);
                i++;
            }

            if (estado == Tablero.JUGADOR_NEGRO) {
                victoriasJugador0++;
            } else {
                perdidasJugador0++;
            }
            pasos += i;
        }

        victoriasJugador0Acum += (double)victoriasJugador0/cantJuegos;
        perdidasJugador0Acum += (double)perdidasJugador0/cantJuegos;
        pasosProm += (double)pasos/cantJuegos;

        System.out.println("Ratio W/T Jugador 0: " + victoriasJugador0Acum);
        System.out.println("Ratio W/T Jugador 1: " + perdidasJugador0Acum);
        System.out.println("Pasos promedio: " + pasosProm);
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
