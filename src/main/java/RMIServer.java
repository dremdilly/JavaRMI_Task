import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RMIServer {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        List<BigInteger> list = new ArrayList<>();
        try {
            System.setProperty(RMI_HOSTNAME, hostName);

            // Create service for RMI
            Service service = new ServiceImpl();

            BufferedReader reader = new BufferedReader(new FileReader("D:/University/Trimester 7/Distributing Computing/Assignments/JavaRMI_Task/src/main/resources/test.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] numbers = line.split(" ");
                Arrays.stream(numbers).forEach(strNum -> list.add(BigInteger.valueOf(Long.parseLong(strNum))));
                line = reader.readLine();
            }

            list.forEach(num -> {
                try {
                    service.sendMessage(num);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            // Init service
            String serviceName = "Service";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            System.out.println("Start " + serviceName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}