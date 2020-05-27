package assignment9.participants;

import assignment9.Common;
import assignment9.messages.*;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.Random;

public class Service extends Participant {

    private static final int RANDOM_LIMIT = 1000;
    private static final int WAIT_SECONDS_FROM = 3;
    private static final int WAIT_SECONDS_TO = 5;

    public Service(int id) {
        super(id);
    }

    @Override
    public void run() {
        try {
            init();
            MessageConsumer requestsConsumer = Common.createQueueMessageConsumer(getContext(), getSession(), getRequestsQueue());
            while (true) {
                serviceRequest(requestsConsumer);
            }
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

    private int generateRandomNum() {
        Random random = new Random();
        return random.nextInt(RANDOM_LIMIT) + 1; // [1, RANDOM_LIMIT)
    }

    private void serviceRequest(MessageConsumer requestsConsumer) throws JMSException, NamingException {
        CustomMessage request = receiveMessage(requestsConsumer, getId());
        CustomMessage response = null;
        String requester = request.getRequester();
        if (request instanceof RandomRequest) {
            response = serviceRandomRequest((RandomRequest) request);
        } else if (request instanceof ArithmeticRequest) {
            response = serviceArithmeticRequest((ArithmeticRequest) request);
        }
        sleep(); // processing should take a random amount of time between 3 and 5 seconds

        Destination destination = Common.getDestinationByName(getContext(), true, requester);
        MessageProducer responseProducer = Common.createMessageProducer(getContext(), getSession(), destination);
        sendMessage(response, responseProducer, getId());
    }

    private RandomResponse serviceRandomRequest(RandomRequest request) throws JMSException {
        RandomResponse response = new RandomResponse();
        int num = generateRandomNum();
        response.setNum(num);
        return response;
    }

    private ArithmeticResponse serviceArithmeticRequest(ArithmeticRequest request) throws JMSException {
        int num1 = request.getNum1();
        int num2 = request.getNum2();
        double result = 0;
        switch (request.getOperation()) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = (double) num1 / num2;
        }
        ArithmeticResponse response = new ArithmeticResponse();
        response.setResult(result);
        return response;
    }

    private void sleep() {
        Random random = new Random();
        int seconds = WAIT_SECONDS_FROM + random.nextInt(WAIT_SECONDS_TO - WAIT_SECONDS_FROM);
        sleepSeconds(seconds);
    }

}
