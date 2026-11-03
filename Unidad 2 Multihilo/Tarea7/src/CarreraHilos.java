import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarreraHilos {

    // Lista sincronizada para registrar de forma segura el orden de llegada de los corredores
    private static final List<String> ordenLlegada = Collections.synchronizedList(new ArrayList<>());

    static class Corredor implements Runnable {
        private String nombre;

        public Corredor(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            try {
                for (int etapa = 1; etapa <= 10; etapa++) {
                    Thread.sleep((long) (Math.random() * 100 + 50));
                    System.out.println(nombre + " ha avanzado al punto " + etapa);
                }

                ordenLlegada.add(nombre);
                System.out.println(nombre + " ha cruzado la meta");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " se a interrumpido");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Autor: Juan Gabriel Galarza Claros");

        // Crear 5 hilos
        Thread corredor1 = new Thread(new Corredor("Corredor 1"));
        Thread corredor2 = new Thread(new Corredor("Corredor 2"));
        Thread corredor3 = new Thread(new Corredor("Corredor 3"));
        Thread corredor4 = new Thread(new Corredor("Corredor 4"));
        Thread corredor5 = new Thread(new Corredor("Corredor 5"));

        // Poner prioridades
        corredor1.setPriority(Thread.MIN_PRIORITY);
        corredor2.setPriority(Thread.NORM_PRIORITY);
        corredor3.setPriority(Thread.MAX_PRIORITY);
        corredor4.setPriority(Thread.NORM_PRIORITY);
        corredor5.setPriority(Thread.MIN_PRIORITY);

        System.out.println("Comienza la carrera de hilos\n");

        corredor1.start();
        corredor2.start();
        corredor3.start();
        corredor4.start();
        corredor5.start();

        corredor1.join();
        corredor2.join();
        corredor3.join();
        corredor4.join();
        corredor5.join();

        // Mostrar orden
        System.out.println("Final de la carrera");
        synchronized (ordenLlegada) {
            for (int i = 0; i < ordenLlegada.size(); i++) {
                System.out.println((i + 1) + "º puesto: " + ordenLlegada.get(i));
            }
        }
    }
}