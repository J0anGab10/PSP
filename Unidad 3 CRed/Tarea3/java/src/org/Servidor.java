package org;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Servidor {

    static Random random = new Random();

    static String leerSensores(String nombre) {
        if(nombre.equals("temperatura")) {
            return "Temperatura: " + random.nextInt(15,35) + "ºC." ;
        } else if (nombre.equals("humedad")) {
            return "Humedad: " + random.nextInt(20,90) + "%." ;
        } else if (nombre.equals("viento")) {
            return "Viento: " + random.nextInt(0,60) + "km/h." ;
        }
        return "ERROR: el sensor " + nombre + " no existe";
    }

    public static void main (String[] args) {

        int puerto = 12345;


        try (DatagramSocket socketServidor = new DatagramSocket(puerto)) {

            while (true) {
                System.out.println("Servidor iniciado correctamente.");

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socketServidor.receive(packet);

                String mensaje = new String(packet.getData(), 0, packet.getLength());
                InetAddress ipEmisor = packet.getAddress(); // para responder
                int puertoEmisor = packet.getPort();

                String respuesta;
                if (mensaje.equals("@ListadoSensores@")) {
                    respuesta = "sensores disponibles : Temperatura, humedad, viento";
                }else if (mensaje.equals("@ayuda@")) {
                    respuesta = "Debes seleccionar el tipo de sensor del que necesitas información";
                }else if (mensaje.equals("@salir@")) {
                    respuesta = "Saliendo del servidor...";
                } else if (mensaje.startsWith("@sensor#") && mensaje.endsWith("@")) {
                    String nombre = mensaje.substring(8, mensaje.length() -1);
                    respuesta = leerSensores(nombre);
                }else {
                    respuesta = "ERROR: Mensaje no reconocido";
                }

                byte[] datos = respuesta.getBytes();
                DatagramPacket paqueteRespuesta = new DatagramPacket(datos, datos.length, ipEmisor, puertoEmisor);
                socketServidor.send(paqueteRespuesta);
            }
        }catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
