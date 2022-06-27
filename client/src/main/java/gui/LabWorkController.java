package gui;

import data.LabWork;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public class LabWorkController {
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label coordinateXLabel;
    @FXML private Label coordinateYLabel;
    @FXML private Label creationDateLabel;
    @FXML private Label minimalPointLabel;
    @FXML private Label personalQualitiesMinimumLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label authorNameLabel;
    @FXML private Label authorHeightLabel;
    @FXML private Label authorEyeColorLabel;
    @FXML private Label authorHairColorLabel;
    @FXML private Label authorNationalityLabel;
    @FXML private Pane closeBtn;
    @FXML private AnchorPane mainPane;

    private NumberFormat nf = NumberFormat.getNumberInstance();

    @FXML void initialize() {
        closeBtn.setOnMouseEntered(event -> {
            closeBtn.setScaleX(1.2);
            closeBtn.setScaleY(1.2);
        });
        closeBtn.setOnMouseExited(event -> {
            closeBtn.setScaleX(1);
            closeBtn.setScaleY(1);
        });
    }

    public void setLabWork(LabWork labWork) {
        idLabel.setText(labWork.getId().toString());
        nameLabel.setText(labWork.getName());
        coordinateXLabel.setText(nf.format(labWork.getCoordinates().getX()));
        coordinateYLabel.setText(nf.format(labWork.getCoordinates().getY()));
        creationDateLabel.setText(labWork.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        minimalPointLabel.setText(nf.format(labWork.getMinimalPoint()));
        personalQualitiesMinimumLabel.setText(labWork.getPersonalQualitiesMinimum() == null ? "": nf.format(labWork.getPersonalQualitiesMinimum()));
        difficultyLabel.setText(labWork.getDifficulty() == null ? "": labWork.getDifficulty().getStringValue());
        authorNameLabel.setText(labWork.getAuthor().getName());
        authorHeightLabel.setText(nf.format(labWork.getAuthor().getHeight()));
        authorEyeColorLabel.setText(labWork.getAuthor().getEyeColor().getStringValue());
        authorHairColorLabel.setText(labWork.getAuthor().getHairColor().getStringValue());
        authorNationalityLabel.setText(labWork.getAuthor().getNationality().getStringValue());
    }

    @FXML private void close() {
        ((SplitPane) mainPane.getParent().getParent()).getItems().remove(mainPane);
    }
}
