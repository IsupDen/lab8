package gui;

import data.*;
import gui.filter.TableFilter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import util.*;
import util.Color;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static util.AnswerFormatter.formatAnswer;
import static util.ResourceBundleManager.getResourceBundle;
import static util.ResourceBundleManager.setResourceBundle;


public class MainController{

    private FXMLLoader labWorkLoader;
    private Node labWorkPanel;
    private FXMLLoader visualLoader;
    private Node visualPanel;
    private App app;
    private CommandManager commandManager;
    private FXMLLoader loader;
    private TableFilter<LabWork> tableFilter;
    @FXML
    private TableView<LabWork> table;
    @FXML
    private TableColumn<LabWork, Integer> idColumn;
    @FXML
    private TableColumn<LabWork, String> nameColumn;
    @FXML
    private TableColumn<LabWork, String> coordinateXColumn;
    @FXML
    private TableColumn<LabWork, String> coordinateYColumn;
    @FXML
    private TableColumn<LabWork, String> creationDateColumn;
    @FXML
    private TableColumn<LabWork, String> minimalPointColumn;
    @FXML
    private TableColumn<LabWork, String> personalQualitiesMinimumColumn;
    @FXML
    private TableColumn<LabWork, String> difficultyColumn;
    @FXML
    private TableColumn<LabWork, String> authorNameColumn;
    @FXML
    private TableColumn<LabWork, String> authorHeightColumn;
    @FXML
    private TableColumn<LabWork, String> authorEyeColorColumn;
    @FXML
    private TableColumn<LabWork, String> authorHairColorColumn;
    @FXML
    private TableColumn<LabWork, String> authorNationalityColumn;
    @FXML private SplitPane splitPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Circle avatar;
    @FXML private MenuButton profile;
    @FXML private ComboBox<HBox> localeComboBox;

    @FXML private MenuButton helpBtn;

    private final NumberFormat nf = NumberFormat.getNumberInstance();

    @FXML public void initialize() {
        idColumn.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        coordinateXColumn.setCellValueFactory(cell -> new SimpleStringProperty(nf.format(cell.getValue().getCoordinates().getX())));
        coordinateYColumn.setCellValueFactory(cell -> new SimpleStringProperty(nf.format(cell.getValue().getCoordinates().getY())));
        creationDateColumn.setCellValueFactory(cell -> cell.getValue().creationDateProperty());
        minimalPointColumn.setCellValueFactory(cell -> new SimpleStringProperty(nf.format(cell.getValue().getMinimalPoint())));
        personalQualitiesMinimumColumn.setCellValueFactory(cell -> cell.getValue().personalQualitiesMinimumProperty() == null ? null : new SimpleStringProperty(nf.format(cell.getValue().getPersonalQualitiesMinimum())));
        difficultyColumn.setCellValueFactory(cell -> (cell.getValue().difficultyProperty() == null || Objects.equals(cell.getValue().difficultyProperty(), new SimpleStringProperty(""))) ? null : cell.getValue().difficultyProperty());
        authorNameColumn.setCellValueFactory(cell -> cell.getValue().authorNameProperty());
        authorHeightColumn.setCellValueFactory(cell -> new SimpleStringProperty(nf.format(cell.getValue().getAuthor().getHeight())));
        authorEyeColorColumn.setCellValueFactory(cell -> cell.getValue().authorEyeColorProperty());
        authorHairColorColumn.setCellValueFactory(cell -> cell.getValue().authorHairColorProperty());
        authorNationalityColumn.setCellValueFactory(cell -> cell.getValue().authorNationalityProperty());
        ObservableList<LabWork> collection = CollectionManager.getInstance().getCollection();
        table.setItems(collection);
        table.setOnMouseClicked(this::selectItem);
        table.setOnKeyPressed(this::keyInput);
        commandManager = new CommandManager(new CommandReader());
        int colorId = RequestHandler.getInstance().getSession().getName().toLowerCase().charAt(0) - 'a';
        avatar.setFill(Paint.valueOf(Color.values()[colorId % 10].getStringValue()));
        profile.setText(RequestHandler.getInstance().getSession().getName());
        localeComboBox.getItems().addAll(
                getLangBox("ru"),
                getLangBox("en"),
                getLangBox("sk"),
                getLangBox("sq")
        );
        localeComboBox.setValue(getLangBox(getResourceBundle().getLocale().getLanguage()));
        localeComboBox.setOnAction(event -> {
            setResourceBundle(ResourceBundle.getBundle("bundles.locale", new Locale(((Label) localeComboBox.getValue().getChildren().get(1)).getText())));
            Locale.setDefault(getResourceBundle().getLocale());
            app.switchToMainPanel();
        });
        tableFilter = new TableFilter<>(table, collection)
                .addFilter(idColumn, labWork -> Integer.toString(labWork.getId()))
                .addFilter(nameColumn, LabWork::getName)
                .addFilter(coordinateXColumn, labWork -> Integer.toString(labWork.getCoordinates().getX()))
                .addFilter(coordinateYColumn, labWork -> Integer.toString(labWork.getCoordinates().getY()))
                .addFilter(creationDateColumn, labWork -> labWork.getCreationDate().toString())
                .addFilter(minimalPointColumn, labWork -> Double.toString(labWork.getMinimalPoint()))
                .addFilter(personalQualitiesMinimumColumn, labWork -> labWork.getPersonalQualitiesMinimum() == null ? "" : labWork.getPersonalQualitiesMinimum().toString())
                .addFilter(difficultyColumn, labWork -> labWork.getDifficulty() == null ? "" : labWork.getDifficulty().getStringValue())
                .addFilter(authorNameColumn, labWork -> labWork.getAuthor().getName())
                .addFilter(authorHeightColumn, labWork -> Double.toString(labWork.getAuthor().getHeight()))
                .addFilter(authorEyeColorColumn, labWork -> labWork.getAuthor().getEyeColor().getStringValue())
                .addFilter(authorHairColorColumn, labWork -> labWork.getAuthor().getHairColor().getStringValue())
                .addFilter(authorNationalityColumn, labWork -> labWork.getAuthor().getNationality().getStringValue());


    }

    @FXML private void add() {
        app.showAddPanel(false);
    }

    @FXML private void addIfMax() {
        app.showAddPanel(true);
    }

    @FXML private void delete() {
        LabWork labWork = table.getSelectionModel().getSelectedItem();
        if (labWork != null) {
            Response response = RequestHandler.getInstance().send(new UserCommand("remove_by_id", labWork.getId().toString()));
            if (response.getStatus() == TypeOfAnswer.SUCCESSFUL) table.getItems().remove(labWork);
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Permission denied!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML private void clear() {
        Response response = RequestHandler.getInstance().send(new UserCommand("clear", ""));
        if (response.getStatus() == TypeOfAnswer.SUCCESSFUL) {
            String user = RequestHandler.getInstance().getSession().getName();
            table.getItems().removeIf(labWork -> labWork.getUser().equals(user));
        }
    }

    @FXML private void execute() {
        File file = app.showFilePanel();
        if (file != null) {
            String response = commandManager.executeScript(file.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(response);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    @FXML private void group() {
        Response response = RequestHandler.getInstance().send(new UserCommand("group_counting_by_coordinates", ""));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(formatAnswer(response));
        alert.setHeaderText(null);
        alert.showAndWait();
        //TODO edit view
    }

    @FXML private void count() {
        app.showEnterPqmPanelPanel();
    }

    @FXML private void removeAllByDifficulty() {
        app.showSelectDifficultyPanel(table);
    }

    @FXML private void logOut() {
        app.switchToLoginPanel();
    }

    @FXML private void disconnect() {
        app.switchToConnectPanel();
    }

    @FXML private void showVisual() {
        try {
            if (visualPanel == null) {
                visualLoader = new FXMLLoader();
                visualPanel = visualLoader.load(getClass().getResourceAsStream("/fxml/visualPanel.fxml"));
                scrollPane.setContent(visualPanel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        VisualController controller = visualLoader.getController();
        controller.setApp(app);
        controller.setTable(table);
        controller.setLabWorkLoader(labWorkLoader);
        controller.setMainController(this);
        controller.paint();

    }


    private void keyInput(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            LabWork labWork = table.getSelectionModel().getSelectedItem();
            Response response = RequestHandler.getInstance().send(new UserCommand("remove_by_id", labWork.getId().toString()));
            if (response.getStatus() == TypeOfAnswer.SUCCESSFUL) table.getItems().remove(labWork);
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Permission denied!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }

    }

    private HBox getLangBox(String lang) {
        Label label = new Label(lang.toLowerCase());
        label.setTextFill(Paint.valueOf("black"));
        HBox langBox = new HBox(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/img/%s.png", lang))))), label);
        langBox.setAlignment(Pos.CENTER_LEFT);
        langBox.setSpacing(5);
        return langBox;
    }


    private void selectItem(MouseEvent event) {
        if (event.getClickCount() == 2) {
            app.showUpdatePanel(table.getSelectionModel().getSelectedItem(), table);
        }
        try {
            if (labWorkPanel == null) {
                labWorkLoader = new FXMLLoader();
                labWorkLoader.setResources(getResourceBundle());
                labWorkPanel = labWorkLoader.load(getClass().getResourceAsStream("/fxml/labWorkPanel.fxml"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (splitPane.getItems().size() == 1) splitPane.getItems().add(labWorkPanel);
            LabWorkController controller = labWorkLoader.getController();
            controller.setLabWork(table.getSelectionModel().getSelectedItem());
        }

    }
    public void setApp(App app) {
        this.app = app;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public Node getLabWorkPanel() {
        return labWorkPanel;
    }

    public void setLabWorkPanel(Node labWorkPanel) {
        this.labWorkPanel = labWorkPanel;
    }
    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public TableFilter<LabWork> getTableFilter() {
        return tableFilter;
    }
}
