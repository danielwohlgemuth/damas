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
public class RL implements Jugador {

    private HashMap<String, Double> tablaDeBusqueda;
    private double alpha;
    private boolean entrenar;
    private int primerJugador;
    private int jugador;
    private double qRate = 0.1;
    private int N = 1000;
    private int[][] ultimoTablero;

    public RL(int jugador) {
        primerJugador = jugador;
        tablaDeBusqueda = new HashMap<>();
        entrenar = true;
//        this.t = t;
        this.jugador = jugador;
    }

    @Override
    public String toString() {
        return "Reinforment Learning["+primerJugador+"]";
    }

    @Override
    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    //    @Override
//    public void resetear(boolean entrenar) {
//        this.entrenar = entrenar;
//        ultimoTablero = t.tablero;
//    }
//
//    @Override
    public void finalizar(int[][] tablero, int estado) {
        if(estado != jugador && entrenar) {//perdimos, actualizar tablero
            updateProbability(ultimoTablero, 0, jugador);
        }
    }

//        Si no hay mas fichas blancas, gana jugador 1 (negro)
//        Si no hay mas fichas negras, gana jugador 2 (blanco)
//        Si es el turno de jugador 1 y no puede moverse, pierde
//        Si es el turno de jugador 2 y no puede moverse, pierde
//        Continua para el resto
//    }

//    public int[][] mover(int[][] tablero) {
//        return new int[1][1];
//    }

    @Override
    public Object[] mover(int[][] tablero) {

        double prob;
        int[][] tableroCandidato = null;
        double maxProb = Integer.MIN_VALUE;
        double q;

//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, jugador);
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];
        @SuppressWarnings("unchecked")
        HashMap<int[][], Boolean> tablerosConCaptura = (HashMap<int[][], Boolean>) resultado[2];
        boolean captura = false;

        if (estado == Tablero.JUEGO_CONTINUA) {

            q = Math.random();
            //entrenar
            if (q <= qRate || !entrenar) {
                for (int[][] posibleTablero : posiblesTableros) {
                    prob = calculateReward(posibleTablero, jugador);
                    if (prob > maxProb) {
                        maxProb = prob;
                        tableroCandidato = posibleTablero;
                    }
                }

            } else {
                tableroCandidato = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
            }

            if (entrenar) {
                updateProbability(ultimoTablero, maxProb, jugador);
            }

            //aplicar jugada
            ultimoTablero = tablero;
            tablero = tableroCandidato;

            // Gano el jugador
        } else if (estado == jugador) {
            updateProbability(ultimoTablero, 1.0, jugador);
            // Gano el oponente
        } else {
            updateProbability(ultimoTablero, 0.0, jugador);
        }
//        t.imprimirTablero();

        return new Object[]{tablero, estado, captura};
    }

    private double calculateReward(int[][] tablero, int jugador) {

//        int oponente = ;

//        int estadoAnterior = estado;
//        Tablero.generarMovimientos(tablero, jugador);
        Object[] resultado = Tablero.generarMovimientos(tablero, this.jugador);
        @SuppressWarnings("unchecked")
        ArrayList<int[][]> posiblesTableros = (ArrayList<int[][]>) resultado[0];
        int estado = (int) resultado[1];
        @SuppressWarnings("unchecked")
        HashMap<int[][], Boolean> tablerosConCaptura = (HashMap<int[][], Boolean>) resultado[2];
        boolean captura = false;

//        int result = calculateResult(tablero);
//        int result = estado;
//        t.estado = estadoAnterior;

        // Gano el jugador
        if (estado == jugador) {
            return 1.0;
            // Gano el oponente
        } else if (estado == Tablero.jugadorOpuesto(jugador)) {
            return 0.0;
            // No hay ganador
        } else {
            return getProbability(tablero);
        }
    }

    private double getProbability(int[][] tablero) {

        String tableroSerializado = Tablero.serializarTablero(tablero, this.jugador);

        //si aun no contiene la tabla, insertar con valor inicial 0.5
        if (!tablaDeBusqueda.containsKey(tableroSerializado)) {
            tablaDeBusqueda.put(tableroSerializado, 0.5);
        }

        return tablaDeBusqueda.get(tableroSerializado);
    }

    private void updateProbability(int[][] tablero, double nextStateProb, int jugador) {

        double prob = calculateReward(tablero, jugador);

        prob = prob + alpha * (nextStateProb - prob);

        String tableroSerializado = Tablero.serializarTablero(tablero, this.jugador);
        tablaDeBusqueda.put(tableroSerializado, prob);
    }

    public void updateAlpha(int currentGame) {

        this.alpha = 0.5 - 0.49 * currentGame / this.N;
    }

    public void imprimirTablaDeBusqueda() {

        for (String key : tablaDeBusqueda.keySet()) {
            System.out.println("Tablero: " + key + ", prob: " + tablaDeBusqueda.get(key));
            Tablero.imprimirTablero(Tablero.deserializar(key));
        }
    }
}
