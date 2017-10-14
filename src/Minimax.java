/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author daniel
 */
public class Minimax implements Jugador {

    private int jugador;
    private int oponente;
    private int profundidad;
//    private Tablero t;

    Minimax(int jugador, int profundidad) {
        this.jugador = jugador;
        oponente = (jugador + 1) % 2;
        if (profundidad > 1) {
            this.profundidad = profundidad;
        } else {
            this.profundidad = 1;
        }
//        this.t = t;
    }

//    @Override
//    public void resetear(boolean entrenar) {
//    }
//
//    @Override
//    public void finalizar() {
//    }


    @Override
    public Object[] mover(int[][] tablero) {
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];

        int mayorValor = Integer.MIN_VALUE;
        int valorActual;
        int[][] tableroCandidato = null;
//        int estadoAnterior = estado;

        if (posiblesTableros.size() != 0 && estado == Tablero.JUEGO_CONTINUA) {
            for (int[][] posibleTablero : posiblesTableros) {
                valorActual = minimax(posibleTablero, this.profundidad, this.oponente);
                if (valorActual > mayorValor) {
                    mayorValor = valorActual;
                    tableroCandidato = posibleTablero;
                }
            }
            tablero = tableroCandidato;
//            estado = estadoAnterior;
        }

        return new Object[]{tablero, estado};
    }

    private int minimax(int[][] tablero, int profundidad, int jugador) {
        Object[] resultado = Tablero.generarMovimientos(tablero, jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];
//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(tablero, this.jugador);

        int mayorValor = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        int valorActual;

        if (posiblesTableros.size() == 0 || estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
//            if (jugador == this.jugador) {
            mayorValor = Tablero.heuristica(tablero, this.jugador);
//            } else {
//                majorValor = -Tablero.heuristica(tablero);
//            }
        } else {
            if (jugador == this.jugador) {
                for (int[][] posibleTablero : posiblesTableros) {
                    mayorValor = Math.max(mayorValor, minimax(posibleTablero, profundidad - 1, oponente));
//                    if (valorActual > mayorValor) {
//                        mayorValor = valorActual;
//                    }
                }
            } else {
                for (int[][] posibleTablero : posiblesTableros) {
                    mayorValor = Math.min(mayorValor, minimax(posibleTablero, profundidad - 1, this.jugador));
//                    if (valorActual < mayorValor) {
//                        mayorValor = valorActual;
//                    }
                }
            }
        }

        return mayorValor;
    }
}