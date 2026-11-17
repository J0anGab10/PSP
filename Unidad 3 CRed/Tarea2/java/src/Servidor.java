import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;
        System.out.println("SERVIDOR DE CHAT\n");

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            System.out.println("Servidor iniciado correctamente.");
            System.out.println("Esperando conexión en el puerto " + puerto);

            // Esperar la conexion del cliente
            try (Socket cliente = servidor.accept()) {

                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                // Preparar entrada y salida de mensajes
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

                // Mensaje de bienvenida
                System.out.println("CHAT DEL SERVIDOR INICIADO\n");
                salida.println("Bienvenido al chat del servidor.");

                String mensaje;

                // Recibir mensajes mientras el cliente siga conectado
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Cliente: " + mensaje);

                    // Comprobar si el cliente quiere salir
                    if (mensaje.equalsIgnoreCase("salir")) {

                        salida.println("CHAT DEL SERVIDOR FINALIZADO");
                        System.out.println("El cliente ha cerrado el chat.");
                        break;
                    }
                    // Responder al cliente
                    String respuesta = "Mensaje recibido: " + mensaje;
                    salida.println(respuesta);
                    System.out.println("Servidor: " + respuesta);
                }
            }
            System.out.println("Conexion finalizada.");
            System.out.println("Servidor finalizado.");

        } catch (IOException e) {

            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}