package assignment1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class FileReaderWriter {

    private static final int WAIT_TIME_MS = 100;

    private static final int LAST_INDEX = 0;
    private static final int WRITER_LAST = 1;
    private static final int READER_LAST = 0;

    private static final int IS_FINISHED_INDEX = 1;
    private static final int UNFINISHED = 0;
    private static final int FINISHED = 1;

    private static final int NUMBERS_FROM_INDEX = 2;
    private static final int NUMBERS = 2;

    private static final int RANDOM_TO = 10;
    private final Random random = new Random();

    private static final int INTS_IN_FILE = 2 + NUMBERS;
    private static final int BYTES_IN_INT = 4;

    private FileChannel channel;
    private MappedByteBuffer buffer;
    private IntBuffer intBuffer;

    private boolean init(String path) {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            channel = file.getChannel();
            buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, INTS_IN_FILE * BYTES_IN_INT);
            intBuffer = buffer.asIntBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return false;
        }
        return true;
    }

    private void close() {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCycle() {

        intBuffer.put(LAST_INDEX, WRITER_LAST);

        int number;
        for (int n = NUMBERS, i = NUMBERS_FROM_INDEX; n > 0; n--, i++) {
            number = random.nextInt(RANDOM_TO);
            intBuffer.put(i, number);
            System.out.print(number + " ");
        }
        System.out.println();

        buffer.force();
    }

    private void waitForFileUpdate(int mode) {
        while (intBuffer.get(LAST_INDEX) == mode && intBuffer.get(IS_FINISHED_INDEX) != FINISHED) {
            try {
                Thread.sleep(WAIT_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(String path, int times) {
        boolean isSuccessfulInit = init(path);
        if (!isSuccessfulInit) return;

        if (times > 0) {
            intBuffer.put(IS_FINISHED_INDEX, UNFINISHED);
            buffer.force();

            int counter = 0;

            writeCycle();

            while (++counter < times) {
                waitForFileUpdate(WRITER_LAST);
                writeCycle();
            }

            waitForFileUpdate(WRITER_LAST);
        }

        intBuffer.put(IS_FINISHED_INDEX, FINISHED);
        buffer.force();

        close();
    }

    private void calculateSum() {
        if (intBuffer.limit() == 2) return;
        int number;
        int sum = 0;

        for (int i = 0; i < NUMBERS; ++i) {
            number = intBuffer.get(NUMBERS_FROM_INDEX + i);
            sum += number;
            System.out.print(number + " ");
        }
        System.out.println(" : " + sum);
    }

    private void read(String path) {
        init(path);
        if (intBuffer == null) return;

        while (true) {
            waitForFileUpdate(READER_LAST);
            if (intBuffer.get(IS_FINISHED_INDEX) == FINISHED) break;
            calculateSum();
            intBuffer.put(LAST_INDEX, READER_LAST);
            buffer.force();
        }

        close();
    }

    private static void printExpectedArgumentsMessage() {
        System.out.println("Provide arguments: \"READER <file path>\" or \"WRITER <times> <file path>\".");
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            printExpectedArgumentsMessage();
        } else {
            String mode = args[0];
            if (mode.equals("READER") && args.length == 2) {
                String path = args[1];
                System.out.println(mode);
                new FileReaderWriter().read(path);
            } else if (mode.equals("WRITER") && args.length == 3) {
                try {
                    int times = Integer.parseInt(args[1]);
                    if (times >= 0) {
                        String path = args[2];
                        System.out.println(mode + " (" + times + " times)");
                        new FileReaderWriter().write(path, times);
                    } else {
                        System.out.println("Number of times to write must be a positive integer.");
                    }
                } catch (Exception e) {
                    printExpectedArgumentsMessage();
                }
            } else {
                printExpectedArgumentsMessage();
            }
        }

    }

}
