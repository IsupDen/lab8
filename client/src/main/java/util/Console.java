package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private static Console instance;
    private Scanner scanner;
    private BufferedReader bufferedReader;
    private boolean exeStatus;

    public static Console getInstance() {
        if (instance == null) instance = new Console();
        return instance;
    }

    public void setScanner(Scanner scanner) {
        exeStatus = false;
        this.scanner = scanner;
    }

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void print(Object obj, boolean fieldsReading) {
        if (!exeStatus || !fieldsReading) print(obj);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public String read() throws IOException {
        try {
            return bufferedReader.readLine();
        } catch (NoSuchElementException e) {
            System.exit(0);
            return null;
        }
    }

    public boolean getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(boolean exeStatus) {
        this.exeStatus = exeStatus;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}
