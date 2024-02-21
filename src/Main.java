
public class Main {
    /**
     *
     * 1 locks y 2 condiciones.
     * Utilizar signal.
     *
     */

        public static void main(String[] args) {

            Barberia barberia = new Barberia();

            // Invocar al barbero. Comienza duermiendo.
            Barbero barbero = new Barbero(barberia);
            Thread threadBarbero  = new Thread(barbero, "Barbero");
            threadBarbero.start();

            Thread[] clientes = new Thread[10];
            for (int i = 0; i < clientes.length ; i++) {
                try {
                    clientes[i] = new Thread(new Cliente(barberia), "Cliente(" + (i+1) + ")");
                    Thread.sleep((int)(Math.random()*(1000-2000+1) + 2000));
                    clientes[i].start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                threadBarbero.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
}