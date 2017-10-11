/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] tablero = Tablero.crearTablero();

        Tablero.printTablero(tablero);

        Jugador[] jugadores = new Jugador[2];
        jugadores[0] = new Random(Tablero.JUGADOR_NEGRO);
        jugadores[1] = new Random(Tablero.JUGADOR_BLANCO);

        int turno = 0;
        for (int i = 0; i < 40; i++) {
            tablero = jugadores[turno].mover(tablero);
            Tablero.printTablero(tablero);
            turno = (turno + 1) % 2;
        }
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
