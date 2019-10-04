/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author ElianaBcrra - Jesus Varela
 */
public class Frame extends JFrame {

    private Board board;
    private final static int sizeX = 800, sizeY = 600;
    private int oldX;
    int count;
    private int oldY;
    private Player pl;
    private Socket sc;
    private TCPClient own;
    private int cantVecinos;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Frame(TCPClient cliente) {
        super();
        this.cantVecinos = 0;
        this.setLayout(null);
        this.setTitle("Ta-Te-Ti");
        this.setSize(this.sizeX, this.sizeY);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pl = new Player();
        this.sc = null;
        this.initComponent();
        this.setVisible(true);
        this.own = cliente;

    }

    public Frame(Socket sc, int player) {
        super();
        this.cantVecinos = 0;
        this.setLayout(null);
        this.setTitle("Ta-Te-Ti");
        this.setSize(this.sizeX, this.sizeY);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pl = new Player(player);
        this.sc = sc;
        this.initComponent();
        this.setVisible(true);
    }

    private int[] comprarar(int x, int y) {
        int valor[] = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board.getBoard()[i][j].getX() < x && this.board.getBoard()[i][j].getX() + 175 > x
                        && this.board.getBoard()[i][j].getY() < y && this.board.getBoard()[i][j].getY() + 175 > y) {
                    valor[0] = j;
                    valor[1] = i;
                }
            }
        }
        return valor;
    }

    public Player getPl() {
        return pl;
    }

    public void setPl(Player pl) {
        this.pl = pl;
    }

    private int[][] vecinosDisponibles(int x, int y) {
        int vec[][] = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (((y == i - 1 && x == j - 1) || (y == i && x == j - 1) || (y == i + 1 && x == j - 1) || (y == i - 1 && x == j)
                        || (y == i && x == j) || (y == i + 1 && x == j) || (y == i - 1 && x == j + 1) || (y == i && x == j + 1) || (y == i + 1 && x == j + 1)) && this.board.getBoard()[i][j].getImg() == 0) {
                    vec[i][j] = 4;
                    cantVecinos++;
                } else {
                    vec[i][j] = 0;
                }
            }
        }
        return vec;

    }

    private void initComponent() {

        this.board = new Board();
        //this.pl.setTurn(true);
        this.board.setBounds(0, 0, this.sizeX, this.sizeY);
        this.getContentPane().add(this.board);
        this.oldX = -1;
        this.oldY = -1;
        count = 0;

        this.board.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (pl.getTurn() == true) {
                    int pos[] = comprarar(me.getX(), me.getY());

                    //Asignacion de posiciones
                    if ((oldX == -1 && oldY == -1) && (me.getX() > 12 && me.getX() < 600)) {
                        if (board.getBoard()[pos[1]][pos[0]].getImg() == 0 && pl.getCountPieces() < 3) {
                            board.getBoard()[pos[1]][pos[0]].setImg(pl.getId());
                            pl.setPiece(pos[0], pos[1]);

                            own.movTurno(getBoard().getTurnPlayer().getImg(), pos[1], pos[0]);

                        } else {

                            if (board.getBoard()[pos[1]][pos[0]].getImg() == getBoard().getTurnPlayer().getImg() && pl.getCountPieces() == 3) {
                                oldX = pos[0];
                                oldY = pos[1];
                                int vec[][] = vecinosDisponibles(pos[0], pos[1]);

                                System.out.println("contar cecinos " + cantVecinos);
                                if (cantVecinos != 0) {
                                    for (int i = 0; i < 3; i++) {
                                        for (int j = 0; j < 3; j++) {
                                            if (vec[i][j] == 4) {
                                                board.getBoard()[i][j].setImg(3);
                                                own.movTurno(3, i, j);
                                            }
                                        }
                                    }
                                    cantVecinos = 0;
                                } else {
                                    oldX = -1;
                                    oldY = -1;
                                }
                            }
                        }

                    } else {
                        //
                        if ((board.getBoard()[pos[1]][pos[0]].getImg() != 3)) {
                            OcultarVecinos();
                        } else {
                            if (board.getBoard()[pos[1]][pos[0]].getImg() == 3) {

                                board.getBoard()[pos[1]][pos[0]].setImg(getBoard().getTurnPlayer().getImg());

                                OcultarVecinos();
                                own.movTurno(0, oldY, oldX);
                                own.movTurno(getBoard().getTurnPlayer().getImg(), pos[1], pos[0]);
                                board.getBoard()[oldY][oldX].setImg(0);
                            }
                        }
                        oldX = -1;
                        oldY = -1;

                    }
                    board.repaint();
                    /**
                     * Position Piece in board. (108px,107px),
                     * (300px,107px),(416px,107px) (108px,300px),
                     * (300px,300px),(416px,300px) (108px,493px),
                     * (300px,493px),(416px,493px)
                     *
                     */

                } else {
                    JOptionPane.showMessageDialog(null, "Espere Su  turno!!", "", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void OcultarVecinos() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j].getImg() == 3) {

                    board.getBoard()[i][j].setImg(0);
                    own.movTurno(0, i, j);
                }
            }
        }
    }

}
