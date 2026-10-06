/**
 * @author: Juan Gabriel Galarza Claros
 * @since: 29/09/2026
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lanzador {
    public static void main(String[] args) throws Exception {

        // OBLIGATORIO PARA LAS CAPTURAS: autor de la actividad
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        String java = System.getProperty("java.home") + "\\bin\\java.exe";

        // Preparamos los dos procesos
        ProcessBuilder pb1 = new ProcessBuilder(java, "-cp", System.getProperty("java.class.path"), "GeneraNumeros", "2");
        ProcessBuilder pb2 = new ProcessBuilder(java, "-cp", System.getProperty("java.class.path"), "GeneraNumeros", "2");

        // Lanzamos los dos y guardamos cada Process en una variable distinta
        Process hijo1 = pb1.start();
        Process hijo2 = pb2.start();

        // Mostramos por pantalla el PID de cada uno y del padre
        System.out.println("Soy el padre, mi PID es: " + ProcessHandle.current().pid());
        System.out.println("He lanzado un hijo 1 PID: " + hijo1.pid());
        System.out.println("He lanzado un hijo 2 PID: " + hijo2.pid());

        // --- LECTURA HIJO 1 ---
        BufferedReader lector1 = new BufferedReader(new InputStreamReader(hijo1.getInputStream()));
        String linea1;
        while ((linea1 = lector1.readLine()) != null) {
            System.out.println("[HIJO 1] " + linea1);
        }

        // --- LECTURA HIJO 2 ---
        BufferedReader lector2 = new BufferedReader(new InputStreamReader(hijo2.getInputStream()));
        String linea2;
        while ((linea2 = lector2.readLine()) != null) {
            System.out.println("[HIJO 2] " + linea2);
        }

        // --- ESPERA Y CÓDIGOS DE SALIDA ---
        int codigo1 = hijo1.waitFor();
        if (codigo1 == 0) {
            System.out.println("El hijo 1 ha terminado correctamente (codigo 0).");
        } else if (codigo1 == 2) {
            System.out.println("El hijo 1 ha recibido un argumento no valido (codigo 2).");
        } else {
            System.out.println("El hijo 1 ha terminado con el codigo: " + codigo1);
        }

        int codigo2 = hijo2.waitFor();
        if (codigo2 == 0) {
            System.out.println("El hijo 2 ha terminado correctamente (codigo 0).");
        } else if (codigo2 == 2) {
            System.out.println("El hijo 2 ha recibido un argumento no valido (codigo 2).");
        } else {
            System.out.println("El hijo 2 ha terminado con el codigo: " + codigo2);
        }
    }
}
