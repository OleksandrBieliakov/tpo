package assignment9.messages;

import javax.jms.JMSException;

public class ArithmeticRequest extends CustomMessage {

    public final static String NUM_1_NAME = "Num1";
    public final static String NUM_2_NAME = "Num2";
    public final static String OPERATION_NAME = "Operation";

    public int getNum1() throws JMSException {
        return this.getInt(NUM_1_NAME);
    }

    public void setNum1(int value) throws JMSException {
        setInt(NUM_1_NAME, value);
    }

    public int getNum2() throws JMSException {
        return this.getInt(NUM_2_NAME);
    }

    public void setNum2(int value) throws JMSException {
        setInt(NUM_2_NAME, value);
    }

    public String getOperation() throws JMSException {
        return this.getString(OPERATION_NAME);
    }

    public void setOperation(String operation) throws JMSException {
        setString(OPERATION_NAME, operation);
    }

}
