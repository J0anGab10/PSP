import java.util.Random;
import java.util.concurrent.Semaphore;
public class SalaVideojuegos {

    static final int PLAZAS = 5;
    static Semaphore salaDisponible = new Semaphore(PLAZAS);

    static class Jugador implements Runnable {
        private int numero;

        public Jugador(int numero) {
            this.numero = numero;
        }

        @Override
        public void run() {
            try {
                System.out.println("Jugador " + numero + " quiere entrar");
                salaDisponible.acquire();

                int enSala = PLAZAS - salaDisponible.availablePermits();
                System.out.println("Jugador " + numero + " entra en sala: " + enSala);

                Thread.sleep(new Random().nextInt(2000, 5001));

                System.out.println("Jugador " + numero + " SALE");

            } catch (InterruptedException e) {
                System.out.println("El jugador " + numero + " fue interrumpido");
                Thread.currentThread().interrupt();
            } finally {
                // aporte para el 10
                // colocar release() en un finally garantiza que el permiso siemper
                // se devuelva al semaforo, incluso si el hilo falla o se interrumpe por error
                salaDisponible.release();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Nombre: Juan Gabriel Galarza Claros");

        // Bucle para iniciar y crear  10 hilos
        for (int i = 1; i <= 10; i++) {
            Thread hilo = new Thread(new Jugador(i));
            hilo.start();
        }
    }
}