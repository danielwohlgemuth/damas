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
    private Tablero t;

    Minimax(Tablero t, int jugador) {
        this.jugador = jugador;
        oponente = (jugador + 1) % 2;
        this.t = t;
    }

    @Override
    public void resetear(boolean entrenar) {
    }

    @Override
    public void finalizar() {
    }

    @Override
    public void mover() {
        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, this.jugador);

        int majorValor = Integer.MIN_VALUE;
        int valorActual;
        int[][] tableroCandidato = null;
        int estadoAnterior = t.estado;

        if (posiblesTableros.size() != 0 && t.estado == Tablero.JUEGO_CONTINUA) {
            for (int[][] posibleTablero : posiblesTableros) {
                valorActual = minimax(posibleTablero, 2, this.oponente);
                if (valorActual > majorValor) {
                    majorValor = valorActual;
                    tableroCandidato = posibleTablero;
                }
            }
            t.tablero = tableroCandidato;
            t.estado = estadoAnterior;
        }
    }

    private int minimax(int[][] tablero, int profundidad, int jugador) {
        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, this.jugador);

        int majorValor = (jugador == this.jugador) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int valorActual;

        if (posiblesTableros.size() == 0 || t.estado != Tablero.JUEGO_CONTINUA || profundidad == 0) {
//            if (jugador == this.jugador) {
                majorValor = Tablero.heuristica(tablero, jugador);
//            } else {
//                majorValor = -Tablero.heuristica(tablero);
//            }
        } else {
            for (int[][] posibleTablero : posiblesTableros) {
                if (jugador == this.jugador) {
                    valorActual = minimax(posibleTablero, profundidad - 1, oponente);
                    if (valorActual > majorValor) {
                        majorValor = valorActual;
                    }
                } else {
                    valorActual = minimax(posibleTablero, profundidad - 1, this.jugador);
                    if (valorActual < majorValor) {
                        majorValor = valorActual;
                    }
                }
            }
        }
        return majorValor;
    }
}