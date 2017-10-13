/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author daniel
 */
public class Random implements Jugador {

    private int jugador;
//    private Tablero t;

    Random(int jugador) {
        this.jugador = jugador;
//        this.t = t;
    }

//    @Override
//    public void resetear(boolean entrenar) {}
//
//    @Override
//    public void finalizar() {}

    @Override
    public Object[] mover(int[][] tablero) {
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];

//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(tablero, jugador);

//        System.out.println("Posibles");
//        for (int[][] posibleTablero : posiblesTableros) {
//            Tablero.imprimirTablero(posibleTablero);
//        }
//        System.out.println("Fin posibles");

        if (estado == Tablero.JUEGO_CONTINUA) {
            tablero = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
        }

        return new Object[] {tablero, estado};
    }
}
