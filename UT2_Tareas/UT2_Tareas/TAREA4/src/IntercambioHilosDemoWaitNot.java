import java.util.Random;

/*
 * ==========================================================
 * TAREA 4 (apartado 2) - Productor-consumidor con wait() y notify()
 * (Código del profesor, con comentarios sencillos añadidos)
 *
 * IDEA: el mismo problema que el apartado 1, pero haciendo A MANO
 * lo que antes hacía la BlockingQueue.
 *
 * LA CLASE Compartido es un "hueco" donde cabe UN solo número:
 * - synchronized: solo un hilo puede estar dentro a la vez.
 * - wait(): el hilo se duerme y SUELTA el cerrojo para que entre el otro.
 * - notify(): despierta al otro hilo que estaba esperando.
 * - producir(): si el hueco está ocupado -> wait(). Si no, deja el número y notify().
 * - consumir(): si el hueco está vacío -> wait(). Si no, lo coge y notify().
 *
 * DIFERENCIA CON EL APARTADO 1: allí la cola tenía 3 huecos; aquí solo hay 1,
 * así que productor y consumidor se van turnando: genero, proceso, genero...
 *
 * REGLA DE ORO: wait() siempre dentro de un WHILE, nunca de un if,
 * porque al despertar hay que volver a comprobar la condición.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */
public class IntercambioHilosDemoWaitNot {

    // Hueco compartido para UN número. Hace a mano lo que hacía la BlockingQueue.
    private static class Compartido {
        private int dato;
        private boolean disponible = false; // ¿hay un número esperando a ser consumido?

        // Equivale a put(): si el hueco está ocupado, el productor espera.
        public synchronized void producir(int valor) throws InterruptedException {
            while (disponible) {
                wait(); // suelta el cerrojo y espera a que el consumidor avise
            }
            dato = valor;
            disponible = true;
            System.out.println("Productor: genero " + valor);
            notify(); // avisa al consumidor de que ya hay un número
        }

        // Equivale a take(): si el hueco está vacío, el consumidor espera.
        public synchronized int consumir() throws InterruptedException {
            while (!disponible) {
                wait(); // suelta el cerrojo y espera a que el productor avise
            }
            disponible = false;
            System.out.println("Consumidor: proceso " + dato);
            notify(); // avisa al productor de que el hueco está libre
            return dato;
        }
    }

    // Hilo productor: genera 10 números y los deja en el hueco compartido.
    private static class Productor implements Runnable {
        private Compartido compartido; // el MISMO objeto que usa el consumidor

        public Productor(Compartido compartido) {
            this.compartido = compartido;
        }

        @Override
        public void run() {
            Random random = new Random();
            try {
                for (int i = 0; i < 10; i++) {
                    compartido.producir(random.nextInt(1, 101)); // número entre 1 y 100
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // reactivar la marca de interrupción
                System.out.println("Productor interrumpido");
            }
        }
    }

    // Hilo consumidor: recoge 10 números del hueco compartido.
    private static class Consumidor implements Runnable {
        private Compartido compartido;

        public Consumidor(Compartido compartido) {
            this.compartido = compartido;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    compartido.consumir();
                    Thread.sleep(300); // el consumidor es más lento que el productor
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumidor interrumpido");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        Compartido compartido = new Compartido(); // un solo hueco para los dos hilos

        Thread productor = new Thread(new Productor(compartido));
        Thread consumidor = new Thread(new Consumidor(compartido));

        productor.start();
        consumidor.start();

        productor.join();  // main espera a que terminen los dos
        consumidor.join();
        System.out.println("Fin del programa");
    }
}
