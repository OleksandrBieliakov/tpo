package assignment9;

import assignment9.messages.RandomRequest;
import assignment9.participants.Requester;
import assignment9.participants.Service;
import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.JMSException;
import javax.jms.Message;

public class Main {

    private static final int NUMBER_OF_SERVICES = 5;
    private static final int NUMBER_OF_REQUESTERS = 10;

    public static void main(String[] args) {

        System.out.println("STARTING");
        for(int i = 1; i <= NUMBER_OF_SERVICES; i++) {
            new Thread(new Service(i)).start();
        }
        for(int i = 1; i <= NUMBER_OF_REQUESTERS; i++) {
            new Thread(new Requester(i)).start();
        }
        System.out.println("ALL CREATED");

    }

}
