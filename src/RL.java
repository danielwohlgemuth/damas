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
//    @Override
//    public void mover(int[][] tablero) {
//        return new int[1][1];
//    }

    private HashMap<String, Double> tablaDeBusqueda;
    private Tablero t;
    private double alpha;
    private boolean entrenar;
    private int primerJugador;
    private int jugador;
    private double qRate = 0.1;
    private int N = 1000;
    private int[][] ultimoTablero;

//    private int gameResult;
//    private int[][] lastTablero; //para guardar el estado anterior donde estuvo el agente
//    private final int jugadorAgente = 1;


    public RL(Tablero t, int jugador) {
        primerJugador = jugador;
        tablaDeBusqueda = new HashMap<>();
        entrenar = true;
        this.t = t;
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
//    public void finalizar() {
//        if(t.estado != jugador && entrenar) {//perdimos, actualizar tablero
//            updateProbability(ultimoTablero, calculateReward(t.tablero, jugador), jugador);
//        }
//    }

//        return this.t.estado;
        //0:gana negro
        //1:gana blanco
        //3:no hay ganador

//        Si no hay mas fichas blancas, gana jugador 1 (negro)
//        Si no hay mas fichas negras, gana jugador 2 (blanco)
//        Si es el turno de jugador 1 y no puede moverse, pierde
//        Si es el turno de jugador 2 y no puede moverse, pierde
//        Continua para el resto
//    }

    public int[][] mover(int[][] tablero) {
        return new int[1][1];
    }

//    @Override
//    public void mover() {
//
//        double prob;
//        int[][] tableroCandidato = null;
//        double maxProb = Integer.MIN_VALUE;
//        double q;
//
//        ArrayList<int[][]> posiblesTableros = Tablero.generarMovimientos(t, jugador);
//
//        if (t.estado == Tablero.JUEGO_CONTINUA) {
//
//            q = Math.random();
//            //entrenar
//            if (q <= qRate || !entrenar) {
//                for (int[][] posibleTablero : posiblesTableros) {
//                    prob = calculateReward(posibleTablero, jugador);
//                    if (prob > maxProb) {
//                        maxProb = prob;
//                        tableroCandidato = posibleTablero;
//                    }
//                }
//
//            } else {
//                tableroCandidato = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
//            }
//
//            if (entrenar) {
//                updateProbability(ultimoTablero, maxProb, jugador);
//            }
//
//            //aplicar jugada
//            ultimoTablero = t.tablero;
//            t.tablero = tableroCandidato;
//
//            // Gano el jugador
//        } else if (t.estado == jugador) {
//            updateProbability(ultimoTablero, 1.0, jugador);
//            // Gano el oponente
//        } else {
//            updateProbability(ultimoTablero, 0.0, jugador);
//        }
////        t.imprimirTablero();
//    }
//
//    private double calculateReward(int[][] tablero, int jugador) {
//
//        int oponente = (jugador + 1) % 2;
//
//        int estadoAnterior = t.estado;
//        Tablero.generarMovimientos(t, jugador);
////        int result = calculateResult(tablero);
//        int result = t.estado;
//        t.estado = estadoAnterior;
//
//        // Gano el jugador
//        if (result == jugador) {
//            return 1.0;
//            // Gano el oponente
//        } else if (result == oponente) {
//            return 0.0;
//            // No hay ganador
//        } else {
//            return getProbability(tablero);
//        }
//    }
//
//    private double getProbability(int[][] tablero) {
//
//        String tableroSerializado = Tablero.serializarTablero(tablero);
//
//        //si aun no contiene la tabla, insertar con valor inicial 0.5
//        if (!tablaDeBusqueda.containsKey(tableroSerializado)) {
//            tablaDeBusqueda.put(tableroSerializado, 0.5);
//        }
//
//        return tablaDeBusqueda.get(tableroSerializado);
//    }
//
//    private void updateProbability(int[][] tablero, double nextStateProb, int jugador) {
//
//        double prob = calculateReward(tablero, jugador);
//
//        prob = prob + alpha * (nextStateProb - prob);
//
//        String tableroSerializado = Tablero.serializarTablero(tablero);
//        tablaDeBusqueda.put(tableroSerializado, prob);
//    }
//
//    public void updateAlpha(int currentGame) {
//
//        this.alpha = 0.5 - 0.49 * currentGame / this.N;
//    }
//
//    public void imprimirTablaDeBusqueda() {
//
//        for (String key : tablaDeBusqueda.keySet()) {
//            System.out.println("Tablero: " + key + ", prob: " + tablaDeBusqueda.get(key));
//            Tablero.imprimirTablero(Tablero.deserializar(key));
//        }
//    }
}
