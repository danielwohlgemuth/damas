
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
public class Tablero {
    public static final int JUGADOR_NEGRO = 0;
    public static final int JUGADOR_BLANCO = 1;
    public static final int PEON_NEGRO = 0;
    public static final int PEON_BLANCO = 1;
    public static final int DAMA_NEGRA = 2;
    public static final int DAMA_BLANCA = 3;
    public static final int VACIO = 4;
    
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
    
    public Tablero() {
        lookupTable = new HashMap<String, Double>();
        reset(true);
        this.printTablero();
        
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
    // 3 primeras filas blancas, 3 ultimas filas negras
    public static int[][] crearTablero() {

        int[][] tablero = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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

        return tablero;
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

    private String serializarTablero(int[][] tablero) {

        String tableroSerializado = "";
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                tableroSerializado += tablero[i][j];
            }
        }

        return tableroSerializado;
    }

    private int[][] deserializar(String tableroSerializado) {

        int valor;
        int[][] tablero = new int[8][8];
        for (int i = 0; i < tableroSerializado.length(); i++) {

            valor = Integer.parseInt(tableroSerializado.charAt(i) + "");
            tablero[i / 8][i % 8] = valor;
        }

        return tablero;
    }

    private void updateProbability(int[][] tablero, double nextStateProb, int jugador) {

        double prob = calculateReward(tablero, jugador);
        //if(lookupTable.containsKey(tableroSerializado))
        //	prob = lookupTable.get(tableroSerializado);		

        prob = prob + alpha * (nextStateProb - prob);

        String tableroSerializado = serializarTablero(tablero);
        lookupTable.put(tableroSerializado, prob);
    }
    
    public void printTable() {

        for (String key : lookupTable.keySet()) {

            System.out.println("Tablero: " + key + ", prob: " + lookupTable.get(key));
            printTablero(deserializar(key));
        }
    }

    public void printTablero() {

        printTablero(this.tablero);
    }

    public static void printTablero(int[][] tablero) {

        System.out.println();
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                System.out.print("|");
                if (tablero[i][j] == Tablero.PEON_NEGRO) {
                    System.out.print("x");
                } else if (tablero[i][j] == Tablero.DAMA_NEGRA) {
                    System.out.print("X");
                } else if (tablero[i][j] == Tablero.PEON_BLANCO) {
                    System.out.print("o");
                } else if (tablero[i][j] == Tablero.DAMA_BLANCA) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
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

    public static int[][] crearTableroModificado(int[][] tableroViejo, int x1, int y1, int x2, int y2) {
        int[][] tableroNuevo = new int[8][8];

        copiarTablero(tableroViejo, tableroNuevo);

        swap(tableroNuevo, x1, y1, x2, y2);

        // Es una captura
        if (Math.abs(x1-x2) == 2) {
            tableroNuevo[(x1+x2)/2][(y1+y2)/2] = Tablero.VACIO;
        }

        return tableroNuevo;
    }
    
    public void updateAlpha(int currentGame) {

        this.alpha = 0.5 - 0.49 * currentGame / this.N;
    }

    public static ArrayList<int [][]> generarMovimientos(int[][] tablero, int jugador) {
        // N: Norte, E: Este, S: Sur, O: Oeste
        // Las direcciones en sentido horario: NE, SE, SO, NO
        ArrayList<int [][]> tablerosPosibles = new ArrayList<int [][]>();
        int oponente = (jugador+1) % 2;
        boolean capturaRealizada = false;
        int[][] tableroModificado;
        int x1, y1, x2, y2;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                // Es pieza del jugador?
                if (tablero[x][y] != Tablero.VACIO && tablero[x][y]%2 == jugador) {

                    for (int i = 1; i <= 4; i++) {
                        if (i == 1) {
                            // NE
                            x1 = x - 1 * (int)Math.pow(-1, jugador);
                            y1 = y + 1 * (int)Math.pow(-1, jugador);
                            x2 = x - 2 * (int)Math.pow(-1, jugador);
                            y2 = y + 2 * (int)Math.pow(-1, jugador);
                        } else if (i == 2) {
                            // NO
                            x1 = x - 1 * (int)Math.pow(-1, jugador);
                            y1 = y - 1 * (int)Math.pow(-1, jugador);
                            x2 = x - 2 * (int)Math.pow(-1, jugador);
                            y2 = y - 2 * (int)Math.pow(-1, jugador);
                            // Es dama
                        } else if (tablero[x][y]/2 == 1) {
                            if (i == 3) {
                                // SE
                                x1 = x + 1 * (int)Math.pow(-1, jugador);
                                y1 = y + 1 * (int)Math.pow(-1, jugador);
                                x2 = x + 2 * (int)Math.pow(-1, jugador);
                                y2 = y + 2 * (int)Math.pow(-1, jugador);
                            } else if (i == 4) {
                                // SO
                                x1 = x + 1 * (int)Math.pow(-1, jugador);
                                y1 = y - 1 * (int)Math.pow(-1, jugador);
                                x2 = x + 2 * (int)Math.pow(-1, jugador);
                                y2 = y - 2 * (int)Math.pow(-1, jugador);
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }

                        if (posicionValida(x1, y1)){
                            // Posicion vacia
                            if (!capturaRealizada && tablero[x1][y1] == Tablero.VACIO){
//                                System.out.println("mov");
//                                System.out.println(" x:"+x+" y:"+y+" x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2);
                                tableroModificado = crearTableroModificado(tablero, x, y, x1, y1);
                                convertirEnDama(tableroModificado, x1, y1, jugador);
                                tablerosPosibles.add(tableroModificado);
                            // Capturable
                            } else if (tablero[x1][y1] != Tablero.VACIO && tablero[x1][y1]%2 == oponente
                                    && posicionValida(x2, y2) && tablero[x2][y2] == Tablero.VACIO){
                                if (!capturaRealizada) {
                                    capturaRealizada = true;
//                                    System.out.println("del");
                                    // Vaciar la lista
                                    tablerosPosibles = new ArrayList<int [][]>();
                                }

                                tableroModificado = crearTableroModificado(tablero, x, y, x2, y2);

                                // No se convirtio en dama
                                if (!convertirEnDama(tableroModificado, x2, y2, jugador)) {

//                                    System.out.println("cap");
//                                    System.out.println(" x:"+x+" y:"+y+" x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2);
                                    masCapturas(tablerosPosibles, tableroModificado, x2, y2, jugador);
                                } else {
                                    tablerosPosibles.add(tablero);
                                }
                            }
                        }
                    }
                }
            }
        }

        return tablerosPosibles;
    }

    public static void masCapturas(ArrayList tablerosPosibles, int[][] tablero, int x, int y, int jugador) {
        boolean capturaRealizada = false;
        int oponente = (jugador+1) % 2;
        int[][] tableroModificado;
        int x1, y1, x2, y2;

        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                // NE
                x1 = x - 1 * (int)Math.pow(-1, jugador);
                y1 = y + 1 * (int)Math.pow(-1, jugador);
                x2 = x - 2 * (int)Math.pow(-1, jugador);
                y2 = y + 2 * (int)Math.pow(-1, jugador);
            } else if (i == 2) {
                // NO
                x1 = x - 1 * (int)Math.pow(-1, jugador);
                y1 = y - 1 * (int)Math.pow(-1, jugador);
                x2 = x - 2 * (int)Math.pow(-1, jugador);
                y2 = y - 2 * (int)Math.pow(-1, jugador);
            // Es dama
            } else if (tablero[x][y]/2 == 1) {
                if (i == 3) {
                    // SE
                    x1 = x + 1 * (int)Math.pow(-1, jugador);
                    y1 = y - 1 * (int)Math.pow(-1, jugador);
                    x2 = x + 2 * (int)Math.pow(-1, jugador);
                    y2 = y - 2 * (int)Math.pow(-1, jugador);
                } else if (i == 4) {
                    // SO
                    x1 = x + 1 * (int)Math.pow(-1, jugador);
                    y1 = y + 1 * (int)Math.pow(-1, jugador);
                    x2 = x + 2 * (int)Math.pow(-1, jugador);
                    y2 = y + 2 * (int)Math.pow(-1, jugador);
                } else {
                    continue;
                }
            } else {
                continue;
            }

            // Capturable
            if (posicionValida(x1, y1) && tablero[x1][y1] != Tablero.VACIO && tablero[x1][y1]%2 == oponente
                    && posicionValida(x2, y2) && tablero[x2][y2] == Tablero.VACIO){
                capturaRealizada = true;

//                System.out.println("cap1");
//                System.out.println(" x:"+x+" y:"+y+" x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2);
                tableroModificado = crearTableroModificado(tablero, x, y, x2, y2);
                // No se convirtio en dama
                if (!convertirEnDama(tableroModificado, x2, y2, jugador)) {

//                    System.out.println("cap2");
                    masCapturas(tablerosPosibles, tableroModificado, x2, y2, jugador);
                } else {
                    tablerosPosibles.add(tablero);
                }
            }
        }

        if (!capturaRealizada) {
            tablerosPosibles.add(tablero);
        }
    }

    public static boolean posicionValida(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean convertirEnDama(int[][] tablero, int x, int y, int jugador) {
//        System.out.println("dama");
//        System.out.println("x:"+x+"y:"+y);
        // Jugador negro
        if (jugador == Tablero.JUGADOR_NEGRO && tablero[x][y] == Tablero.PEON_NEGRO && x == 0) {
            tablero[x][y] = Tablero.DAMA_NEGRA;
//            System.out.println("t");
            return true;
            // Jugador blanco
        } else if (jugador == Tablero.JUGADOR_BLANCO && tablero[x][y] == Tablero.PEON_BLANCO && x == 7) {
            tablero[x][y] = Tablero.DAMA_BLANCA;
//            System.out.println("t");
            return true;
        }

        return false;
    }

}