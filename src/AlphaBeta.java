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
    int expandidos = 0;

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
        expandidos = 1;

        if (posiblesTableros.size() != 0 && estado == Tablero.JUEGO_CONTINUA) {
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            int majorValor = Integer.MIN_VALUE;
            int valorActual;

            for (int[][] posibleTablero : posiblesTableros) {
                valorActual = minimax(posibleTablero, this.profundidad, alpha, beta, Tablero.jugadorOpuesto(this.jugador));
                if (valorActual > majorValor) {
                    majorValor = valorActual;
                    tablero = posibleTablero;
                }
                expandidos++;
            }
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

        if (posiblesTableros.size() == 0 || estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
            return Tablero.heuristica(tablero, this.jugador);
        }

        int v = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (jugador == this.jugador) {
            for (int[][] posibleTablero : posiblesTableros) {
                v = Math.max(v, minimax(posibleTablero, profundidad - 1, alpha, beta, Tablero.jugadorOpuesto(this.jugador)));
                expandidos++;
                if (beta <= v) {
                    return v;
                }
                alpha = Math.max(alpha, v);
            }
        } else {
            for (int[][] posibleTablero : posiblesTableros) {
                v = Math.min(v, minimax(posibleTablero, profundidad - 1, alpha, beta, this.jugador));
                expandidos++;
                if (v <= alpha) {
                    return v;
                }
                beta = Math.min(beta, v);
            }
        }
        return v;
    }
}