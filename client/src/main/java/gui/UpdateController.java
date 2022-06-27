package gui;

import data.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.RequestHandler;
import util.UserCommand;

import java.util.Arrays;
import java.util.List;

public class UpdateController {

    private LabWork labWork;
    private TableView<LabWork> table;
    private Stage updateStage;
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

    @FXML private void update() {
        labWork.setName(nameField.getText());
        labWork.setCoordinates(new Coordinates(Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText())));
        labWork.setMinimalPoint(Double.parseDouble(minimalPointField.getText()));
        labWork.setPersonalQualitiesMinimum(personalQualitiesMinimumField.getText().equals("") ? null: Integer.parseInt(personalQualitiesMinimumField.getText()));
        labWork.setDifficulty(difficultyChoiceBox.getValue().equals("") ? null: Difficulty.valueOf(difficultyChoiceBox.getValue()));
        labWork.setAuthor(new Person(authorNameField.getText(),
                        Double.parseDouble(authorHeightField.getText()),
                        HairColor.valueOf(authorHairColorChoiceBox.getValue()),
                        EyeColor.valueOf(authorEyeColorChoiceBox.getValue()),
                        Country.valueOf(authorNationalityChoiceBox.getValue())));

        RequestHandler.getInstance().send(new UserCommand("update", labWork.getId().toString()).addLabWork(labWork));
        table.refresh();
        updateStage.close();
    }

    public void setUpdateStage(Stage addStage) {
        this.updateStage = addStage;
    }

    public void setTable(TableView<LabWork> table) {
        this.table = table;
    }

    public void setLabWork(LabWork labWork) {
        this.labWork = labWork;
        nameField.setText(labWork.getName());
        coordinateXField.setText(labWork.getCoordinates().getX().toString());
        coordinateYField.setText(String.valueOf(labWork.getCoordinates().getY()));
        minimalPointField.setText(labWork.getMinimalPoint().toString());
        personalQualitiesMinimumField.setText(labWork.getPersonalQualitiesMinimum() == null ? null: labWork.getPersonalQualitiesMinimum().toString());
        difficultyChoiceBox.setValue(labWork.getDifficulty() == null ? null: labWork.getDifficulty().getStringValue());
        authorNameField.setText(labWork.getAuthor().getName());
        authorHeightField.setText(labWork.getAuthor().getHeight().toString());
        authorHairColorChoiceBox.setValue(labWork.getAuthor().getHairColor().getStringValue());
        authorEyeColorChoiceBox.setValue(labWork.getAuthor().getEyeColor().getStringValue());
        authorNationalityChoiceBox.setValue(labWork.getAuthor().getNationality().getStringValue());
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
                if (newValue == null || newValue.isEmpty() || Integer.parseInt(newValue) >= 0) {
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
