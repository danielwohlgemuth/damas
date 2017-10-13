
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
class Tablero {
    static final int JUGADOR_NEGRO = 0;
    static final int JUGADOR_BLANCO = 1;
    static final int JUEGO_CONTINUA = 2;

    private static final int PEON_NEGRO = 0;
    private static final int PEON_BLANCO = 1;
    private static final int DAMA_NEGRA = 2;
    private static final int DAMA_BLANCA = 3;
    private static final int VACIO = 4;

    // Negro ganador: 0, Blanco ganador: 1, En curso: 2
//    int estado;
//    int[][] tablero;

//    Tablero() {
//        estado = Tablero.JUEGO_CONTINUA;
//        tablero = Tablero.crearTablero();
//    }
//
//    void resetear() {
//        tablero = crearTablero();
//        estado = Tablero.JUEGO_CONTINUA;
//    }

    // 3 primeras filas blancas, 3 ultimas filas negras
    static int[][] crearTablero() {

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

    static String serializarTablero(int[][] tablero) {

        StringBuilder tableroSerializado = new StringBuilder();
        for (int[] aTablero : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {

                tableroSerializado.append(aTablero[j]);
            }
        }

        return tableroSerializado.toString();
    }

    static int[][] deserializar(String tableroSerializado) {

        int valor;
        int[][] tablero = new int[8][8];
        for (int i = 0; i < tableroSerializado.length(); i++) {

            valor = Integer.parseInt(tableroSerializado.charAt(i) + "");
            tablero[i / 8][i % 8] = valor;
        }

        return tablero;
    }

//    void imprimirTablero() {
//        Tablero.imprimirTablero(tablero);
//    }

    static void imprimirTablero(int[][] tablero) {

        System.out.println();
        for (int[] aTablero : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {

                System.out.print("|");
                if (aTablero[j] == Tablero.PEON_NEGRO) {
                    System.out.print("x");
                } else if (aTablero[j] == Tablero.DAMA_NEGRA) {
                    System.out.print("X");
                } else if (aTablero[j] == Tablero.PEON_BLANCO) {
                    System.out.print("o");
                } else if (aTablero[j] == Tablero.DAMA_BLANCA) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println();
    }

    private static void swap(int[][] tablero, int i, int j, int k, int l) {
        int aux = tablero[i][j];
        tablero[i][j] = tablero[k][l];
        tablero[k][l] = aux;
    }

    private static void copiarTablero(int[][] tableroOrigen, int[][] tableroDestino) {

        for (int i = 0; i < tableroOrigen.length; i++) {
            System.arraycopy(tableroOrigen[i], 0, tableroDestino[i], 0, tableroOrigen[0].length);
        }
    }

    private static int[][] crearTableroModificado(int[][] tableroViejo, int x1, int y1, int x2, int y2) {
        int[][] tableroNuevo = new int[8][8];

        copiarTablero(tableroViejo, tableroNuevo);

        swap(tableroNuevo, x1, y1, x2, y2);

        // Es una captura
        if (Math.abs(x1-x2) == 2) {
            tableroNuevo[(x1+x2)/2][(y1+y2)/2] = Tablero.VACIO;
        }

        return tableroNuevo;
    }

    static Object[] generarMovimientos(int[][] tablero, int jugador) {
        // N: Norte, E: Este, S: Sur, O: Oeste
        // Las direcciones en sentido horario: NE, SE, SO, NO
//        int[][] tablero = t.tablero;
        ArrayList<int [][]> tablerosPosibles = new ArrayList<>();
        int oponente = (jugador+1) % 2;
        boolean capturaRealizada = false;
        int estado;
        int[][] tableroModificado;
        int x1, y1, x2, y2;
        int[] fichasRestantes = new int[] {0, 0};

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                // Es pieza del jugador?
                if (tablero[x][y] != Tablero.VACIO && tablero[x][y]%2 == jugador) {

                    for (int i = 1; i <= 4; i++) {
                        if (i == 1) {
                            // NE
                            x1 = x - (int) Math.pow(-1, jugador);
                            y1 = y + (int) Math.pow(-1, jugador);
                            x2 = x - 2 * (int)Math.pow(-1, jugador);
                            y2 = y + 2 * (int)Math.pow(-1, jugador);
                        } else if (i == 2) {
                            // NO
                            x1 = x - (int) Math.pow(-1, jugador);
                            y1 = y - (int) Math.pow(-1, jugador);
                            x2 = x - 2 * (int)Math.pow(-1, jugador);
                            y2 = y - 2 * (int)Math.pow(-1, jugador);
                            // Es dama
                        } else if (tablero[x][y]/2 == 1) {
                            if (i == 3) {
                                // SE
                                x1 = x + (int) Math.pow(-1, jugador);
                                y1 = y + (int) Math.pow(-1, jugador);
                                x2 = x + 2 * (int)Math.pow(-1, jugador);
                                y2 = y + 2 * (int)Math.pow(-1, jugador);
                            // i == 4
                            } else {
                                // SO
                                x1 = x + (int) Math.pow(-1, jugador);
                                y1 = y - (int) Math.pow(-1, jugador);
                                x2 = x + 2 * (int)Math.pow(-1, jugador);
                                y2 = y - 2 * (int)Math.pow(-1, jugador);
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
                                    tablerosPosibles = new ArrayList<>();
                                }

                                tableroModificado = crearTableroModificado(tablero, x, y, x2, y2);

                                // No se convirtio en dama
                                if (!convertirEnDama(tableroModificado, x2, y2, jugador)) {

//                                    System.out.println("cap");
//                                    System.out.println(" x:"+x+" y:"+y+" x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2);
                                    masCapturas(tablerosPosibles, tableroModificado, x2, y2, jugador);
                                } else {
                                    tablerosPosibles.add(tableroModificado);
                                }
                            }
                        }
                    }
                }

                if (tablero[x][y] != Tablero.VACIO) {
                    fichasRestantes[tablero[x][y]%2] += 1;
                }
            }
        }

        // Si el jugador quedo bloqueado o sin fichas, pierde
        if (tablerosPosibles.size() == 0 || fichasRestantes[jugador] == 0) {
            estado = oponente;
            // Si el oponente quedo sin fichas, gana
        } else if (fichasRestantes[oponente] == 0) {
            estado = jugador;
        } else {
            estado = Tablero.JUEGO_CONTINUA;
        }

        return new Object[] {tablerosPosibles, estado};
    }

    private static void masCapturas(ArrayList<int[][]> tablerosPosibles, int[][] tablero, int x, int y, int jugador) {
        boolean capturaRealizada = false;
        int oponente = (jugador+1) % 2;
        int[][] tableroModificado;
        int x1, y1, x2, y2;

        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                // NE
                x1 = x - (int) Math.pow(-1, jugador);
                y1 = y + (int) Math.pow(-1, jugador);
                x2 = x - 2 * (int)Math.pow(-1, jugador);
                y2 = y + 2 * (int)Math.pow(-1, jugador);
            } else if (i == 2) {
                // NO
                x1 = x - (int) Math.pow(-1, jugador);
                y1 = y - (int) Math.pow(-1, jugador);
                x2 = x - 2 * (int)Math.pow(-1, jugador);
                y2 = y - 2 * (int)Math.pow(-1, jugador);
            // Es dama
            } else if (tablero[x][y]/2 == 1) {
                if (i == 3) {
                    // SE
                    x1 = x + (int) Math.pow(-1, jugador);
                    y1 = y - (int) Math.pow(-1, jugador);
                    x2 = x + 2 * (int)Math.pow(-1, jugador);
                    y2 = y - 2 * (int)Math.pow(-1, jugador);
                // i == 4
                } else {
                    // SO
                    x1 = x + (int) Math.pow(-1, jugador);
                    y1 = y + (int) Math.pow(-1, jugador);
                    x2 = x + 2 * (int)Math.pow(-1, jugador);
                    y2 = y + 2 * (int)Math.pow(-1, jugador);
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

    private static boolean posicionValida(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private static boolean convertirEnDama(int[][] tablero, int x, int y, int jugador) {
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

    static int heuristica(int[][] tablero, int jugador) {

        int[] fichasRestantes = new int[] {0, 0};
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (tablero[x][y] != Tablero.VACIO) {
                    fichasRestantes[tablero[x][y]%2] += 1;
                }
            }
        }
        return fichasRestantes[jugador]-fichasRestantes[(jugador+1)%2];

    }

}