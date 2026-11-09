public class Moto extends Thread {
    private static final int PROGRESO = 10;
    int id;

    public Moto(int id) {
    this.id = id;
    }
    @Override
    public void run() {
        try {
            for (int etapa = 0; etapa <= PROGRESO; etapa++) {
                Thread.sleep(500);
                int progreso = etapa + 1;
                System.out.println("Moto " + id + " ha avanzado al punto " + progreso + ".");
            }
            System.out.println("Moto " + id +  " ha completado su recorrido");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Moto " + id +  " se ha interrumpido");
        }
    }
}
