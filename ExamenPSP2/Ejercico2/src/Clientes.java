import java.util.Random;
import java.util.concurrent.Semaphore;
public class Clientes implements Runnable {
    private Semaphore semaphore;
    private String nombre;
    public Clientes(Semaphore semaphore, String nombre) {
        this.semaphore = semaphore;
        this.nombre = nombre;
    }
    @Override
    public void run() {
        try {
// Adquiere un permiso de semáforo
            System.out.println("Jugador " + nombre + " quiere entrar");
            semaphore.acquire(); // si no quedan plazas, el hilo espera aquí, y si quedan resta 1

            // Solo llegamos aquí si TENEMOS permiso. Por eso el try/finally
            // que devuelve el permiso empieza DESPUÉS del acquire().
            try {
                int enSala = SalaVideojuegos.PLAZAS - SalaVideojuegos.salaDisponible.availablePermits();
                System.out.println("Jugador " + nombre + " ENTRA (en sala: " + enSala + ")");
                Thread.sleep(new Random().nextInt(2000, 5001)); // juega 2-5 s
                System.out.println("Jugador " + nombre + " SALE");
            } finally {
                // Mejora: en finally el permiso se devuelve SIEMPRE, aunque haya excepción.
                salaDisponible.release();
        } catch (InterruptedException e) {
//Mensaje por consola sobre la interrupción
            return; // no ha cogido el permiso: no hay nada que liberar
        }
        try {
// Mensaje por consola de que el ‘Cliente X está realizando un pedido’
// Simula el tiempo de realizar el pedido (1 segundo)
// Mensaje por consola ‘Cliente X ha terminado su pedido’
        } catch (InterruptedException e) {
//Mensaje por consola sobre la interrupción
        } finally {
// Libera el permiso del semáforo para otros clientes
        }
    }
}
