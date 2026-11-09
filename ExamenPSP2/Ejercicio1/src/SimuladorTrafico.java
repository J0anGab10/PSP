public class SimuladorTrafico {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Autor: Juan Gabriel Galarza Claros");

        Thread coche1 = new Thread(new Coche(1));
        System.out.println("Estado antes del start = " + coche1.getState());

        Thread moto1 = new Thread(new Moto(1));
        System.out.println("Antes del start = " + moto1.getState());

        Thread coche2 = new Thread(new Coche(2));
        System.out.println("Antes del start = " + coche2.getState());

        Thread moto2 = new Thread(new Moto(2));
        System.out.println("Antes del start = " + moto2.getState());

        moto1.start();
        coche1.start();
        moto2.start();
        coche2.start();

        System.out.println("Estado después de terminar = " + moto1.getState());
        System.out.println("Estado después de terminar = " + coche1.getState());
        System.out.println("Estado después de terminar = " + moto2.getState());
        System.out.println("Estado después de terminar = " + coche2.getState());
    }
}
