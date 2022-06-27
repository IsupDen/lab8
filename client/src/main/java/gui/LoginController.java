package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static util.AnswerFormatter.formatAnswer;
import static util.TextFormat.successText;

public class LoginController {

    private App app;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label wrongLogin;
    @FXML private Button loginBtn;
    @FXML private Button registerBtn;
    private List<Boolean> valid = Arrays.asList(new Boolean[2]);

    @FXML private void initialize() {
        username.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.isBlank()) {
                valid.set(0, true);
                username.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            } else {
                valid.set(0, false);
                username.getStyleClass().add("wrong-field");
            }
            loginBtn.setDisable(valid.contains(false) || valid.contains(null));
            registerBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        password.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.isBlank()) {
                valid.set(1, true);
                password.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            } else {
                valid.set(1, false);
                password.getStyleClass().add("wrong-field");
            }
            loginBtn.setDisable(valid.contains(false) || valid.contains(null));
            registerBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
    }

    @FXML
    private void login() {
        Session session = new Session(username.getText(), password.getText(), TypeOfSession.Login);
        String sessionStatus = formatAnswer(RequestHandler.getInstance().login(session));

        if (!sessionStatus.equals(successText("Action processed successful!"))) {
            username.clear();
            password.clear();
            wrongLogin.setOpacity(1);
            return;
        }
        app.switchToMainPanel();

    }

    @FXML
    private void register() {
        Session session = new Session(username.getText(), password.getText(), TypeOfSession.Register);
        String sessionStatus = formatAnswer(RequestHandler.getInstance().register(session));

        if (!sessionStatus.equals(successText("Action processed successful!"))) {
            username.clear();
            password.clear();
            wrongLogin.setOpacity(1);
            return;
        }
        app.switchToMainPanel();
    }

    public void setApp(App app) {
        this.app = app;
    }

}
