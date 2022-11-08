import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for a service which will be accessible remotely
 */
public interface Service extends Remote {
    BigInteger getMessage() throws RemoteException ;
    void sendMessage(BigInteger num) throws RemoteException;
    void storeCalculatedMessage(BigInteger num) throws RemoteException;
}