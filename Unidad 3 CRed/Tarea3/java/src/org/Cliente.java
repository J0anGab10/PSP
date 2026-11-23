package org;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Cliente {
    public static void main (String[] args) {

        int puerto = 12345;

        try (DatagramSocket socketCliente = new DatagramSocket(puerto)) {
            Scanner teclado = new Scanner(String[]args) {

                InetAddress destino = InetAddress.getByName("127.0.0.1"); // ó “localhost”

                String mensaje;

                while (!mensaje.equals("@salir")) {
                    System.out.println();
                    System.out.println("1. Consultar un sensor");
                    System.out.println("2. Listado de sensores");
                    System.out.println("3. Ayuda");
                    System.out.println("4. Salir");
                    System.out.println("Opcion: ");
                    String opcion = teclado.nextLine();


                    if (opcion.equals("1")) {
                        System.out.println("Nombre del sensor : ");
                        String nombre = teclado.nextLine();
                        mensaje = "@sensor#" + nombre + "@";
                    }

                }
                byte[] datos = "Hola, servidor".getBytes();
                DatagramPacket packet = new DatagramPacket(datos, datos.length, destino, puerto);
                socketCliente.send(packet);

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socketCliente.receive(packet);
                String mensaje = new String(packet.getData(), 0, packet.getLength());
                InetAddress ipEmisor = packet.getAddress(); // para responder
                int puertoEmisor = packet.getPort();
            }
        }catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
