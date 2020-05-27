package assignment9;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Common {

    private static final String QUEUE_NAME = "Queue";
    private static final String TOPIC_NAME = "Topic";
    private static final String TOPIC_SUBSCRIBER_ID = "TopicSubscriber";
    private static final String TOPIC_SUBSCRIBER_NAME = "TopicSubscriber";
    private static final String CONNECTION_FACTORY = "ConnectionFactory";

    public static Context getContext() throws NamingException {
        Context context = new InitialContext();
        return context;
    }

    public static Connection createAndStartQueueConnection(Context context) throws NamingException, JMSException {
        Connection connection = createConnection(context);
        connection.start();
        return connection;
    }

    public static Connection createAndStartTopicConnection(Context context) throws NamingException, JMSException {
        Connection connection = createConnection(context);
        connection.setClientID(TOPIC_SUBSCRIBER_ID);
        connection.start();
        return connection;
    }

    public static Session createSession(Connection connection) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    public static MessageProducer createMessageProducer(Context context, Session session, boolean queue)
            throws NamingException, JMSException {
        Destination destination = getDestination(context, queue);
        MessageProducer messageProducer = session.createProducer(destination);
        return messageProducer;
    }

    public static MessageProducer createMessageProducer(Context context, Session session, Destination destination)
            throws NamingException, JMSException {
        MessageProducer messageProducer = session.createProducer(destination);
        return messageProducer;
    }

    public static MessageConsumer createQueueMessageConsumer(Context context, Session session)
            throws NamingException, JMSException {
        Destination destination = getDestination(context, true);
        return session.createConsumer(destination);
    }

    public static MessageConsumer createQueueMessageConsumer(Context context, Session session, Destination destination)
            throws NamingException, JMSException {
        return session.createConsumer(destination);
    }

    public static MessageConsumer createTopicSubscriber(Context context, Session session)
            throws NamingException, JMSException {
        Destination destination = getDestination(context, false);
        return session.createConsumer(destination);
    }

    public static TopicSubscriber createDurableTopicSubscriber(Context context, Session session)
            throws NamingException, JMSException {
        Topic topic = (Topic) getDestination(context, false);
        return session.createDurableSubscriber(topic, TOPIC_SUBSCRIBER_NAME);
    }

    public static Message createMessage(Session session, String content) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setText(content);
        return message;
    }

    private static Connection createConnection(Context context) throws NamingException, JMSException {
        ConnectionFactory factory = connectionFactory(context);
        Connection connection = factory.createConnection();
        return connection;
    }

    private static ConnectionFactory connectionFactory(Context context) throws NamingException {
        return (ConnectionFactory) context.lookup(Common.CONNECTION_FACTORY);
    }

    private static Destination getDestination(Context context, boolean queue) throws NamingException {
        Destination destination = queue //
                ? Common.getQueue(context) //
                : Common.getTopic(context);
        return destination;
    }

    public static Destination getDestinationByName(Context context, boolean queue, String name) throws NamingException {
        Destination destination = queue //
                ? (Queue) context.lookup(name)//
                : (Topic) context.lookup(name);
        return destination;
    }

    /**
     * get destination (queue)
     */
    private static Queue getQueue(Context context) throws NamingException {
        Queue queue = (Queue) context.lookup(Common.QUEUE_NAME);
        return queue;
    }

    /**
     * get destination (topic)
     */
    private static Topic getTopic(Context context) throws NamingException {
        Topic topic = (Topic) context.lookup(Common.TOPIC_NAME);
        return topic;
    }

}