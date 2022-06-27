package util;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleWorker {

    private final Scanner scanner;
    final Logger logger = Logger.getLogger(ConsoleWorker.class.getCanonicalName());

    public ConsoleWorker(Scanner scanner) {this.scanner = scanner;}

    public static void print(Object obj) {System.out.print(obj);}
    public static void println(Object obj) {System.out.println(obj);}

    public String read() {
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            logger.info("Server was turned off!");
            System.exit(0);
            return null;
        }
    }
}
