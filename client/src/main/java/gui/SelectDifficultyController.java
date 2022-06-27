package gui;

import data.Difficulty;
import data.LabWork;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.RequestHandler;
import util.Session;
import util.UserCommand;

public class SelectDifficultyController {
    private Stage selectStage;
    private TableView<LabWork> table;
    @FXML private ChoiceBox<String> difficultyChoiceBox;

    @FXML private void initialize() {
        difficultyChoiceBox.setItems(FXCollections.observableArrayList(
                Difficulty.INSANE.getStringValue(),
                Difficulty.NORMAL.getStringValue(),
                Difficulty.TERRIBLE.getStringValue(),
                ""
        ));
    }

    @FXML private void delete() {
        String difficulty = difficultyChoiceBox.getValue();
        String user = RequestHandler.getInstance().getSession().getName();
        RequestHandler.getInstance().send(new UserCommand("remove_all_by_difficulty", difficulty));
        table.getItems().removeIf(labWork -> {
            if (labWork.getDifficulty() == null) return difficulty.equals("") && RequestHandler.getInstance().getSession().getName().equals(user);
            return labWork.getDifficulty().getStringValue().equals(difficulty) && RequestHandler.getInstance().getSession().getName().equals(user);
        });
        selectStage.close();
    }

    @FXML private void cancel() {
        selectStage.close();
    }


    public void setSelectStage(Stage selectStage) {
        this.selectStage = selectStage;
    }

    public void setTable(TableView<LabWork> table) {
        this.table = table;
    }
}
