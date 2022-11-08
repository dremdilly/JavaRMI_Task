import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<BigInteger> queue;
    private final List<BigInteger> numbers;
    private long startTime = 0;
    boolean isProcessStarted = false;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
        this.numbers = new ArrayList<>();
    }

    @Override
    public BigInteger getMessage() throws RemoteException {
        if(!this.isProcessStarted) {
            startTime = System.nanoTime();
        }
        isProcessStarted = true;
        return this.queue.poll();
    }

    @Override
    public void sendMessage(BigInteger num) throws RemoteException {
        this.queue.add(num);
    }

    @Override
    public void storeCalculatedMessage(BigInteger num) throws RemoteException {
        System.out.println("Queue: " + queue);
        numbers.add(num);

        if (queue.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger number : numbers) sum = sum.add(number);

            System.out.println("Sum: " + sum);
            long endTime = System.nanoTime();
            System.out.println("Time: " + (endTime - startTime));
        }
    }
}