package assignment9.messages;

import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.JMSException;

abstract public class CustomMessage extends ActiveMQMapMessage {
    public final static String CUSTOM_TYPE = "CustomType";
    public final static String REQUESTER_NAME = "Requester";

    public CustomMessage() {
        super();
        try {
            setString(CUSTOM_TYPE, this.getClass().getSimpleName());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getRequester() throws JMSException {
        return this.getString(REQUESTER_NAME);
    }

    public void setRequester(String requester) throws JMSException {
        setString(REQUESTER_NAME, requester);
    }

}
