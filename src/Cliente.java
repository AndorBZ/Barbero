
public class Cliente implements Runnable{
    private Barberia barberia;
    private String nombre;

    public Cliente(Barberia barberia) {
        this.barberia = barberia;
    }

    public String getNombre() {
        return nombre;
    }

    public void marcharse() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        this.nombre = Thread.currentThread().getName();
        barberia.entraCliente(this);
        if (Thread.currentThread().isInterrupted()) {
            System.out.println(this.nombre + " no tiene dónde sentarse y se marcha");
        }
    }
}
