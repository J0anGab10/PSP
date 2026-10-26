/**
 * @author Juan Gabriel Galarza claros
 */

class MiTarea implements Runnable{
    // Declaración de atributos
    private String[] elementos;
    private long pausa;

    // Creamos el contructor
    public MiTarea(String[] elementos, long pausa) {
        this.elementos = elementos;
        this.pausa = pausa;
    }

    // Metodo Runnable
    @Override
    public void run() {
        // Muestra el nombre
        String nombre = Thread.currentThread().getName();

            // Recorresr los elementos del array
            for (String elemento : elementos) {
                System.out.println(nombre + "; " + elemento);
                try {
                    Thread.sleep(pausa);
                } catch (InterruptedException e) {
                    System.out.println(nombre + " ha sido interrumpido.");
                    return;
                }
            }
    }
}

public class EstasdosHilosDemo {
    // Hio principal
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Nombre y apellidos: Juan Gabriel Galarza Claros");

        MiTarea tareaNumeros = new MiTarea(new String[]{"1","2","3","4","5"}, 300);
        MiTarea tareaLetras = new MiTarea(new String[]{"A","B","C","D","E"}, 500);

        Thread hiloNumeros = new Thread(tareaNumeros, "Numeros");
        Thread hiloLetras = new Thread(tareaLetras, "Letras");

        // Momento1: antes de start
        System.out.println("1. Antes de Start(): Numeros = " + hiloNumeros.getState()
                + " Letras = " + hiloLetras.getState());

        // Arrancamos los hilos
        hiloNumeros.start();
        hiloLetras.start();
        // Momento2: despues de start
        System.out.println("2. Después de Start(): Numeros = " + hiloNumeros.getState()
                + " Letras = " + hiloLetras.getState());

        // Ponemos a dormir el hilo principal (main)
        Thread.sleep(100);
        // Momento3: despues de dormir 100 ms el main
        System.out.println("1. Antes de Start(): Numeros = " + hiloNumeros.getState()
                + " Letras = " + hiloLetras.getState());

        // Esperamos a que termine hilonumeros
        hiloNumeros.join();
        // Momento4: justo despues que el main espera al hiloNumeros
        System.out.println("1. Antes de Start(): Numeros = " + hiloNumeros.getState()
                + " Letras = " + hiloLetras.getState());

        // Esperamso a que termine hiloLetras
        hiloLetras.join();
        // Momento5: justo despues de que el main espera al hiloLetras
        System.out.println("1. Antes de Start(): Numeros = " + hiloNumeros.getState()
                + " Letras = " + hiloLetras.getState());

    }
}
