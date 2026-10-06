import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Lanzador {
    public static void main(String[] args) throws Exception {
        String java = System.getProperty("java.home") + "\\bin\\java.exe";
        String cp = System.getProperty("java.class.path");

        // 5.a) Crear y lanzar los dos procesos en variables distintas
        ProcessBuilder pb1 = new ProcessBuilder(java, "-cp", cp, "GeneraNumeros", "10");
        ProcessBuilder pb2 = new ProcessBuilder(java, "-cp", cp, "GeneraNumeros", "10");

        Process p1 = pb1.start();
        Process p2 = pb2.start();

        // 5.b) Mostrar los PID de ambos
        System.out.println("PID Hijo 1: " + p1.pid());
        System.out.println("PID Hijo 2: " + p2.pid());

        // 5.c) Leer la salida del Hijo 1
        System.out.println("\n--- Salida Hijo 1 ---");
        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
            String linea;
            while ((linea = br1.readLine()) != null) {
                System.out.println("[HIJO 1] " + linea);
            }
        }

        // 5.c) Leer la salida del Hijo 2
        System.out.println("\n--- Salida Hijo 2 ---");
        try (BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
            String linea;
            while ((linea = br2.readLine()) != null) {
                System.out.println("[HIJO 2] " + linea);
            }
        }

        // 5.d) Esperar a los dos y mostrar sus códigos devueltos
        int ret1 = p1.waitFor();
        int ret2 = p2.waitFor();

        System.out.println("\nHijo 1 terminó con código: " + ret1);
        System.out.println("Hijo 2 terminó con código: " + ret2);
    }
}