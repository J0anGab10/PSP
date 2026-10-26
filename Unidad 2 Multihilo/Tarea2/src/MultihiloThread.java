/**
 * @author Juan Gabriel Galarza Claros
 */
class MultihiloThreadDemo extends Thread {
    @Override
    public void run() {
        // Muestra el nombre y el ID del hilo en ejecución
        System.out.println("Hilo ejecutándose -> Nombre: " + Thread.currentThread().getName() + ", ID: " + Thread.currentThread().threadId());
    }
}
public class MultihiloThread {
    public static void main(String[] args) {
        int numHilos = 8;

        // Muestra el nombre y el ID del hilo main,reemplaza TuNombre por tu nombre real
        System.out.println("\nHilo Principal (main) -> Nombre: TuNombre, ID: " + Thread.currentThread().threadId());

        for (int i = 0; i < numHilos; i++) {
            // Crea un objeto MultihiloThreadDemo y arráncalo con start()
            MultihiloThreadDemo hilo = new MultihiloThreadDemo();
            hilo.start();
        }
    }
}