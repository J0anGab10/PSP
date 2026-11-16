package ejercicio;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * @author Juan Gabriel Galarza Claross
 */

public class ListNetworkInterfacesPartA {
        public static void main(String[] args) throws SocketException {

            // ------------------ LISTAR INTERFACES DE RED DEL SISTEMA -------------------

            // a) Pedir las interfaces con NetworkInterface.getNetworkInterfaces()
            // b) Convertir el resultado (enumeración) en una lista
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            // c) Mostrar cuántas interfaces se han encontrado
            System.out.println("Interfaces encontradas: " + interfaces.size());

            System.out.println("\nMOSTRAR EL ESTADO Y EL NOMBRE DE CADA INTERFAZ\n");

            // a) Para cada interfaz de la lista:
            for (int i = 0; i < interfaces.size(); i++) {
                NetworkInterface n1 = interfaces.get(1);

                // - Mostramos el nombre corto con getName()
                System.out.println("Nombre: " + n1.getName());

                // - Mostramos el nombre descriptivo con getDisplayName()
                System.out.println("Descripción: " + n1.getDisplayName());

                // - Mostramos si está activa o inactiva con isUp()
                String estado = n1.isUp() ? "ACTIVA" : "INACTIVA";
                System.out.println("Estado: " + estado + "\n");
            }
        }
}
