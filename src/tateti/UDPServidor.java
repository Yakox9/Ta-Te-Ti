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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ElianaBcrra - Jesus Varela
 */
public class UDPServidor implements Runnable {

     int puerto_server = 0;
    byte[] buffer = new byte[1024];

    
 
     public static void main(String[] args) {
     int port = 4444;
         Thread servidor = new Thread(new UDPServidor(port));
    servidor.start();
    TCPServer serv = new TCPServer(servidor);
    serv.escuchar(port);
    }


    public UDPServidor(int port) {
        this.puerto_server = port;
    }

    @Override
    public void run() {

        try {

            DatagramSocket socketUDP = new DatagramSocket();

            while (true) {

                String mensaje = "JESELIAN" + ":" + puerto_server + ":";

                buffer = mensaje.getBytes();

             //   DatagramPacket msjEnv = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("10.0.255.255"), puerto_server);
                 DatagramPacket msjEnv = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.1.255"), puerto_server);
                socketUDP.send(msjEnv);

            }

        } catch (SocketException ex) {
            Logger.getLogger(UDPServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
}
