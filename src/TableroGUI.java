/*
    Codigo de https://stackoverflow.com/a/21142687/
 */

//import javax.imageio.ImageIO;

import javax.swing.*;
import javax.imageio.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Thread.sleep;

public class TableroGUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    //    private Image[][] chessPieceImages = new Image[2][6];
    private Image[] imagenesTablero = new Image[6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
//    private static final String COLS = "ABCDEFGH";
//    public static final int QUEEN = 0, KING = 1,
//            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
//    public static final int[] STARTING_ROW = {
//            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
//    };
//    public static final int BLACK = 0, WHITE = 1;

    private TableroGUI() {
        inicializarGui();
    }

    public final void inicializarGui() {
        // create the images for the chess pieces
        cargarImagenes();

        // set up the main GUI
        gui.setBorder(new EmptyBorder(0, 0, 0, 0));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new AbstractAction("Nuevo") {

            @Override
            public void actionPerformed(ActionEvent e) {
                desplegarTablero(Tablero.crearTablero());
            }
        });
        tools.add(new AbstractAction("Iniciar") {

            @Override
            public void actionPerformed(ActionEvent e) {
                Jugador[] jugadores = new Jugador[2];

                jugadores[0] = new Random(Tablero.JUGADOR_NEGRO);
                jugadores[1] = new AlphaBeta(Tablero.JUGADOR_BLANCO, 2);

                int[][] tablero = Tablero.crearTablero();
                int turno = Tablero.JUGADOR_NEGRO;
                int estado = Tablero.JUEGO_CONTINUA;
                int pasosSinCaptura = 0;
                boolean captura;

                while (estado == Tablero.JUEGO_CONTINUA && pasosSinCaptura < 100) {
                    Object[] resultado = jugadores[turno].mover(tablero);
                    tablero = (int[][]) resultado[0];
                    estado = (int) resultado[1];
                    captura = (boolean) resultado[2];

                    desplegarTablero(tablero);
                    if (!captura) {
                        pasosSinCaptura++;
                    } else {
                        pasosSinCaptura = 0;
                    }

                    turno = Tablero.jugadorOpuesto(turno);
//                    cantJugadas++;
//                    try {
//                        chessBoard.doLayout();
//                        sleep(100);
//
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                        System.exit(1);
//                    }
                }


                if (estado == Tablero.JUGADOR_NEGRO) {
//                    victoriasJugador0++;
                    message.setText("Ganador: " + jugadores[0]);
                } else if (estado == Tablero.jugadorOpuesto(Tablero.JUGADOR_NEGRO)) {
                    message.setText("Ganador: " + jugadores[1]);
//                    perdidasJugador0++;
                    // 50-moves rule
                } else {
                    message.setText("Empate");
//                    empatesJugador0++;
                }

                desplegarTablero(tablero);
            }
        });
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

//        gui.add(new JLabel("?"), BorderLayout.LINE_START);
//        gui.add(new JButton("Save"), BorderLayout.);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(0, 0, 0, 0),
                new LineBorder(Color.BLACK)
        ));
        Color ochre = new Color(204, 119, 34);
        Color black = new Color(53, 95, 16);
        Color white = new Color(252, 238, 152);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(new BufferedImage(44, 44, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(white);
                } else {
                    b.setBackground(black);
                }
                chessBoardSquares[i][j] = b;
            }
        }
        desplegarTablero(Tablero.crearTablero());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard.add(chessBoardSquares[i][j]);
            }
        }
    }

    private void cargarImagenes() {
        try {
            String[] image_names = {"peon_negro", "peon_blanco", "dama_negra", "dama_blanca", "fondo_negro", "fondo_blanco"};

            for (int i = 0; i < image_names.length; i++) {
                imagenesTablero[i] = ImageIO.read(new File("images/" + image_names[i] + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Initializes the icons of the initial chess board piece places
     */
    private void desplegarTablero(int[][] tablero) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // peon negro, peon_blanco, dama negra, dama blanca
                if (tablero[i][j] < 4) {
                    chessBoardSquares[i][j].setIcon(new ImageIcon(imagenesTablero[tablero[i][j]]));
                    // fondo blanco
                } else if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    chessBoardSquares[i][j].setIcon(new ImageIcon(imagenesTablero[5]));
                    // fondo negro
                } else {
                    chessBoardSquares[i][j].setIcon(new ImageIcon(imagenesTablero[4]));
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Runnable r = () -> {
            TableroGUI cg = new TableroGUI();

            JFrame f = new JFrame("Damas");
            f.add(cg.gui);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.pack();
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}
