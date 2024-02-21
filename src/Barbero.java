public class Barbero implements Runnable{
    private Barberia barberia;

    public Barbero(Barberia barberia) {
        this.barberia = barberia;
    }

    @Override
    public void run() {
        while (true) {
            try {
                barberia.atenderCliente();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
