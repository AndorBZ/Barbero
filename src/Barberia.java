import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia {

    private final Lock lock;
    private final Condition conditionBarbero;
    private final Condition conditionCliente;
    private Queue<Cliente> sillas;
    private final int sillasDisponibles = 5;

    public Barberia() {
        sillas = new LinkedList<>();
        lock = new ReentrantLock();
        conditionBarbero = lock.newCondition();
        conditionCliente = lock.newCondition();
    }

    public void entraCliente(Cliente cliente) {
        lock.lock();
        try {
            if (sillas.isEmpty()) {
                conditionBarbero.signal();
            }
            if (sillas.size() <= sillasDisponibles) {
                System.out.println("Entra " + cliente.getNombre() + " y se sienta a esperar...");
                sillas.add(cliente);
                conditionCliente.await();
            } else {
                cliente.marcharse();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void atenderCliente() throws InterruptedException {
        lock.lock();
        try {
            if (!sillas.isEmpty()) {
                Cliente cliente = sillas.element();
                System.out.println("El Barbero atiende a " + cliente.getNombre());
                conditionCliente.signal();
                lock.unlock();
                Thread.sleep((int)(Math.random()*(5000-8000+1)+8000));
                lock.lock();
                System.out.println("El Barbero ha terminado con " + cliente.getNombre());
                sillas.poll();
            } else {
                System.out.println("El Barbero se sienta a dormir...");
                conditionBarbero.await();
                System.out.println("El Barbero ha despertado");
            }
        } finally {
            lock.unlock();
        }
    }

}
