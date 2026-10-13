import java.util.Random;

public class GeneraNumeros {
    public static void main(String[] args) {
        // Cantidad por defecto si no se pasa argumento
        int cantidad = 35;

        // Comprobación y parseo del argumento recibido
        if (args.length > 0) {
            try {
                cantidad = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("El argumento no es un número: " + args[0]);
                System.exit(2);
            }
        }

        // Generación de números aleatorios entre 0 y 100
        Random aleatorio = new Random();
        for (int i = 0; i < cantidad; i++) {
            System.out.println(aleatorio.nextInt(101));
        }

        // Finalización correcta del proceso hijo
        System.exit(0);
    }
}