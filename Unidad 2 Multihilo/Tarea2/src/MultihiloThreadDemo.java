/**
 * @author Juan Gabriel Galarza Claros
 */
class MultihiloRunnableDemo implements Runnable {
    @Override
    public void run() {
        // Muestra el nombre y el ID del hilo en ejecución
        System.out.println("Hilo Runnable -> Nombre: " + Thread.currentThread().getName() + ", ID: " + Thread.currentThread().threadId());
    }
}
    class MultihiloRunnable {
    public static void main(String[] args) {
        int numHilos = 8;

        // Muestra el nombre y el ID del hilo main
        System.out.println("Hilo Principal (main) -> Nombre: TuNombre, ID: " + Thread.currentThread().threadId());

        // Crea un único objeto de la clase que implementa Runnable
        MultihiloRunnableDemo tarea = new MultihiloRunnableDemo();

        for (int i = 0; i < numHilos; i++) {
            // Crea un objeto Thread pasando el objeto Runnable y arráncalo con start()
            Thread hilo = new Thread(tarea);
            hilo.start();
        }
    }
}