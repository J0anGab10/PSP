import java.sql.SQLOutput;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Juan Gabriel Galarza Claros
 */

class Productor implements Runnable {
    private BlockingQueue<Integer> cola;

    public Productor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                int numero = new Random().nextInt(1, 101);
                System.out.println("Productor genero " + numero);
                cola.put(numero);
            }
        }catch (InterruptedException e) {
            System.err.println("Productor interrumpido");
        }
    }
}

class Consumidor implements Runnable {
    private BlockingQueue<Integer> cola;

    public Consumidor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                int numero = cola.take();
                System.out.println("Consumidor proceso " + numero);

                Thread.sleep(300);
            }
        }catch(InterruptedException e) {
            System.err.println("Productor interrumpido");
        }
    }
}

public class IntercambioHilosDemoCola {
    public static void main() {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(3);
        Thread productor = new Thread(new Productor(cola));
        Thread consumidor = new Thread(new Consumidor(cola));

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
