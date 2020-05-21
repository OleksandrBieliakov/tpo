package assignment8;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(Common.RMI_PORT);
            EchoAddRemoteObject echoAddRemoteObject = new EchoAddRemoteObject();
            System.out.print("Server started");
            Naming.bind(Common.ECHO_NAME, echoAddRemoteObject);
            Naming.bind(Common.ADD_NAME, echoAddRemoteObject);
        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}
