import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * ==========================================================
 * TAREA 4 (apartado 1) - Productor-consumidor con BlockingQueue
 *
 * IDEA: un hilo PRODUCE números y otro los CONSUME. Se los pasan por una
 * cola compartida en la que solo caben 3.
 *
 * CÓMO FUNCIONA:
 * - put(x): mete un número. Si la cola está LLENA, el productor espera.
 * - take(): saca un número. Si la cola está VACÍA, el consumidor espera.
 * - El consumidor es más lento (sleep de 300 ms), así que la cola se llena
 * y el productor tiene que esperar. Esa espera ES la sincronización.
 *
 * LO IMPORTANTE: no escribimos synchronized, wait() ni notify().
 * La cola lo hace todo por dentro.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */

class Productor implements Runnable {
    private final BlockingQueue<Integer> cola; // la MISMA cola que usa el consumidor

    public Productor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < 10; i++) {
                int numero = random.nextInt(1, 101); // entre 1 y 100
                System.out.println("Productor: genero " + numero);
                cola.put(numero); // si ya hay 3 en la cola, el productor ESPERA aquí
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Productor interrumpido");
        }
    }
}

class Consumidor implements Runnable {
    private final BlockingQueue<Integer> cola;

    public Consumidor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                int numero = cola.take(); // si la cola está vacía, el consumidor ESPERA aquí
                System.out.println("    Consumidor: proceso " + numero);
                Thread.sleep(300);        // el consumidor es más lento que el productor
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumidor interrumpido");
        }
    }
}

public class IntercambioHilosDemoCola {
    public static void main(String[] args) {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(3); // capacidad fija: 3

        Thread productor = new Thread(new Productor(cola));
        Thread consumidor = new Thread(new Consumidor(cola));
        productor.start();
        consumidor.start();

        try {
            productor.join();  // main espera a los dos antes de terminar
            consumidor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Fin del programa");
    }
}
