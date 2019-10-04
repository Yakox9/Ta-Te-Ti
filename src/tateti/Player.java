/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

/**
 *
 * @author ElianaBcrra - Jesus Varela
 */
public class Player {

    private int id;
    public static int idPlayer = 1;
    public static int maxPieces = 3;
    private Piece piece[];
    private int countPieces;
    private Boolean turn;

    public Player() {
        this.id = 1;
        this.piece = new Piece[3];
        this.countPieces = 0;
        this.turn = false;
    }

    public Player(int id) {
        this.id = id;
        this.piece = new Piece[3];
        this.countPieces = 0;
        this.turn = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Piece[] getPiece() {
        return piece;
    }

    public void updatePiece(int x, int y, int oldX, int oldY) {

        for (int i = 0; i < 3; i++) {
            if (piece[i].getX() == oldX && piece[i].getY() == oldY) {
                piece[i].setX(x);
                piece[i].setY(y);
            }
        }
    }

    public void setPiece(int x, int y) {
        this.piece[this.countPieces] = new Piece();
        this.piece[this.countPieces].setX(x);
        this.piece[this.countPieces].setY(y);
        this.piece[this.countPieces].setImg(this.id);
        updateCountPieces();
    }

    public int getCountPieces() {
        return countPieces;
    }

    public void updateCountPieces() {
        this.countPieces++;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

}
