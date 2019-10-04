/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author ElianaBcrra- Jesus Varela
 */
public class TCPServer implements Runnable {

    public static final int PORT = 4444;
    private LinkedList<Socket> jugadores = new LinkedList<Socket>();
    private Boolean turno;
    private int T[][] = new int[3][3];
    private int turnos = 1;

    private Thread hiloS;
    private int contador = 0;

    private LinkedList<Socket> usuarios = new LinkedList<Socket>();
    private Socket socket;
    private int ZG;
    private int TZG[][];
    private DataOutputStream out;
    private DataInputStream inp;
    private Thread udpS;

    public TCPServer(Thread s) {
        this.udpS = s;
    }

    public TCPServer(Socket soc, LinkedList jugs, int xo, int[][] t) {
        socket = soc;
        usuarios = jugs;
        ZG = xo;
        TZG = t;
    }

    public void cargarMatriz() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                T[i][j] = 0;
            }
        }
    }

    public void escuchar(int Puerto) {

        try {
            cargarMatriz();
            ServerSocket socketServidor = new ServerSocket(Puerto);
            System.out.println("Esperando jugadores...");

            while (turnos <= 2) {
                Socket cliente = socketServidor.accept();
                jugadores.add(cliente);

                System.out.println("Cliente conectado");

                int zg = turnos % 2 == 0 ? 1 : 2;

                turnos++;
                hiloS = new Thread(new TCPServer(cliente, jugadores, zg, T));
                hiloS.start();

            }

            this.udpS.stop();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }
    }

    @Override
    public void run() {

        String jug = "";

        try {
            inp = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            turno = ZG == 1;
            System.out.println("ZG " + ZG);
            String msg = "";
            msg += "JUGAR " + (turno ? 1 : 2);
            msg += ";";

            msg += turno;
            System.out.println("msg " + msg);
            out.writeUTF(msg);

            while (true) {

                String recibidos = inp.readUTF();
                String recibido[] = recibidos.split(";");

                int fila = Integer.parseInt(recibido[1]);
                int columna = Integer.parseInt(recibido[2]);

                TZG[fila][columna] = Integer.parseInt(recibido[0]);

                jug = recibido[0];
                String info = "";
                info += Integer.parseInt(recibido[0]) + ";";
                info += fila + ";";
                info += columna + ";";

                if ((Integer.parseInt(recibido[0]) == 2 || Integer.parseInt(recibido[0]) == 1) && ganador(Integer.parseInt(recibido[0])) == true) {
                    info += Integer.parseInt(recibido[0]) + ";";

                } else {
                    info += 0 + ";";
                }

                for (Socket user : usuarios) {
                    out = new DataOutputStream(user.getOutputStream());
                    out.writeUTF(info);
                }

            }

        } catch (IOException ex) {

            System.out.println("Se ha desconectado un juegador ");
        } finally {
            String info = "1;2;1;5;";
            for (Socket user : usuarios) {
                try {
                    out = new DataOutputStream(user.getOutputStream());
                    out.writeUTF(info);

                    try {
                        out.writeUTF(info);
                    } catch (IOException e) {

                        for (int i = 0; i < usuarios.size(); i++) {
                            if (usuarios.get(i) == user) {
                                usuarios.remove(i);
                            }
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Fin del juego");
                }

            }
        }

    }

    public boolean ganador(int ficha) {

        int contFic = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TZG[i][j] == ficha) {
                    contador++;
                }
            }
            if (contador == 3) {
                return true;
            } else {
                contador = 0;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TZG[j][i] == ficha) {
                    contador++;
                }
            }
            if (contador == 3) {
                return true;
            } else {
                contador = 0;
            }
        }

        if (TZG[0][0] == ficha && TZG[1][1] == ficha && TZG[2][2] == ficha) {
            return true;
        }

        if (TZG[2][0] == ficha && TZG[1][1] == ficha && TZG[0][2] == ficha) {
            return true;
        }

        return false;
    }
}
