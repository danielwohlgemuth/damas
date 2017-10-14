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
public class Minimax implements Jugador {

    private int primerJugador;
    private int jugador;
    private int oponente;
    private int profundidad;

    Minimax(int jugador, int profundidad) {
        primerJugador = jugador;
        this.jugador = jugador;
        oponente = (jugador + 1) % 2;
        if (profundidad > 1) {
            this.profundidad = profundidad;
        } else {
            this.profundidad = 1;
        }
    }

    @Override
    public String toString() {
        return "Minimax("+profundidad+")["+primerJugador+"]";
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
            captura = tablerosConCaptura.get(tablero);
        }

        return new Object[]{tablero, estado, captura};
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