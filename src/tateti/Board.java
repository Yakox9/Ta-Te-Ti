/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author ElianaBcrra - Jesus Varela
 */
public class Board extends JPanel {

    private Piece board[][];
    private Image background;
    private Image imagePiece[];
    private JLabel turnTitle;
    private JLabel playerTitle;
    private JLabel playersTitle;
    private Piece turnPlayer;
    private int jugador;

    public int getJugador() {

        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }
    public final static int pieceSize = 175;

    public Board() {
        super();
        this.setLayout(null);
        turnTitle = new JLabel("Turno: ");
        // playerTitle=new JLabel("Jugador 1: ");
        playersTitle = new JLabel("Jugador:");
        turnPlayer = new Piece(630, 50, 1);
        turnTitle.setFont(new Font("Tahoma", Font.BOLD, 50));
        //  playerTitle.setFont(new Font("Tahoma", Font.BOLD,30));
        playersTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        turnTitle.setBounds(610, 0, 200, 50);
        //   playerTitle.setBounds(620,200,200,50);
        playersTitle.setBounds(620, 390, 200, 50);
        this.add(turnTitle);
        //   this.add(playerTitle);
        this.add(playersTitle);
        imagePiece = new Image[4];
        this.board = new Piece[3][3];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new Piece(((j * pieceSize) + 12) + 25 * j, ((i * pieceSize) + 5) + 20 * i, 0);

            }
        }
        generateImage();
        this.setVisible(true);

    }

    public Piece getTurnPlayer() {
        return turnPlayer;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    /**
     ** Position Piece in board. (108px,107px), (300px,107px),(416px,107px)
     * (108px,300px), (300px,300px),(416px,300px) (108px,493px),
     * (300px,493px),(416px,493px)
     *
     */
    public void Jugar() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, -15, 600, 600, null);
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                g2d.drawImage(this.imagePiece[this.board[i][j].getImg()], this.board[i][j].getX(), this.board[i][j].getY(), pieceSize, pieceSize, null);
            }
        }
        g2d.drawImage(this.imagePiece[turnPlayer.getImg()], turnPlayer.getX(), turnPlayer.getY(), pieceSize - 30, pieceSize - 30, null);
        //g2d.drawImage(this.imagePiece[1], 640,250, pieceSize-50, pieceSize-50, null);
        g2d.drawImage(this.imagePiece[getJugador()], 640, 430, pieceSize - 50, pieceSize - 50, null);
    }

    private void generateImage() {
        this.background = new ImageIcon(getClass().getResource("resource/tablero.jpg")).getImage();
        this.imagePiece[0] = new ImageIcon(getClass().getResource("resource/0.png")).getImage();
        this.imagePiece[1] = new ImageIcon(getClass().getResource("resource/1.png")).getImage();
        this.imagePiece[2] = new ImageIcon(getClass().getResource("resource/2.png")).getImage();
        this.imagePiece[3] = new ImageIcon(getClass().getResource("resource/3.png")).getImage();

    }
}
