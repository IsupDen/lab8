package gui;

import data.*;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddController {
    private Boolean checkMax;
    private Stage addStage;
    private List<Boolean> valid = Arrays.asList(new Boolean[10]);
    @FXML private TextField nameField;
    @FXML private TextField coordinateXField;
    @FXML private TextField coordinateYField;
    @FXML private TextField minimalPointField;
    @FXML private TextField personalQualitiesMinimumField;
    @FXML private ChoiceBox<String> difficultyChoiceBox;
    @FXML private TextField authorNameField;
    @FXML private TextField authorHeightField;
    @FXML private ChoiceBox<String> authorEyeColorChoiceBox;
    @FXML private ChoiceBox<String> authorHairColorChoiceBox;
    @FXML private ChoiceBox<String> authorNationalityChoiceBox;
    @FXML private Button submitBtn;

    @FXML private void initialize() {
        difficultyChoiceBox.setItems(FXCollections.observableArrayList(
                Difficulty.INSANE.getStringValue(),
                Difficulty.NORMAL.getStringValue(),
                Difficulty.TERRIBLE.getStringValue(),
                ""
        ));
        authorEyeColorChoiceBox.setItems(FXCollections.observableArrayList(
                EyeColor.BLUE.getStringValue(),
                EyeColor.BROWN.getStringValue(),
                EyeColor.WHITE.getStringValue(),
                EyeColor.ORANGE.getStringValue(),
                EyeColor.GREEN.getStringValue()
        ));
        authorHairColorChoiceBox.setItems(FXCollections.observableArrayList(
                HairColor.BLUE.getStringValue(),
                HairColor.BLACK.getStringValue(),
                HairColor.WHITE.getStringValue(),
                HairColor.ORANGE.getStringValue(),
                HairColor.BROWN.getStringValue()
        ));
        authorNationalityChoiceBox.setItems(FXCollections.observableArrayList(
                Country.GERMANY.getStringValue(),
                Country.ITALY.getStringValue(),
                Country.VATICAN.getStringValue(),
                Country.NORTH_KOREA.getStringValue()
        ));
        validateInit();
    }

    @FXML private void add() {
        LabWork labWork = new LabWork(
                0,
                nameField.getText(),
                new Coordinates(Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText())),
                null,
                Double.parseDouble(minimalPointField.getText()),
                personalQualitiesMinimumField.getText().equals("") ? null: Integer.parseInt(personalQualitiesMinimumField.getText()),
                difficultyChoiceBox.getValue().equals("") ? null: Difficulty.valueOf(difficultyChoiceBox.getValue()),
                new Person(authorNameField.getText(),
                        Double.parseDouble(authorHeightField.getText()),
                        HairColor.valueOf(authorHairColorChoiceBox.getValue()),
                        EyeColor.valueOf(authorEyeColorChoiceBox.getValue()),
                        Country.valueOf(authorNationalityChoiceBox.getValue())));
        if (checkMax) {
            Response response = RequestHandler.getInstance().send(new UserCommand("add_if_max", "").addLabWork(labWork));
            if (response.getStatus() == TypeOfAnswer.SUCCESSFUL) CollectionManager.getInstance().add(response.getLabWork());
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("LabWork isn't max!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } else {
            Response response = RequestHandler.getInstance().send(new UserCommand("add", "").addLabWork(labWork));
            CollectionManager.getInstance().add(response.getLabWork());
        }
        addStage.close();
    }

    public void setAddStage(Stage addStage) {
        this.addStage = addStage;
    }

    public void setCheckMax(Boolean checkMax) {
        this.checkMax = checkMax;
    }

    private void validateInit() {
        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                valid.set(0, false);
                nameField.getStyleClass().add("wrong-field");
            } else {
                valid.set(0, true);
                nameField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        coordinateXField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                var value = Double.parseDouble(newValue);
                if (value > -1000 && value < 1000) {
                    valid.set(1, true);
                    coordinateXField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid.set(1, false);
                    coordinateXField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid.set(1, false);
                coordinateXField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        coordinateYField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                var value = Double.parseDouble(newValue);
                if (value > -600 && value < 600) {
                    valid.set(2, true);
                    coordinateYField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid.set(2, false);
                    coordinateYField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid.set(2, false);
                coordinateYField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        minimalPointField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                var value = Double.parseDouble(newValue);
                if (value >= 0) {
                    valid.set(3, true);
                    minimalPointField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid.set(3, false);
                    minimalPointField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid.set(3, false);
                minimalPointField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        personalQualitiesMinimumField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty() || Integer.parseInt(newValue) >= 0) {
                    valid.set(4, true);
                    personalQualitiesMinimumField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid.set(4, false);
                    personalQualitiesMinimumField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid.set(4, false);
                personalQualitiesMinimumField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        authorNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                valid.set(5, false);
                authorNameField.getStyleClass().add("wrong-field");
            } else {
                valid.set(5, true);
                authorNameField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        authorHeightField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                var value = Double.parseDouble(newValue);
                if (value >= 0) {
                    valid.set(6, true);
                    authorHeightField.getStyleClass().removeIf(o -> o.equals("wrong-field"));
                } else {
                    valid.set(6, false);
                    authorHeightField.getStyleClass().add("wrong-field");
                }
            } catch (NumberFormatException e) {
                valid.set(6, false);
                authorHeightField.getStyleClass().add("wrong-field");
            }
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        authorHairColorChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            valid.set(7, true);
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        authorEyeColorChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            valid.set(8, true);
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        authorNationalityChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            valid.set(9, true);
            submitBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
    }
}
