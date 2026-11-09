import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * ==========================================================
 * TAREA 7 - Carrera de hilos con PRIORIDADES
 *
 * IDEA: 5 corredores (hilos) con distintas prioridades recorren 10 etapas.
 * Al llegar a la meta se apuntan en una lista compartida.
 *
 * PRIORIDADES: van de 1 (MIN_PRIORITY) a 10 (MAX_PRIORITY). La normal es 5.
 * Se ponen con setPriority() ANTES de start().
 *
 * LISTA SINCRONIZADA: Collections.synchronizedList() hace que dos corredores
 * que llegan a la vez no estropeen la lista al añadirse.
 *
 * LO QUE VERÁS: el de prioridad 10 NO siempre gana. La prioridad es solo
 * una SUGERENCIA al sistema operativo, y solo se nota cuando la CPU está
 * saturada. Aquí los hilos pasan casi todo el tiempo dormidos (sleep),
 * así que el ganador lo deciden los tiempos aleatorios.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */
public class CarreraHilos {

    static final int ETAPAS = 10;

    // Lista compartida por los 5 hilos. synchronizedList hace que cada add()
    // sea seguro: dos corredores que llegan a la vez no se pisan.
    private static final List<String> ordenLlegada = Collections.synchronizedList(new ArrayList<>());

    // La TAREA de cada corredor (implementa Runnable, no es un hilo).
    static class Corredor implements Runnable {
        private final String nombre;

        public Corredor(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            Random random = new Random();
            int prioridad = Thread.currentThread().getPriority();
            try {
                for (int etapa = 1; etapa <= ETAPAS; etapa++) {
                    Thread.sleep(random.nextInt(50, 151)); // "tiempo de carrera" de la etapa
                    System.out.println(nombre + " (prioridad " + prioridad + ") ha avanzado al punto " + etapa + ".");
                }
                ordenLlegada.add(nombre); // sección crítica protegida por la lista sincronizada
                System.out.println(">>> " + nombre + " ha cruzado la meta");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " se ha interrumpido");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        // Prioridades en el orden que pide el enunciado (corredor 1 a 5).
        int[] prioridades = {
                Thread.MIN_PRIORITY,   // Corredor 1 -> 1
                Thread.NORM_PRIORITY,  // Corredor 2 -> 5
                Thread.MAX_PRIORITY,   // Corredor 3 -> 10
                Thread.NORM_PRIORITY,  // Corredor 4 -> 5
                Thread.MIN_PRIORITY    // Corredor 5 -> 1
        };

        Thread[] corredores = new Thread[prioridades.length];
        for (int i = 0; i < corredores.length; i++) {
            String nombre = "Corredor " + (i + 1);
            corredores[i] = new Thread(new Corredor(nombre), nombre); // tarea + nombre del hilo
            corredores[i].setPriority(prioridades[i]);                // ANTES de start()
        }

        System.out.println("Comienza la carrera de hilos\n");
        for (Thread c : corredores) c.start();
        for (Thread c : corredores) c.join(); // main espera a que lleguen todos

        System.out.println("\nFinal de la carrera");
        // Para RECORRER una synchronizedList hay que sincronizar sobre ella a mano.
        synchronized (ordenLlegada) {
            for (int i = 0; i < ordenLlegada.size(); i++) {
                System.out.println((i + 1) + "º puesto: " + ordenLlegada.get(i));
            }
        }
    }
}
