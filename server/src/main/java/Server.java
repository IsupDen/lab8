import collection.CollectionManager;
import database.DBConnector;
import database.DBCreator;
import database.DBWorker;
import util.*;

import java.io.IOException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;
import java.util.regex.Pattern;

import static util.ConsoleWorker.println;
import static util.ConsoleWorker.print;
import static util.TextFormat.errText;
import static util.TextFormat.successText;


public class Server {
    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(Server.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e);
        }

        try (Scanner scanner = new Scanner(System.in)) {
            DatagramSocket datagramSocket = getDatagramSocket(scanner);
            DBWorker dbWorker = connectToDB();
            if (dbWorker == null) return;
            Receiver receiver = new Receiver(dbWorker);
            ExecutorService requestThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 3);
            Runtime.getRuntime().addShutdownHook(new Thread(new ExitSaver()));
            Executor mainThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 3);
            println(getInformation(datagramSocket));
            while (true) {
                byte[] buf = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);
                RequestReceiver requestReceiver = new RequestReceiver(datagramSocket, packet, new Invoker(receiver), requestThreadPool);
                mainThreadPool.execute(requestReceiver);
            }
        } catch (IOException e) {
            println("Some problem's with network!");
        }
    }

    private static DatagramSocket getDatagramSocket(Scanner scanner) {
        while (true) {
            try {
                return new DatagramSocket(getPort(scanner));
            } catch (SocketException e) {
                println(errText("Socket could not bind to the specified local port!"));
            }
        }
    }

    private static int getPort(Scanner scanner) {
        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("\\d{1,5}");
        try {
            do {
                print("Please, enter remote host port: ");

                arg = scanner.nextLine();

            } while (!(remoteHostPortPattern.matcher(arg).find() && (Integer.parseInt(arg.trim()) < 65536)));

            return Integer.parseInt(arg);
        } catch (NoSuchElementException e) {
            System.exit(0);
            return 0;
        }
    }

    private static DBWorker connectToDB() {

        Connection db;
        try {
            db = new DBConnector().connect();
            if (db == null) return null;
        } catch (SQLException e) {
            println("Connection establishing problems");
            return null;
        }
        DBCreator dbCreator = new DBCreator(db);
        try {
            dbCreator.create();
        } catch (SQLException e) {
            println("Something wrong with db!");
            return null;
        }
        try {
            return new DBWorker(db);
        } catch (NoSuchAlgorithmException e) {
            println("Hashing algorithm not found!");
            return null;
        }
    }

    public static String getInformation(DatagramSocket datagramSocket) {
        try {
            return successText("\nSERVER STATUS:\n") +
                    successText("--------------------------------------------------\n") +
                    "Server address: " + successText(String.valueOf(InetAddress.getLocalHost())) + "\n" +
                    "Server port:    " + successText(String.valueOf(datagramSocket.getLocalPort())) + "\n" +
                    successText("--------------------------------------------------\n");
        } catch (UnknownHostException ignored) {
            return null;
        }
    }
}
