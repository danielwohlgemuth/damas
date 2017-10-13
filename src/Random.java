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
    private Tablero t;

    Random(Tablero t, int jugador) {
        this.jugador = jugador;
        this.t = t;
    }

    @Override
    public void resetear(boolean entrenar) {}

    @Override
    public void finalizar() {}

    @Override
    public void mover() {

        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, jugador);

//        System.out.println("Posibles");
//        for (int[][] posibleTablero : posiblesTableros) {
//            Tablero.imprimirTablero(posibleTablero);
//        }
//        System.out.println("Fin posibles");

        if (t.estado == Tablero.JUEGO_CONTINUA) {
            t.tablero = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
        }

    }
}
