package gui;

import data.LabWork;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import util.CollectionManager;

import javax.swing.text.TabableView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static util.ResourceBundleManager.getResourceBundle;

public class VisualController {

    private final HashMap<LabWork, Node> collection = new HashMap<>();
    private TableView<LabWork> table;
    private App app;
    private FXMLLoader labWorkLoader;
    private MainController mainController;

    @FXML private AnchorPane visualizationPane;


    public void paint() {
        for (LabWork labWork: CollectionManager.getInstance().getCollection()) {
            Canvas canvas = getLabWorkCanvas(labWork);
            if (!collection.containsKey(labWork)) {
                Node node = checkCollisions(labWork, canvas);
                collection.put(labWork, node);
                visualizationPane.getChildren().add(node);
            }
        }
    }

    private Canvas getLabWorkCanvas(LabWork labWork) {
        Canvas labCanvas = new Canvas(75, 75);
        labCanvas.setOnMouseEntered(event -> {
            labCanvas.setScaleX(1.13);
            labCanvas.setScaleY(1.13);
        });
        labCanvas.setOnMouseExited(event -> {
            labCanvas.setScaleX(1);
            labCanvas.setScaleY(1);
        });
        labCanvas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                app.showUpdatePanel(labWork, table);
            }
            try {
                if (mainController.getLabWorkPanel() == null) {
                    labWorkLoader = new FXMLLoader();
                    labWorkLoader.setResources(getResourceBundle());
                    mainController.setLabWorkPanel(labWorkLoader.load(getClass().getResourceAsStream("/fxml/labWorkPanel.fxml")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mainController.getSplitPane().getItems().size() == 1) mainController.getSplitPane().getItems().add(mainController.getLabWorkPanel());
            LabWorkController controller = labWorkLoader.getController();
            controller.setLabWork(labWork);
        });
        labCanvas.setLayoutX(getCorrectX(Double.valueOf(labWork.getCoordinates().getX()), labCanvas.getWidth()));
        labCanvas.setLayoutY(getCorrectY((double) labWork.getCoordinates().getY(), labCanvas.getHeight()));
        GraphicsContext graphicsContext = labCanvas.getGraphicsContext2D();
        int colorId = labWork.getUser().toLowerCase().charAt(0) - 'a';
        graphicsContext.setFill(Paint.valueOf(util.Color.values()[colorId % 10].getStringValue()));
        graphicsContext.fillOval(10, 60, 55, 15);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/labWork.png")));
        graphicsContext.drawImage(image, 0, 0, 70, 70);
        return labCanvas;
    }

    private Node checkCollisions(LabWork labWork, Canvas canvas) {
        for (LabWork printed: collection.keySet()) {
            Node printedNode = collection.get(printed);
            if (isCollision(printedNode, canvas)) {
                try {
                    MenuButton printedMenuButton = (MenuButton) printedNode;
                    visualizationPane.getChildren().remove(printedNode);
                    printedMenuButton.getItems().add(getLabWorkItem(labWork));
                    printedMenuButton.setText(String.valueOf(Integer.parseInt(printedMenuButton.getText())+1));
                    return printedMenuButton;
                } catch (ClassCastException e) {
                    visualizationPane.getChildren().remove(printedNode);
                    Node newItem = createMenuButton(printed, labWork);
                    collection.put(printed, newItem);
                    return createMenuButton(printed, labWork);
                }

            }
        }
        return canvas;
    }

    private boolean isCollision(Node printed, Canvas canvas) {
        return printed.getBoundsInParent().intersects(canvas.getBoundsInParent());
    }

    private MenuButton createMenuButton(LabWork labWork1, LabWork labWork2) {
        MenuButton menuButton = new MenuButton();
        menuButton.getItems().setAll(getLabWorkItem(labWork1), getLabWorkItem(labWork2));
        menuButton.getStylesheets().add("css/combobox.css");
        menuButton.setText("2");
        menuButton.setLayoutX(getCorrectX((labWork1.getCoordinates().getX() + labWork2.getCoordinates().getX()) / 2d, 40d));
        menuButton.setLayoutY(getCorrectY((labWork1.getCoordinates().getY() + labWork2.getCoordinates().getY()) / 2d, 40d));
        menuButton.setStyle("-fx-background-radius:30");
        menuButton.setPrefWidth(40);
        menuButton.setPrefHeight(40);
        return menuButton;
    }

    private Double getCorrectX(Double x, Double width) {
        return x + 1000 - width / 2.0;
    }

    private Double getCorrectY(Double y, Double height) {
        return 600 - (y + height / 2.0);
    }

    private CustomMenuItem getLabWorkItem(LabWork labWork) {
        HBox labWorkBox = new HBox();
        labWorkBox.setPrefHeight(10);
        labWorkBox.setPrefWidth(100);
        int colorId = labWork.getUser().toLowerCase().charAt(0) - 'a';
        labWorkBox.getChildren().add(new Circle(5, Paint.valueOf(util.Color.values()[colorId % 10].getStringValue())));
        labWorkBox.getChildren().add(new Label(labWork.getName()));
        labWorkBox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                app.showUpdatePanel(labWork, table);
            }
            try {
                if (mainController.getLabWorkPanel() == null) {
                    labWorkLoader = new FXMLLoader();
                    labWorkLoader.setResources(getResourceBundle());
                    mainController.setLabWorkPanel(labWorkLoader.load(getClass().getResourceAsStream("/fxml/labWorkPanel.fxml")));
                    mainController.getSplitPane().getItems().add(mainController.getLabWorkPanel());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            LabWorkController controller = labWorkLoader.getController();
            controller.setLabWork(labWork);
        });
        CustomMenuItem labWorkItem = new CustomMenuItem(labWorkBox);
        labWorkItem.setHideOnClick(false);
        return labWorkItem;
    }

    public void setTable(TableView<LabWork> table) {
        this.table = table;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setLabWorkLoader(FXMLLoader labWorkLoader) {
        this.labWorkLoader = labWorkLoader;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
