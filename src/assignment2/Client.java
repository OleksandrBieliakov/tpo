package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client {

    private static final int TIMES = 50;
    private static final int WAIT_MS = 200;

    private static final int MIN_LEN = 3;
    private static final int MODE_INDEX = 0;
    private static final int HOST_INDEX = 1;
    private static final int PORT_INDEX = 2;

    private static final String ECHO = "ECHO";
    private static final int ECHO_ARGS_LEN = 4;
    private static final int MESSAGE_INDEX = 3;

    private static final String ADD = "ADD";
    private static final int ADD_ARGS_LEN = 5;
    private static final int NUM1_INDEX = 3;
    private static final int NUM2_INDEX = 4;

    private static final String BYE = "BYE";

    private final AddCommand addCommand = new AddCommand();
    private final EchoCommand echoCommand = new EchoCommand();

    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) {
        try {
            sock = new Socket(host, port);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            close();
            System.exit(1);
        }
    }

    private void makeRequest(String req) {
        System.out.println("Request: " + req);
        out.println(req);
        String resp;
        try {
            resp = in.readLine();
            System.out.println("Response: " + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (sock != null) {
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void echo(String message) {
        makeRequest(ECHO + " " + message);
    }

    public void add(int num1, int num2) {
        makeRequest(ADD + " " + num1 + " " + num2);
    }

    public void bye() {
        makeRequest(BYE);
    }

    private static void printExpectedArgumentsMessage() {
        System.out.println("Provide arguments: \"ECHO <host> <port> <message>\" or \"ADD <host> <port> <num1> <num2>\".");
    }


    public interface Command<T> {
        void execute(T data);
    }

    public class AddCommand implements Command<List<Integer>> {
        @Override
        public void execute(List<Integer> data) {
            add(data.get(0), data.get(1));
        }
    }

    public AddCommand getAddCommand() {
        return addCommand;
    }

    public class EchoCommand implements Command<String> {
        @Override
        public void execute(String data) {
            echo(data);
        }
    }

    public EchoCommand getEchoCommand() {
        return echoCommand;
    }

    public void callCommand(Command command, Object data) {
        command.execute(data);
    }

    public static void main(String[] args) {
        int len = args.length;

        if (len >= MIN_LEN) {
            int port = -1;
            try {
                port = Integer.parseInt(args[PORT_INDEX]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                printExpectedArgumentsMessage();
            }

            if (port >= 0) {
                String mode = args[MODE_INDEX].toUpperCase();
                String host = args[HOST_INDEX];
                Client client = new Client(host, port);

                Command command = null;
                Object data = null;
                if (mode.equals(ECHO) && len == ECHO_ARGS_LEN) {
                    data = args[MESSAGE_INDEX];
                    command = client.getEchoCommand();
                } else if (mode.equals(ADD) && len == ADD_ARGS_LEN) {
                    try {
                        int num1 = Integer.parseInt(args[NUM1_INDEX]);
                        int num2 = Integer.parseInt(args[NUM2_INDEX]);
                        data = Arrays.asList(num1, num2);
                        command = client.getAddCommand();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        printExpectedArgumentsMessage();
                    }
                } else {
                    printExpectedArgumentsMessage();
                }

                if (command != null) {
                    for (int i = 0; i < TIMES; i++) {
                        client.callCommand(command, data);
                        try {
                            Thread.sleep(WAIT_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                client.bye();
                client.close();
            }
        } else {
            printExpectedArgumentsMessage();
        }
    }

}
