import java.util.Random;
import java.util.concurrent.Semaphore;

/*
 * ==========================================================
 * TAREA 6 - Sala de videojuegos con Semaphore
 *
 * IDEA: 10 jugadores quieren entrar en una sala con solo 4 plazas.
 *
 * EL SEMÁFORO ES UN CONTADOR DE PERMISOS (empieza en 4):
 * - acquire(): coge un permiso. Si quedan 0, el jugador ESPERA en la puerta.
 * - release(): devuelve el permiso y deja pasar a uno que esperaba.
 * - availablePermits(): cuántos permisos quedan libres.
 * Por eso: jugadores en sala = PLAZAS - availablePermits().
 *
 * CLAVE: el release() va en un finally que empieza DESPUÉS del acquire().
 * Así el permiso se devuelve siempre, pero solo si de verdad se cogió.
 *
 * EXPERIMENTO SIN release(): entran 4, nunca devuelven la plaza y los
 * otros 6 se quedan esperando para siempre (hay que pararlo con Stop).
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */
public class SalaVideojuegos {

    static final int PLAZAS = 4;
    static Semaphore salaDisponible = new Semaphore(PLAZAS); // 4 permisos = 4 plazas

    static class Jugador implements Runnable {
        private final int numero;

        public Jugador(int numero) {
            this.numero = numero;
        }

        @Override
        public void run() {
            try {
                System.out.println("Jugador " + numero + " quiere entrar");
                salaDisponible.acquire(); // si no quedan plazas, el hilo espera aquí, y si quedan resta 1

                // Solo llegamos aquí si TENEMOS permiso. Por eso el try/finally
                // que devuelve el permiso empieza DESPUÉS del acquire().
                try {
                    int enSala = PLAZAS - salaDisponible.availablePermits();
                    System.out.println("Jugador " + numero + " ENTRA (en sala: " + enSala + ")");
                    Thread.sleep(new Random().nextInt(2000, 5001)); // juega 2-5 s
                    System.out.println("Jugador " + numero + " SALE");
                } finally {
                    // Mejora: en finally el permiso se devuelve SIEMPRE, aunque haya excepción.
                    salaDisponible.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Jugador " + numero + " fue interrumpido");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Autor: Juan Gabriel Galarza Claros");
        for (int i = 1; i <= 10; i++) {
            new Thread(new Jugador(i)).start();
        }
    }
}
