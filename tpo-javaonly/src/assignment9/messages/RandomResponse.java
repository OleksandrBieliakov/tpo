package assignment9.messages;

import javax.jms.JMSException;

public class RandomResponse extends CustomMessage {

    public final static String NUM_NAME = "Result";

    public int getNum() throws JMSException {
        return this.getInt(NUM_NAME);
    }

    public void setNum(int value) throws JMSException {
        setInt(NUM_NAME, value);
    }

}
