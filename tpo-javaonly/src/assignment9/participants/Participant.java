package assignment9.participants;

import assignment9.Common;
import assignment9.messages.*;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

import static assignment9.messages.RandomResponse.NUM_NAME;

public abstract class Participant implements Runnable {

    static final String REQUESTS_QUEUE = "Requests";
    static final String[] OPERATIONS = {"+", "-", "*", "/"};

    private String id;

    private Context context;
    private Connection connection;
    private Session session;
    private Destination requestsQueue;

    static Logger logger = Logger.getLogger(Participant.class);

    public Participant(int id) {
        this.id = this.getClass().getSimpleName() + id;
    }

    public void init() throws NamingException, JMSException {
        context = Common.getContext();
        connection = Common.createAndStartQueueConnection(context);
        session = Common.createSession(connection);
        requestsQueue = Common.getDestinationByName(context, true, REQUESTS_QUEUE);
    }

    public Context getContext() {
        return context;
    }

    public Connection getConnection() {
        return connection;
    }

    public Session getSession() {
        return session;
    }

    public Destination getRequestsQueue() {
        return requestsQueue;
    }

    public String getId() {
        return id;
    }

    void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(CustomMessage message, MessageProducer producer, String idSender) throws JMSException {
        producer.send(message);

        if (message instanceof RandomRequest) {
            logger.info(idSender.toUpperCase() + ": RandomRequest sent");
        } else if (message instanceof ArithmeticRequest) {
            logger.info(idSender.toUpperCase() + ": ArithmeticRequest sent (" +
                    ((ArithmeticRequest) message).getNum1() +
                    ((ArithmeticRequest) message).getOperation() +
                    ((ArithmeticRequest) message).getNum2() + ")");
        } else if (message instanceof RandomResponse) {
            logger.info(idSender.toUpperCase() + ": RandomResponse sent (" +
                    ((RandomResponse) message).getNum() + ")");
        } else if (message instanceof ArithmeticResponse) {
            logger.info(idSender.toUpperCase() + ": ArithmeticResponse sent (" +
                    ((ArithmeticResponse) message).getResult() + ")");
        }
    }

    CustomMessage receiveMessage(MessageConsumer consumer, String idReceiver) throws JMSException {
        Message message = consumer.receive();
        ActiveMQMapMessage mq = (ActiveMQMapMessage) message;
        String customType = mq.getString(CustomMessage.CUSTOM_TYPE);
        if (customType.equals(RandomRequest.class.getSimpleName())) {
            RandomRequest cm = new RandomRequest();
            cm.setRequester(mq.getString(CustomMessage.REQUESTER_NAME));
            logger.info(idReceiver.toUpperCase() + ": RandomRequest received");
            return cm;
        } else if (customType.equals(RandomResponse.class.getSimpleName())) {
            RandomResponse cm = new RandomResponse();
            cm.setRequester(mq.getString(CustomMessage.REQUESTER_NAME));
            cm.setNum(mq.getInt(NUM_NAME));
            logger.info(idReceiver.toUpperCase() + ": RandomResponse received (" + cm.getNum() + ")");
            return cm;
        } else if (customType.equals(ArithmeticRequest.class.getSimpleName())) {
            ArithmeticRequest cm = new ArithmeticRequest();
            cm.setRequester(mq.getString(CustomMessage.REQUESTER_NAME));
            cm.setNum1(mq.getInt(ArithmeticRequest.NUM_1_NAME));
            cm.setNum2(mq.getInt(ArithmeticRequest.NUM_2_NAME));
            cm.setOperation(mq.getString(ArithmeticRequest.OPERATION_NAME));
            logger.info(idReceiver.toUpperCase() + ": ArithmeticRequest received (" + cm.getNum1() + cm.getOperation() + cm.getNum2() + ")");
            return cm;
        } else if (customType.equals(ArithmeticResponse.class.getSimpleName())) {
            ArithmeticResponse cm = new ArithmeticResponse();
            cm.setRequester(mq.getString(CustomMessage.REQUESTER_NAME));
            cm.setResult(mq.getDouble(NUM_NAME));
            logger.info(idReceiver.toUpperCase() + ": ArithmeticResponse received (" + cm.getResult() + ")");
            return cm;
        } else {
            throw new IllegalArgumentException("Unsupported " + customType);
        }
    }

}
