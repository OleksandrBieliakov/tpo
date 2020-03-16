package assignment2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public class Server {

    private static final int ARGS_LEN = 2;
    private static final int HOST_INDEX = 0;
    private static final int PORT_INDEX = 1;

    private static final int COMMAND_INDEX = 0;
    private static final String NOT_SUPPORTED = "NOT SUPPORTED COMMAND";

    private static final String ECHO = "ECHO";
    private static final int MESS_INDEX = 1;
    private static final int ECHO_LEN = 2;

    private static final String ADD = "ADD";
    private static final int NUM1_INDEX = 1;
    private static final int NUM2_INDEX = 2;
    private static final int ADD_LEN = 3;

    private static final String BYE = "BYE";
    private static final int BYE_LEN = 1;

    private ServerSocketChannel ssc = null;
    private Selector selector = null;

    private static Charset charset = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;

    private static Pattern reqPatt = Pattern.compile(" +");

    private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
    private StringBuffer reqString = new StringBuffer();

    private StringBuffer remsg = new StringBuffer();

    public Server(String host, int port) {
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(host, port));
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started and ready for handling requests");
            serviceConnections();
        } catch (Exception exc) {
            exc.printStackTrace();
            closeServer();
            System.out.println("Could not start the server");
        }
    }

    private void closeServer() {
        try {
            if (selector != null) {
                selector.close();
            }
            if (ssc != null) {
                ssc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serviceConnections() {
        boolean serverIsRunning = true;

        while (serverIsRunning) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (key.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        serviceRequest(sc);
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private void serviceRequest(SocketChannel sc) {
        if (sc == null || !sc.isOpen()) {
            return;
        }

        reqString.setLength(0);
        bbuf.clear();
        try {
            readLoop:
            while (true) {               // we continue until we reach the EOL
                int n = sc.read(bbuf);
                if (n > 0) {
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    while (cbuf.hasRemaining()) {
                        char c = cbuf.get();
                        if (c == '\r' || c == '\n') break readLoop;
                        reqString.append(c);
                    }
                }
            }

            System.out.println("Request: " + reqString);
            String[] req = reqPatt.split(reqString);
            String cmd = req[COMMAND_INDEX];
            String message;
            if (req.length == BYE_LEN && cmd.equals(BYE)) {
                message = BYE;
            } else if (req.length == ADD_LEN && cmd.equals(ADD)) {
                try {
                    int num1 = Integer.parseInt(req[NUM1_INDEX]);
                    int num2 = Integer.parseInt(req[NUM2_INDEX]);
                    message = Integer.toString(num1 + num2);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    message = NOT_SUPPORTED;
                }
            } else if (req.length == ECHO_LEN && cmd.equals(ECHO)) {
                message = req[MESS_INDEX];
            } else {
                message = NOT_SUPPORTED;
            }
            writeResp(sc, message);

            if (cmd.equals(BYE)) {
                closeChannel(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeChannel(SocketChannel sc) {
        try {
            if (sc != null) {
                sc.close();
                sc.socket().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResp(SocketChannel sc, String message) {
        remsg.setLength(0);
        remsg.append(message);
        remsg.append('\n');
        ByteBuffer buf = charset.encode(CharBuffer.wrap(remsg));
        try {
            sc.write(buf);
            System.out.println("Response: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printExpectedArgumentsMessage() {
        System.out.println("Provide arguments: \"<host> <port>\"");
    }

    public static void main(String[] args) {
        int len = args.length;

        if (len == ARGS_LEN) {
            try {
                int port = Integer.parseInt(args[PORT_INDEX]);
                String host = args[HOST_INDEX];
                new Server(host, port);
            } catch (NumberFormatException e) {
                printExpectedArgumentsMessage();
            }
        } else {
            printExpectedArgumentsMessage();
        }
    }

}
