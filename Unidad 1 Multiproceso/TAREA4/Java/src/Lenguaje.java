import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Clase que genera cadenas aleatorias de letras mayúsculas
 * y las guarda en el fichero especificado por argumento.
 *
 * @author Juan Gabriel Galarza Claros
 */
public class Lenguaje {

    private static final int LINEAS = 100_000;
    private static final int LONGITUD_LINEA = 60;

    /**
     * Punto de entrada del subproceso.
     * Recibe por argumento el nombre del fichero de salida.
     *
     * @param args Contiene en args[0] el nombre del fichero a generar.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Falta el nombre del fichero de salida.");
            System.exit(1);
        }

        String nombreFichero = args[0];
        Random aleatorio = new Random();

        // Escritura eficiente con BufferedWriter
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {
            StringBuilder sb = new StringBuilder(LONGITUD_LINEA);

            for (int i = 0; i < LINEAS; i++) {
                sb.setLength(0); // Limpia el buffer para la siguiente línea
                for (int j = 0; j < LONGITUD_LINEA; j++) {
                    // Letras de la A a la Z ('A' = 65, son 26 letras en mayúscula)
                    char c = (char) ('A' + aleatorio.nextInt(26));
                    sb.append(c);
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
            System.exit(2);
        }

        // Finalización correcta del subproceso
        System.exit(0);
    }
}
