import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class Cliente {
    public static void main(String[] args) {
        String direccionServidor = "127.0.0.1";
        int puerto = 12345;
        System.out.println("CLIENTE DE CHAT\n");

        try (
                // Establecer conexion con el servidor
                Socket socket = new Socket(direccionServidor, puerto);

                // Leer mensajes por teclado
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

                // Recibir respuestas del servidor
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Enviar mensajes servidor
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Conectando al servidor.");
            System.out.println(entrada.readLine());

            String mensaje;

            // Comunicacion con el servidor
            while (true) {
                System.out.print("Tu mensaje: ");

                mensaje = teclado.readLine();
                salida.println(mensaje);

                String respuesta = entrada.readLine();

                System.out.println("Servidor: " + respuesta);

                // Finalizar cuando se escriba salir
                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }
            }
            System.out.println("El chat a terminado.");

        } catch (IOException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}