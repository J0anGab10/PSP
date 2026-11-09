import java.util.concurrent.atomic.AtomicInteger;

/*
 * ==========================================================
 * TAREA 5 - Likes con AtomicInteger (versión CORRECTA)
 *
 * IDEA: 3 usuarios (hilos) dan 100 likes cada uno a la vez sobre el
 * MISMO contador. El total tiene que ser 300.
 *
 * POR QUÉ FUNCIONA: incrementAndGet() es ATÓMICO. Leer, sumar 1 y escribir
 * ocurren como un solo paso: ningún otro hilo puede colarse en medio.
 *
 * join(): el main espera a los 3 hilos antes de mostrar el total.
 * Sin join(), podría mostrar el total cuando aún no han terminado.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */
public class LikesSimulacion {

    // static: lo comparten todos los hilos (está en la memoria común del proceso).
    static AtomicInteger contadorLikes = new AtomicInteger(0);

    static final int LIKES_POR_USUARIO = 100;

    // static class: se puede crear desde main sin tener un objeto LikesSimulacion.
    static class Usuario extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < LIKES_POR_USUARIO; i++) {
                int valor = contadorLikes.incrementAndGet(); // suma 1 y devuelve el NUEVO valor
                System.out.println(getName() + " → like nº " + valor);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        Usuario[] usuarios = new Usuario[3];
        for (int i = 0; i < usuarios.length; i++) {
            usuarios[i] = new Usuario();
            usuarios[i].start();
        }

        // Sin join(), el main podría mostrar el total antes de que acaben los hilos.
        for (Usuario u : usuarios) {
            u.join();
        }

        System.out.println("Total de likes: " + contadorLikes.get()); // siempre 300
    }
}
