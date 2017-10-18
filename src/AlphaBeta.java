/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Daniel Min
 * @author Daniel Wohlgemuth
 */
public class AlphaBeta implements Jugador {

    private int primerJugador;
    private int jugador;
    private int profundidad;

    AlphaBeta(int jugador, int profundidad) {
        primerJugador = jugador;
        this.jugador = jugador;
        if (profundidad > 1) {
            this.profundidad = profundidad;
        } else {
            this.profundidad = 1;
        }
    }

    @Override
    public String toString() {
        return "AlphaBeta(" + profundidad + ")[" + primerJugador + "]";
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

//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, this.jugador);

//        System.out.println("Posibles");
//        for (int[][] posibleTablero : posiblesTableros) {
//            Tablero.imprimirTablero(posibleTablero);
//        }
//        System.out.println("Fin posibles");

//        int[][] tableroCandidato = null;
//        int estadoAnterior = t.estado;

        if (posiblesTableros.size() != 0 && estado == Tablero.JUEGO_CONTINUA) {
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            int majorValor = Integer.MIN_VALUE;
            int valorActual;

            for (int[][] posibleTablero : posiblesTableros) {
//                System.out.println("Max 0 Posible");
//                Tablero.imprimirTablero(posibleTablero);
                valorActual = minimax(posibleTablero, this.profundidad, alpha, beta, Tablero.jugadorOpuesto(this.jugador));
                if (valorActual > majorValor) {
                    majorValor = valorActual;
                    tablero = posibleTablero;
                }
            }
//            tablero = tableroCandidato;
//            estado = estadoAnterior;
            captura = tablerosConCaptura.get(tablero);
        }

        resultado = Tablero.generarMovimientos(tablero, Tablero.jugadorOpuesto(this.jugador));
        estado = (int) resultado[1];

        return new Object[]{tablero, estado, captura};
    }

    private int minimax(int[][] tablero, int profundidad, int alpha, int beta, int jugador) {
        Object[] resultado = Tablero.generarMovimientos(tablero, jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];

//        System.out.println("Actual");
//        System.out.println("Jugador " + jugador);
//        Tablero.imprimirTablero(tablero);
//        System.out.println("Posibles");
//        for (int[][] posibleTablero : posiblesTableros) {
//            Tablero.imprimirTablero(posibleTablero);
//        }
//        System.out.println("Fin posibles");

        int mayorValor = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        int valorActual;

        if (posiblesTableros.size() == 0 || estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
//            if (jugador == this.jugador) {
            mayorValor = Tablero.heuristica(tablero, this.jugador);

//            System.out.println("Heuristica " + mayorValor);
//            System.out.println("Jugador " + jugador);
        } else {
            if (jugador == this.jugador) {
                for (int[][] posibleTablero : posiblesTableros) {
//                    System.out.println("Max Posible");
//                    Tablero.imprimirTablero(tablero);
                    mayorValor = Math.max(mayorValor, minimax(posibleTablero, profundidad - 1, alpha, beta, Tablero.jugadorOpuesto(this.jugador)));
                    alpha = Math.max(alpha, mayorValor);
//                    System.out.println("Max mayor " + mayorValor);
                    if (beta <= mayorValor) {
                        break;
                    }
                }
            } else {
                for (int[][] posibleTablero : posiblesTableros) {
//                    System.out.println("Min Posible");
//                    Tablero.imprimirTablero(tablero);
                    mayorValor = Math.min(mayorValor, minimax(posibleTablero, profundidad - 1, alpha, beta, this.jugador));
                    beta = Math.min(beta, mayorValor);
//                    System.out.println("Min mayor " + mayorValor);
                    if (mayorValor <= alpha) {
                        break;
                    }
                }
            }
        }
        return mayorValor;
    }
}