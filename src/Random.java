/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author daniel
 */
public class Random implements Jugador {

    private int primerJugador;
    private int jugador;
//    private Tablero t;

    Random(int jugador) {
        primerJugador = jugador;
        this.jugador = jugador;
//        this.t = t;
    }

    @Override
    public String toString() {
        return "Random["+primerJugador+"]";
    }

    @Override
    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    @Override
    public Object[] mover(int[][] tablero) {
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];
        @SuppressWarnings("unchecked")
        HashMap<int[][], Boolean> tablerosConCaptura = (HashMap<int[][], Boolean>) resultado[2];
        boolean captura = false;

//        System.out.println("Posibles");
//        for (int[][] posibleTablero : posiblesTableros) {
//            Tablero.imprimirTablero(posibleTablero);
//        }
//        System.out.println("Fin posibles");

        if (estado == Tablero.JUEGO_CONTINUA) {
            tablero = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
            captura = tablerosConCaptura.get(tablero);
        }

        return new Object[] {tablero, estado, captura};
    }
}
