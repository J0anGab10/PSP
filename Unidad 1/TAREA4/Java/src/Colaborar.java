import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de coordinar los subprocesos Lenguaje, ejecutándolos
 * tanto de forma secuencial como en paralelo para comparar sus tiempos.
 *
 * @author Juan Gabriel Galarza Claros
 */
public class Colaborar {

    private static final int NUM_PROCESOS = 4;

    /**
     * Ejecuta los 4 subprocesos de forma secuencial (uno a uno) y mide el tiempo.
     *
     * @param java Ruta al binario ejecutable java.exe.
     * @param cp   Ruta del classpath compilado.
     * @return El tiempo total transcurrido en milisegundos.
     * @throws Exception Si ocurre un error al lanzar o esperar el proceso.
     */
    public static long ejecutarSecuencial(String java, String cp) throws Exception {
        long t0 = System.nanoTime();

        for (int i = 1; i <= NUM_PROCESOS; i++) {
            String fichero = "seq" + i + ".txt";
            ProcessBuilder pb = new ProcessBuilder(java, "-cp", cp, "Lenguaje", fichero);
            Process p = pb.start();

            int ret = p.waitFor(); // Espera a cada uno antes de lanzar el siguiente
            if (ret != 0) {
                System.err.println("Error en subproceso secuencial " + i + ": código " + ret);
            }
        }

        return (System.nanoTime() - t0) / 1_000_000;
    }

    /**
     * Ejecuta los 4 subprocesos en paralelo (lanzar todos, esperar después) y mide el tiempo.
     *
     * @param java Ruta al binario ejecutable java.exe.
     * @param cp   Ruta del classpath compilado.
     * @return El tiempo total transcurrido en milisegundos.
     * @throws Exception Si ocurre un error al lanzar o esperar los procesos.
     */
    public static long ejecutarParalelo(String java, String cp) throws Exception {
        long t0 = System.nanoTime();
        List<Process> procesos = new ArrayList<>();

        // Bucle 1: Lanzar todos seguidos (sin bloquear con waitFor)
        for (int i = 1; i <= NUM_PROCESOS; i++) {
            String fichero = "par" + i + ".txt";
            ProcessBuilder pb = new ProcessBuilder(java, "-cp", cp, "Lenguaje", fichero);
            procesos.add(pb.start());
        }

        // Bucle 2: Esperar a que terminen todos y comprobar resultados
        for (int i = 0; i < procesos.size(); i++) {
            int ret = procesos.get(i).waitFor();
            if (ret != 0) {
                System.err.println("Error en subproceso paralelo " + (i + 1) + ": código " + ret);
            }
        }

        return (System.nanoTime() - t0) / 1_000_000;
    }

    /**
     * Método principal que ejecuta las dos mediciones y calcula la aceleración.
     *
     * @param args Argumentos de consola (no requeridos).
     * @throws Exception Si ocurre algún error durante la ejecución.
     */
    public static void main(String[] args) throws Exception {
        String java = System.getProperty("java.home") + "\\bin\\java.exe";
        String cp = System.getProperty("java.class.path");

        System.out.println("Iniciando ejecución secuencial...");
        long tiempoSec = ejecutarSecuencial(java, cp);

        System.out.println("Iniciando ejecución en paralelo...");
        long tiempoPar = ejecutarParalelo(java, cp);

        double mejora = (double) tiempoSec / tiempoPar;
        int nucleos = Runtime.getRuntime().availableProcessors();

        System.out.println("\n----------------- RESULTADOS -----------------");
        System.out.printf("Secuencial: %d ms%n", tiempoSec);
        System.out.printf("Paralelo:   %d ms%n", tiempoPar);
        System.out.printf("Aceleración / Mejora: %.2f%n", mejora);
        System.out.println("Núcleos disponibles:  " + nucleos);
        System.out.println("----------------------------------------------");
    }
}