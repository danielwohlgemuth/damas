/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daniel
 */
public class RL implements Jugador {
    @Override
    public int[][] mover(int[][] tablero) {
        return new int[1][1];
    }

    private HashMap<String, Double> lookupTable;
    private int[][] tablero;
    private double alpha;
    private boolean entrenar;
    private int gameResult;
    private int N;
    private int[][] lastTablero; //para guardar el estado anterior donde estuvo el agente
    private final int jugadorAgente = 1;
    private double qRate = 0.1;
    private final int size = 8;
    private List<int[]> reinasA, reinasB;
    private int fichasA, fichasB;
    private final int fwrdA = -1, fwrdB = 1;
    boolean noMovA, noMovB;

    public RL() {
        lookupTable = new HashMap<String, Double>();
        reset(true);
//        this.printTablero();

    }

    // 3 primeras filas blancas, 3 ultimas filas negras
    public void reset(boolean entrenar) {

        tablero = new int[8][8];
        lastTablero = new int[8][8];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i < 3) { // fichas blancas
                    if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0)) {
                        tablero[i][j] = Tablero.PEON_BLANCO;
                    } else {
                        tablero[i][j] = Tablero.VACIO;
                    }
                } else if (i > 4) { // fichas negras
                    if ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) {
                        tablero[i][j] = Tablero.PEON_NEGRO;
                    } else {
                        tablero[i][j] = Tablero.VACIO;
                    }
                } else {
                    tablero[i][j] = Tablero.VACIO;
                }
            }
        }
        this.entrenar = entrenar;
//        this.gameResult = 0;
//        this.fichasA = 12;
//        this.fichasB = 12;
//        this.noMovA = false;
//        this.noMovB = false;
//        this.reinasA = new ArrayList<>();
//        this.reinasB = new ArrayList<>();
    }

    private int calculateResult(int[][] tablero) {

        //1:gana jugador x
        //2:gana jugador o
        //3:empate
        //0:no hay ganador
        int jugador = 1;
        int contrario = 2;

//        Si no hay mas fichas blancas, gana jugador 1 (negro)
//        Si no hay mas fichas negras, gana jugador 2 (blanco)
//        Si es el turno de jugador 1 y no puede moverse, pierde
//        Si es el turno de jugador 2 y no puede moverse, pierde
//        Continua para el resto

//        if (this.fichasB == 0) return jugador;
//        else if (this.fichasA == 0) return contrario;
//        else if (this.noMovA == this.noMovB && this.noMovA) return 3; // empate
//        else return 0; // continua el juego
        return 1;
    }

    private double calculateReward(int[][] tablero, int jugador) {

        //(1 % 2) + 1 = 2
        //(2 % 2) + 1 = 1
        int contrario = (jugador % 2) + 1;

        int result = calculateResult(tablero);
        if (result == jugador) {

            return 1.0;
        } else if (result == contrario) {

            return 0.0;
        } else if (result == 3) {//empate

            return 0.0;
        } else {//no hay ganador

            return getProbability(tablero);
        }
    }

    private double getProbability(int[][] tablero) {

        String tableroSerializado = "";
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                tableroSerializado += tablero[i][j];
            }
        }
        //si aun no contiene la tabla, insertar con valor inicial 0.5
        if (!lookupTable.containsKey(tableroSerializado)) {
            lookupTable.put(tableroSerializado, 0.5);
        }

        return lookupTable.get(tableroSerializado);
    }


    private void updateProbability(int[][] tablero, double nextStateProb, int jugador) {

        double prob = calculateReward(tablero, jugador);
        //if(lookupTable.containsKey(tableroSerializado))
        //	prob = lookupTable.get(tableroSerializado);

        prob = prob + alpha * (nextStateProb - prob);

        String tableroSerializado = Tablero.serializarTablero(tablero);
        lookupTable.put(tableroSerializado, prob);
    }

    public void printTable() {

        for (String key : lookupTable.keySet()) {

            System.out.println("Tablero: " + key + ", prob: " + lookupTable.get(key));
            Tablero.printTablero(Tablero.deserializar(key));
        }
    }


    public void updateAlpha(int currentGame) {

        this.alpha = 0.5 - 0.49 * currentGame / this.N;
    }


    private static void swap(int[][] tablero, int i, int j, int k, int l) {
        int aux = tablero[i][j];
        tablero[i][j] = tablero[k][l];
        tablero[k][l] = aux;
    }

    private static void copiarTablero(int[][] tableroOrigen, int[][] tableroDestino) {

        for (int i = 0; i < tableroOrigen.length; i++) {
            for (int j = 0; j < tableroOrigen[0].length; j++) {

                tableroDestino[i][j] = tableroOrigen[i][j];
            }
        }
    }

    
}
