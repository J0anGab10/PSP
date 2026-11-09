/*
 * ==========================================================
 * TAREA 3 - Ver los ESTADOS de un hilo con getState()
 *
 * IDEA: dos hilos (Numeros y Letras) muestran cosas con pausas, y el main
 * va mirando en qué estado está cada uno en 5 momentos.
 *
 * ESTADOS QUE VERÁS:
 * NEW            -> creado, pero aún sin start()
 * RUNNABLE       -> ejecutándose o listo para ejecutarse
 * TIMED_WAITING  -> dormido en sleep() (espera con tiempo límite)
 * TERMINATED     -> run() ya terminó
 *
 * POR QUÉ CASI SIEMPRE SALE TIMED_WAITING: imprimir tarda microsegundos,
 * pero cada pausa dura 300 o 500 ms. El hilo pasa casi toda su vida dormido.
 *
 * join(): el main se para hasta que ese hilo termine.
 * ==========================================================
 *
 * @author Juan Gabriel Galarza Claros
 */

class MiTarea implements Runnable {
    private final String[] elementos; // lo que mostrará el hilo, uno a uno
    private final long pausa;         // ms de espera después de cada elemento

    public MiTarea(String[] elementos, long pausa) {
        this.elementos = elementos;
        this.pausa = pausa;
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        for (String elemento : elementos) { // Por cada elemeno del Array elementos...
            System.out.println(nombre + ": " + elemento);
            try {
                Thread.sleep(pausa); // mientras duerme, su estado es TIMED_WAITING
            } catch (InterruptedException e) {
                // Al capturar la excepción la marca de interrupción se borra: la reactivamos.
                Thread.currentThread().interrupt();
                System.out.println(nombre + " ha sido interrumpido.");
                return;
            }
        }
    }
}

// El nombre de la clase public debe coincidir EXACTAMENTE con el del archivo.
public class EstadosHilosDemo {

    // Metodo auxiliar para no repetir el mismo println cinco veces.
    private static void mostrar(String momento, Thread a, Thread b) {
        System.out.println(momento + " -> " + a.getName() + " = " + a.getState()
                + " | " + b.getName() + " = " + b.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        // new Thread(tarea, nombre): la tarea (Runnable) y el nombre del hilo.
        Thread numeros = new Thread(new MiTarea(new String[]{"1", "2", "3", "4", "5"}, 300), "Numeros");
        Thread letras  = new Thread(new MiTarea(new String[]{"A", "B", "C", "D", "E"}, 500), "Letras");

        mostrar("1. Antes de start()", numeros, letras);    // NEW | NEW

        numeros.start();
        letras.start();
        mostrar("2. Justo tras start()", numeros, letras);  // normalmente RUNNABLE (puede variar)

        Thread.sleep(100);
        mostrar("3. Tras 100 ms", numeros, letras);         // TIMED_WAITING | TIMED_WAITING

        numeros.join(); // main espera a que Numeros termine (5 x 300 = 1500 ms)
        mostrar("4. Tras numeros.join()", numeros, letras); // TERMINATED | TIMED_WAITING

        letras.join();  // main espera a que Letras termine (5 x 500 = 2500 ms)
        mostrar("5. Tras letras.join()", numeros, letras);  // TERMINATED | TERMINATED
    }
}
