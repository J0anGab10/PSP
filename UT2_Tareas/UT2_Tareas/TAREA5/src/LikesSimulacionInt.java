/*
 * ==========================================================
 * TAREA 5 - EXPERIMENTO con int normal (versión que FALLA)
 *
 * IDEA: lo mismo, pero con un int y contadorLikes++. Se esperan 30.000
 * likes, pero normalmente salen MENOS, y cada vez un número distinto.
 *
 * POR QUÉ FALLA (condición de carrera): contadorLikes++ son 3 pasos:
 * 1) leer el valor  2) sumarle 1  3) escribirlo
 * Si dos hilos leen el mismo valor a la vez, los dos escriben lo mismo
 * y se pierde un like.
 *
 * NOTA: en un ordenador de un solo núcleo puede salir bien por casualidad.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */
public class LikesSimulacionInt {

    static int contadorLikes = 0; // int normal: contadorLikes++ NO es atómico

    static final int LIKES_POR_USUARIO = 10_000;

    static class Usuario extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < LIKES_POR_USUARIO; i++) {
                contadorLikes++; // 3 pasos: leer, sumar 1, escribir -> otro hilo se puede colar
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
        for (Usuario u : usuarios) {
            u.join();
        }

        System.out.println("Esperados: " + 3 * LIKES_POR_USUARIO);
        System.out.println("Obtenidos: " + contadorLikes);
    }
}
