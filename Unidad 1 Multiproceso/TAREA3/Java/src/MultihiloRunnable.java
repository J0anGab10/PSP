/*
 * ==========================================================
 * TAREA 2 (apartado 2) - Crear hilos IMPLEMENTANDO Runnable
 *
 * IDEA: lo mismo que el apartado 1, pero separando la TAREA del HILO.
 *
 * CÓMO FUNCIONA:
 * 1. MultihiloRunnableDemo implementa Runnable: es la TAREA (qué hacer).
 * 2. Creamos UN solo objeto tarea.
 * 3. Creamos 8 objetos Thread (los HILOS) y a cada uno le pasamos la misma tarea.
 * 4. start() arranca cada hilo, que ejecuta el run() de la tarea.
 *
 * VENTAJA DE RUNNABLE: la clase queda libre para heredar de otra
 * (en Java solo se puede heredar de UNA clase).
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */

// Esta clase NO es un hilo: es la TAREA que ejecutará un hilo.
class MultihiloRunnableDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("Hilo Runnable -> Nombre: " + Thread.currentThread().getName()
                + ", ID: " + Thread.currentThread().threadId());
    }
}

public class MultihiloRunnable {
    public static void main(String[] args) {
        int numHilos = 8;

        System.out.println("Autor: Juan Gabriel Galarza Claros");
        System.out.println("Hilo principal -> Nombre: " + Thread.currentThread().getName()
                + ", ID: " + Thread.currentThread().threadId());

        // UNA sola tarea, compartida por los 8 hilos.
        MultihiloRunnableDemo tarea = new MultihiloRunnableDemo();

        for (int i = 0; i < numHilos; i++) {
            Thread hilo = new Thread(tarea); // el HILO recibe la TAREA en el constructor
            hilo.start();
        }
    }
}
