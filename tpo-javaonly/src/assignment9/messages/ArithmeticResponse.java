package assignment9.messages;

import javax.jms.JMSException;

public class ArithmeticResponse extends CustomMessage {

    public final static String RESULT_NAME = "Result";

    public double getResult() throws JMSException {
        return this.getDouble(RESULT_NAME);
    }

    public void setResult(double value) throws JMSException {
        setDouble(RESULT_NAME, value);
    }

}
