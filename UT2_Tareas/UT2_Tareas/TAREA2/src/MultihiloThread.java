/*
 * ==========================================================
 * TAREA 2 (apartado 1) - Crear hilos EXTENDIENDO Thread
 *
 * IDEA: creamos 8 hilos y cada uno dice su nombre y su ID.
 *
 * CÓMO FUNCIONA:
 * 1. MultihiloThreadDemo hereda de Thread, así que cada objeto ES un hilo.
 * 2. En run() ponemos lo que hará el hilo.
 * 3. En main creamos 8 objetos y llamamos a start() en cada uno.
 * 4. start() crea el hilo de verdad y ese hilo ejecuta run().
 *
 * QUÉ VERÁS: los mensajes salen en distinto orden cada vez,
 * porque el orden lo decide el planificador del sistema operativo.
 *
 * OJO: si cambias start() por run(), NO se crea ningún hilo:
 * todo lo ejecuta el main y todos los mensajes diran "main".
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */

// La clase del hilo ES un Thread (hereda de él).
class MultihiloThreadDemo extends Thread {
    @Override
    public void run() {
        // run() contiene lo que hace el hilo. Thread.currentThread() devuelve
        // el hilo que está ejecutando esta línea en este momento.
        System.out.println("Hilo ejecutándose -> Nombre: " + Thread.currentThread().getName()
                + ", ID: " + Thread.currentThread().threadId());
    }
}

// Solo puede haber UNA clase public por archivo, y da nombre al archivo.
public class MultihiloThread {
    public static void main(String[] args) {
        int numHilos = 8;

        // Aquí todavía solo existe el hilo main.
        System.out.println("Autor: Juan Gabriel Galarza Claros");
        System.out.println("Hilo principal -> Nombre: " + Thread.currentThread().getName()
                + ", ID: " + Thread.currentThread().threadId());

        for (int i = 0; i < numHilos; i++) {
            MultihiloThreadDemo hilo = new MultihiloThreadDemo(); // estado NEW
            hilo.start(); // crea el hilo nuevo y ÉL llama a run() -> RUNNABLE
            // Experimento: con hilo.run() NO se crea hilo: todos los mensajes salen como "main".
        }
        // El main termina aquí, pero el programa sigue hasta que acaben los 8 hilos.
    }
}
