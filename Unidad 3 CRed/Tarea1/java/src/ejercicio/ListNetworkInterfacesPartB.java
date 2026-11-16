package ejercicio;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class ListNetworkInterfacesPartB {
    public static void main(String[] args) throws SocketException {

        // ------------------ LISTAR INTERFACES DE RED DEL SISTEMA -------------------

        // a) Pedir las interfaces con NetworkInterface.getNetworkInterfaces()
        // b) Convertir el resultado (enumeración) en una lista
        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

        // c) Mostrar cuántas interfaces se han encontrado
        System.out.println("Interfaces encontradas: " + interfaces.size());

        System.out.println("\n-------- MOSTRAR CONFIGURACIÓN IP DE CADA INTERFAZ ----------");

        // a) Para cada interfaz de la lista (usando el bucle for con 'i' de tu Parte A):
        for (int i = 0; i < interfaces.size(); i++) {
            NetworkInterface n1 = interfaces.get(i);

            // - Mostramos el nombre corto con getName()
            System.out.println("Nombre: " + n1.getName());

            // - Mostramos el nombre descriptivo con getDisplayName()
            System.out.println("Descripción: " + n1.getDisplayName());

            // - Mostramos si está activa o inactiva con isUp()
            String estado = n1.isUp() ? "ACTIVA" : "INACTIVA";
            System.out.println("Estado: " + estado);

            // - Ampliación Parte B: Obtener direcciones IP, prefijo y broadcast
            List<InterfaceAddress> directions = n1.getInterfaceAddresses();

            if (directions.isEmpty()) {
                System.out.println("  -> Sin direcciones IP configuradas.");
            } else {
                System.out.println("  -> Configuración IP:");
                for (int j = 0; j < directions.size(); j++) {
                    InterfaceAddress dir = directions.get(j);

                    // Distinguimos entre IPv4 e IPv6 usando instanceof
                    if (dir.getAddress() instanceof Inet4Address) {
                        System.out.println("     [IPv4] Dirección: " + dir.getAddress().getHostAddress());
                        System.out.println("            Prefijo: /" + dir.getNetworkPrefixLength());
                        System.out.println("            Broadcast: " + (dir.getBroadcast() != null ? dir.getBroadcast().getHostAddress() : "N/A"));
                    } else if (dir.getAddress() instanceof Inet6Address) {
                        System.out.println("     [IPv6] Dirección: " + dir.getAddress().getHostAddress());
                        System.out.println("            Prefijo: /" + dir.getNetworkPrefixLength());
                    }
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }
}