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
public class Random implements Jugador {

    private int jugador;

    public Random(int jugador) {
        this.jugador = jugador;
    }

    @Override
    public int[][] mover(int[][] tablero) {

        ArrayList<int [][]> posiblesTableros = Tablero.generarMovimientos(tablero, this.jugador);

//        for (int i = 0; i < posiblesTableros.size(); i++) {
//            Tablero.printTablero(posiblesTableros.get(i));
//        }

        int[][] tableroNuevo = posiblesTableros.get((int)(Math.random() * posiblesTableros.size()));

        return tableroNuevo;
    }
}
