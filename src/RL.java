/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Daniel Min
 * @author Daniel Wohlgemuth
 */
public class RL implements Jugador {

    private HashMap<String, Double> tablaDeBusqueda;
    private double alpha = 0.5;
    boolean entrenar;
    private int primerJugador;
    private int jugador;
    private double qRate = 0.8;
//    private int N = 1000;
    private int[][] ultimoTablero;

    RL(int jugador) {
        primerJugador = jugador;
        this.jugador = jugador;
        tablaDeBusqueda = new HashMap<>();
        entrenar = false;
        ultimoTablero = Tablero.crearTablero();
    }

    @Override
    public String toString() {
        return "Reinforment Learning[" + primerJugador + "]";
    }

    @Override
    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    //        @Override
//    void resetear(boolean entrenar) {
//        this.entrenar = entrenar;
//    }

    //    @Override
    void finalizar(int estado) {
        if (estado == jugador) {
            updateProbability(ultimoTablero, 1);
        } else {
            updateProbability(ultimoTablero, 0);
        }
    }

    @Override
    public Object[] mover(int[][] tablero) {

        double prob;
        int[][] tableroCandidato = null;
        double maxProb = Integer.MIN_VALUE;
        double q;

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
            if (q <= qRate) {
                for (int[][] posibleTablero : posiblesTableros) {
//                    prob = calculateReward(posibleTablero);
                    prob = getProbability(posibleTablero);
                    if (prob > maxProb) {
                        maxProb = prob;
                        tableroCandidato = posibleTablero;
                    }
                }

                if (entrenar) {
                    updateProbability(ultimoTablero, maxProb);
                }
            } else {
                tableroCandidato = posiblesTableros.get((int) (Math.random() * posiblesTableros.size()));
            }

            captura = tablerosConCaptura.get(tableroCandidato);

            ultimoTablero = tablero;
            tablero = tableroCandidato;
        }

        resultado = Tablero.generarMovimientos(tablero, Tablero.jugadorOpuesto(this.jugador));
        estado = (int) resultado[1];

        return new Object[]{tablero, estado, captura};
    }

    private double calculateReward(int[][] tablero) {

        Object[] resultado = Tablero.generarMovimientos(tablero, Tablero.jugadorOpuesto(this.jugador));
        int estado = (int) resultado[1];

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

        String tableroSerializado = Tablero.serializarTablero(tablero);

        //si aun no contiene la tabla, insertar con valor inicial 0.5
        if (!tablaDeBusqueda.containsKey(tableroSerializado)) {
//            tablaDeBusqueda.put(tableroSerializado, 0.5);
            return 0.5;
        }

        if (this.jugador == Tablero.JUGADOR_NEGRO) {
            return tablaDeBusqueda.get(tableroSerializado);
        } else {
            return 1 - tablaDeBusqueda.get(tableroSerializado);
        }

    }

    private void updateProbability(int[][] tablero, double nextStateProb) {

        if (nextStateProb == 0.5) {
            return;
        }

        double prob = getProbability(tablero);

        if (this.jugador == Tablero.JUGADOR_BLANCO) {
            nextStateProb = 1 - nextStateProb;
        }

        prob = prob + alpha * (nextStateProb - prob);

        String tableroSerializado = Tablero.serializarTablero(tablero);
        tablaDeBusqueda.put(tableroSerializado, prob);
    }

//    public void updateAlpha(int currentGame) {
//
//        this.alpha = 0.5 - 0.49 * currentGame / this.N;
//    }

    public void imprimirTablaDeBusqueda() {

        for (String key : tablaDeBusqueda.keySet()) {
            System.out.println("Tablero: " + key + ", prob: " + tablaDeBusqueda.get(key));
            Tablero.imprimirTablero(Tablero.deserializar(key));
        }
    }

    void guardarTablaDeBusqueda() {
        Properties properties = new Properties();
        System.out.println("Guardando...");

        for (HashMap.Entry<String, Double> entry : tablaDeBusqueda.entrySet()) {
            properties.put(entry.getKey(), entry.getValue().toString());
        }
        try {
            properties.store(new FileOutputStream("DatosRL.txt"), null);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void recuperarTablaDeBusqueda() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("DatosRL.txt"));
        } catch (java.io.IOException e) {
            try {
                properties.store(new FileOutputStream("DatosRL.txt"), null);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(1);
            }
        }

        for (String key : properties.stringPropertyNames()) {
            tablaDeBusqueda.put(key, Double.parseDouble(properties.get(key).toString()));
        }
    }
}
