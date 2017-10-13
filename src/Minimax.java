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
//    private Tablero t;

    Minimax(int jugador) {
        this.jugador = jugador;
        oponente = (jugador + 1) % 2;
//        this.t = t;
    }

//    @Override
//    public void resetear(boolean entrenar) {
//    }
//
//    @Override
//    public void finalizar() {
//    }
    public int[][] mover(int[][] tablero) {
        return new int[1][1];
    }

//    @Override
//    public int[][] mover(int[][] tablero) {
//        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
//        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
//        int estado = (int) resultado[1];
//
//        int majorValor = Integer.MIN_VALUE;
//        int valorActual;
//        int[][] tableroCandidato = null;
//        int estadoAnterior = t.estado;
//
//        if (posiblesTableros.size() != 0 && t.estado == Tablero.JUEGO_CONTINUA) {
//            for (int[][] posibleTablero : posiblesTableros) {
//                valorActual = minimax(posibleTablero, 2, this.oponente);
//                if (valorActual > majorValor) {
//                    majorValor = valorActual;
//                    tableroCandidato = posibleTablero;
//                }
//            }
//            t.tablero = tableroCandidato;
//            t.estado = estadoAnterior;
//        }
//    }
//
//    private int minimax(int[][] tablero, int profundidad, int jugador) {
//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(tablero, this.jugador);
//
//        int mayorValor = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        int valorActual;
//
//        if (posiblesTableros.size() == 0 || t.estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
////            if (jugador == this.jugador) {
//                mayorValor = Tablero.heuristica(tablero, jugador);
////            } else {
////                majorValor = -Tablero.heuristica(tablero);
////            }
//        } else {
//            for (int[][] posibleTablero : posiblesTableros) {
//                if (jugador == this.jugador) {
//                    mayorValor = Math.max(mayorValor, minimax(posibleTablero, profundidad - 1, oponente));
////                    if (valorActual > mayorValor) {
////                        mayorValor = valorActual;
////                    }
//                } else {
//                    mayorValor = Math.min(mayorValor, minimax(posibleTablero, profundidad - 1, this.jugador));
////                    if (valorActual < mayorValor) {
////                        mayorValor = valorActual;
////                    }
//                }
//            }
//        }
//        return mayorValor;
//    }
}