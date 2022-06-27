package util;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.regex.Pattern;

import static util.TextFormat.*;

public class SessionWorker {
    private final Console console;

    public SessionWorker(Console console) {
        this.console = console;
    }

    public Session getSession() throws IOException {
        console.println(helpText("\nAuthorization(Registration):"));
        boolean sessionStatus = getSessionStatus();
        return sessionStatus ? new Session(getUsername(), getUserPassword(), TypeOfSession.Login)
                : new Session(getUsername(), getUserPassword(), TypeOfSession.Register);
    }
    private boolean getSessionStatus() throws IOException{
        String answer;

        do {
            System.out.print("Do you register or login? [r/l]: ");
            answer = console.read();
        } while (answer == null || !answer.equals("r") && !answer.equals("l"));

        return answer.equals("l");
    }

    private String getUsername() throws IOException {

        String username;
        Pattern usernamePattern = Pattern.compile("^\\s*\\b(\\w+)\\b\\s*");

        while (true) {
            console.print("Please, enter username!: ");
            username = console.read();
            if (username != null && usernamePattern.matcher(username).find()) break;
            console.println(errText("Username should be not empty string of letters and digits!"));
        }

        return username.trim();
    }

    private String getUserPassword() throws IOException {
        if (System.console() == null) {
            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*([\\d\\w]*)\\s*");
            while (true) {
                console.print("Please, enter password!: ");
                password = console.read();
                if (password != null && passwordPattern.matcher(password).find()) break;
                console.println(errText("Password should be string of letters and digits!"));
            }
            return password.trim();
        } else {
            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*([\\d\\w]*)\\s*");
            while (true) {
                console.print("Please, enter password!:");
                password = new String(System.console().readPassword());
                if (passwordPattern.matcher(password).find()) break;
                console.println(errText("Password should be string of letters and digits!"));
            }
            return password.trim();
        }
    }
}
