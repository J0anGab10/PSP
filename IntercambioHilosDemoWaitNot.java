package org.example;
import java.util.Random;

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

    private static class Productor implements Runnable {
        private Compartido compartido;

        public Productor(Compartido compartido) {
            this.compartido = compartido;
        }

        @Override
        public void run() {
            Random random = new Random();
            try {
                for (int i = 0; i < 10; i++) {
                    compartido.producir(random.nextInt(1, 101));
                }
            } catch (InterruptedException e) {
                System.out.println("Productor interrumpido");
            }
        }
    }

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
                System.out.println("Consumidor interrumpido");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Nombre Apellido");

        Compartido compartido = new Compartido();

        Thread productor = new Thread(new Productor(compartido));
        Thread consumidor = new Thread(new Consumidor(compartido));

        productor.start();
        consumidor.start();

        productor.join();
        consumidor.join();
        System.out.println("Fin del programa");
    }
}