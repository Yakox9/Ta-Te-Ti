/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.TryNode;

/**
 *
 * @author ElianaBcrra - Jesus Varela
 */
public class UDPCliente implements Runnable {

    final int puerto = 4444;
    byte[] buffer = new byte[1024];
    UDPServidor server = new UDPServidor(puerto);
    private Thread hiloC;
    int port = 0;
    public static void main(String[] args) {

        UDPCliente cliente = new UDPCliente();

        cliente.hiloC = new Thread(new UDPCliente());
        cliente.hiloC.start();

    }

    public UDPCliente() {
    }

    @Override
    public void run() {
        try {

            String ipC = "";
            int puertoC = 0;
            boolean band = false;

            while (!band) {
                DatagramSocket socketUDP = new DatagramSocket(puerto);
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);/**/
                socketUDP.receive(peticion);

                if (peticion != null) {
                    String mensaje = new String(peticion.getData());

                    InetAddress address = peticion.getAddress();

                    System.out.println(" mensaje recibido:    " + mensaje);
                    String recibido[] = mensaje.split(":");
                    puertoC = Integer.parseInt(recibido[1]);
                    ipC = address.toString();
                    if (recibido[0].toString().equals("JESELIAN")) {
                        port = Integer.parseInt(recibido[1]);
                        String prueba[] = ipC.split("/");

                        Thread hilo = new Thread(new TCPClient(port, prueba[1]));
                        hilo.start();
                    } else {

                    }
                    socketUDP.close();
                    band = true;
                }

            }

        } catch (SocketException ex) {
            Logger.getLogger(UDPCliente.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(UDPCliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
