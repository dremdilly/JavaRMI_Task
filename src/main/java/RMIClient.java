import java.math.BigInteger;
import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            while (true){
                BigInteger num = service.getMessage();
                if(num == null) {
                    System.out.println("Not received");
                    break;
                }
                else {
                    System.out.println("Received: " + num);
                    service.storeCalculatedMessage(numberOfPrimesLess(num));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BigInteger numberOfPrimesLess(BigInteger num) {
        BigInteger count = BigInteger.ZERO;
        for (BigInteger i = BigInteger.ONE; i.compareTo(num) < 0; i = i.add(BigInteger.ONE)) {
            if (isPrime(i)) count = count.add(BigInteger.ONE);
        }
        return count;
    }

    public static boolean isPrime(BigInteger num) {
        for (BigInteger i = BigInteger.valueOf(2L); i.multiply(i).compareTo(num) <= 0; i = i.add(BigInteger.ONE)) {
            if (num.remainder(i).compareTo(BigInteger.ZERO) == 0) {
                return false;
                }
            }
        return true;
    }
}