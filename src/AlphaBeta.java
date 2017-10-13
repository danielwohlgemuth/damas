/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author daniel
 */
public class AlphaBeta implements Jugador {

    private int jugador;
    private int oponente;
//    private Tablero t;

    AlphaBeta(int jugador) {
        this.jugador = jugador;
        oponente = (jugador + 1) % 2;
//        this.t = t;
    }
//
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
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];

//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, this.jugador);

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int majorValor = Integer.MIN_VALUE;
        int valorActual;
        int[][] tableroCandidato = null;
//        int estadoAnterior = t.estado;

        if (posiblesTableros.size() != 0 && estado == Tablero.JUEGO_CONTINUA) {
            for (int[][] posibleTablero : posiblesTableros) {
                System.out.println("Max 0 Posible");
                Tablero.imprimirTablero(posibleTablero);
                valorActual = minimax(posibleTablero, 2, alpha, beta, this.oponente);
                if (valorActual > majorValor) {
                    majorValor = valorActual;
                    tableroCandidato = posibleTablero;
                }
            }
            tablero = tableroCandidato;
//            estado = estadoAnterior;
        }

        return new Object[] {tablero, estado};
    }

    private int minimax(int[][] tablero, int profundidad, int alpha, int beta, int jugador) {
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];

        int mayorValor = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        int valorActual;

        if (posiblesTableros.size() == 0 || estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
//            if (jugador == this.jugador) {
            mayorValor = Tablero.heuristica(tablero, jugador);

            System.out.println("Heuristica " + mayorValor);
            System.out.println("Jugador " + jugador);
//            } else {
//                majorValor = -Tablero.heuristica(tablero);
//            }
        } else {
            for (int[][] posibleTablero : posiblesTableros) {
                if (jugador == this.jugador) {
                    System.out.println("Max Posible");
                    Tablero.imprimirTablero(tablero);
                    mayorValor = Math.max(mayorValor, minimax(posibleTablero, profundidad - 1, alpha, beta, oponente));
                    alpha = Math.max(alpha, mayorValor);
                    System.out.println("Max mayor " + mayorValor);
                    if (beta <= mayorValor) {
                        break;
                    }
                } else {
                    System.out.println("Min Posible");
                    Tablero.imprimirTablero(tablero);
                    mayorValor = Math.min(mayorValor, minimax(posibleTablero, profundidad - 1, alpha, beta, this.jugador));
                    beta = Math.min(beta, mayorValor);
                    System.out.println("Min mayor " + mayorValor);
                    if (mayorValor <= alpha) {
                        break;
                    }
                }
            }
        }
        return mayorValor;
    }
}