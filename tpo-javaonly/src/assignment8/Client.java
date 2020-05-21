package assignment8;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            IEcho echoRemoteObject = (IEcho) Naming.lookup(Common.ECHO_NAME);
            EchoRequest echoRequest = new EchoRequest("Hello");
            EchoResponse echoResponse = echoRemoteObject.echo(echoRequest);
            System.out.println(echoResponse.getMessage());

            IAdd addRemoteObject = (IAdd) Naming.lookup(Common.ADD_NAME);
            AddRequest addRequest = new AddRequest(new BigDecimal(1), new BigDecimal(2));
            AddResponse addResponse = addRemoteObject.add(addRequest);
            System.out.println(addResponse.getSum());
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

}
