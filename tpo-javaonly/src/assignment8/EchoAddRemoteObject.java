package assignment8;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class EchoAddRemoteObject extends UnicastRemoteObject implements IEcho, IAdd {

    protected EchoAddRemoteObject() throws RemoteException {
    }

    protected EchoAddRemoteObject(int port) throws RemoteException {
        super(port);
    }

    protected EchoAddRemoteObject(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public AddResponse add(AddRequest request) throws RemoteException {
        BigDecimal sum = request.getNum1();
        if (sum != null) {
            sum = sum.add(request.getNum2());
        } else {
            sum = request.getNum2();
        }
        return new AddResponse(sum);
    }

    @Override
    public EchoResponse echo(EchoRequest request) throws RemoteException {
        return new EchoResponse(request.getMessage());
    }

}
