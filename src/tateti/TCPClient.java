//
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
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TCPClient implements Runnable {

    private Socket socketCliente;
    private DataInputStream inp;
    private DataOutputStream out;
    private int port = 4444;
    private String host;
    private String msg;
    private Frame ventana;

    public TCPClient(int port, String ip) {
        this.port = port;
        this.host = ip;

        try {
            System.out.println(host);
            this.ventana = new Frame(this);
            socketCliente = new Socket(host, port);
            inp = new DataInputStream(socketCliente.getInputStream());
            out = new DataOutputStream(socketCliente.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TCPClient() {

        try {
            System.out.println(host);
            this.ventana = new Frame(this);
            socketCliente = new Socket(host, port);
            inp = new DataInputStream(socketCliente.getInputStream());
            out = new DataOutputStream(socketCliente.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            msg = inp.readUTF();

            String cadena[] = msg.split(";");
            String ZG = cadena[0].split(" ")[1];
            System.out.println("ZG " + ZG);
            ventana.getBoard().setJugador(Integer.parseInt(ZG));
            ventana.getBoard().repaint();
            ventana.getPl().setTurn(Boolean.valueOf(cadena[1]));
            ventana.getBoard().repaint();

            while (true) {

                msg = inp.readUTF();
                System.out.println("" + msg);
                String[] mensajes = msg.split(";");
                int zg = Integer.parseInt(mensajes[0]);
                int f = Integer.parseInt(mensajes[1]);
                int c = Integer.parseInt(mensajes[2]);
                int ganar = Integer.parseInt(mensajes[3]);

                if ((ganar == Integer.parseInt(ZG) || ganar == 5) && ganar != 0) {
                    JOptionPane.showMessageDialog(null, "Gano!", "", JOptionPane.WARNING_MESSAGE);
                    break;
                } else if (ganar != Integer.parseInt(ZG) && ganar != 0) {
                    JOptionPane.showMessageDialog(null, "Perdio!", "", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                System.out.println("zg " + zg);

                ventana.getBoard().getBoard()[f][c].setImg(zg);
                ventana.getBoard().repaint();

                if ((zg == 2 || zg == 1) && ganar != 5) {
                    ventana.getPl().setTurn(!ventana.getPl().getTurn());
                    ventana.getBoard().getTurnPlayer().setImg((ventana.getBoard().getTurnPlayer().getImg() == 2) ? 1 : 2);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void movTurno(int idC, int fila, int columna) {
        try {

            if (ventana.getPl().getTurn()) {
                String datos = "";
                datos += idC + ";";
                datos += fila + ";";
                datos += columna + ";";
                out.writeUTF(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
