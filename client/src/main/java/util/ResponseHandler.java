package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

import static util.AnswerFormatter.formatAnswer;
import static util.TextFormat.errText;

public class ResponseHandler {
    public static Response receive(ByteBuffer buffer) {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return (Response) inObj.readObject();
        } catch (ClassNotFoundException e) {
            //return errText("Server version is unsupported!");
        } catch (InvalidClassException e) {
            //return errText("Your version is outdated!");
        } catch (IOException e) {
            //return errText("Response is broken! Try again!");
        }
        return null;
    }

    public static String receive(String errorInformation) {
        return errorInformation;
    }
}
