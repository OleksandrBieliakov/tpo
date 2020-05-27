package assignment9.participants;

import assignment9.Common;
import assignment9.messages.*;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.Random;

public class Requester extends Participant {

    public Requester(int id) {
        super(id);
    }

    @Override
    public void run() {
        try {
            init();
            MessageProducer requestProducer = Common.createMessageProducer(getContext(), getSession(), getRequestsQueue());
            Destination destination = Common.getDestinationByName(getContext(), true, getId());
            MessageConsumer responseConsumer = Common.createQueueMessageConsumer(getContext(), getSession(), destination);
            int num1 = requestRandomNum(requestProducer, responseConsumer);
            int num2 = requestRandomNum(requestProducer, responseConsumer);
            String operation = getRandomOperation();
            double result = requestArithmeticCalculation(num1, num2, operation, requestProducer, responseConsumer);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        } finally {
            if (getConnection() != null) {
                try {
                    getConnection().close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getRandomOperation() {
        Random random = new Random();
        int index = random.nextInt(OPERATIONS.length);
        return OPERATIONS[index];
    }

    private int requestRandomNum(MessageProducer requestProducer, MessageConsumer responseConsumer) throws JMSException {
        RandomRequest request = new RandomRequest();
        request.setRequester(getId());
        sendMessage(request, requestProducer, getId());
        RandomResponse response = (RandomResponse) receiveMessage(responseConsumer, getId());
        return response.getNum();
    }

    private double requestArithmeticCalculation(int num1, int num2, String operation, MessageProducer requestProducer,
                                                MessageConsumer responseConsumer) throws JMSException {
        ArithmeticRequest request = new ArithmeticRequest();
        request.setRequester(getId());
        request.setNum1(num1);
        request.setNum2(num2);
        request.setOperation(operation);
        sendMessage(request, requestProducer, getId());
        ArithmeticResponse response = (ArithmeticResponse) receiveMessage(responseConsumer, getId());
        return response.getResult();
    }

}
