package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.RequestHandler;
import util.Response;
import util.UserCommand;

import static util.AnswerFormatter.formatAnswer;

public class EnterValueController {
    private Stage enterStage;
    private boolean valid = false;
    @FXML private TextField pqmField;
    @FXML private Button submitBtn;

    @FXML private void initialize() {
        pqmField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty() || Integer.parseInt(newValue) >= 0) {
                    valid = true;
                    pqmField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid = false;
                    pqmField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid = false;
                pqmField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(!valid);
        }));
    }

    @FXML private void count() {
        Response response = RequestHandler.getInstance().send(new UserCommand("count_less_than_pqm", pqmField.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(formatAnswer(response));
        alert.setHeaderText(null);
        alert.showAndWait();
        enterStage.close();
        //TODO edit view
    }

    @FXML private void cancel() {
        enterStage.close();
    }

    public void setEnterStage(Stage enterStage) {
        this.enterStage = enterStage;
    }
}
