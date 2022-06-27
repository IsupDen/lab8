package gui;

import data.Difficulty;
import data.LabWork;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import util.CollectionSynchronizer;
import util.RequestHandler;
import util.UserCommand;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static util.ResourceBundleManager.getResourceBundle;

public class App extends Application {

    private Stage stage;
    private Thread collectionSync;


    public static void runApp() {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("LabWorks");
        switchToConnectPanel(); // TODO change to connect panel
        this.stage.show();
        this.stage.setOnCloseRequest(event -> {
            if (collectionSync != null) collectionSync.interrupt();
        });
    }


    public void switchToConnectPanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/connectPanel.fxml"));
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add("css/style.css");
            stage.setResizable(false);
            stage.setMinWidth(300);
            stage.setMinHeight(200);
            ConnectController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToLoginPanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/loginPanel.fxml"));
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add("css/style.css");
            stage.setResizable(false);
            stage.setMinWidth(300);
            stage.setMinHeight(200);
            LoginController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToMainPanel() {
        try {
            if (collectionSync == null) {
                collectionSync = new Thread(new CollectionSynchronizer());
                collectionSync.start();
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/mainPanel.fxml"));
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add("css/style.css");
            stage.setResizable(true);
            stage.setMinWidth(650);
            stage.setMinHeight(300);
            MainController controller = loader.getController();
            controller.setApp(this);
            controller.setLoader(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddPanel(Boolean checkMax) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/addPanel.fxml"));
            Stage addStage = new Stage();
            addStage.setTitle("Add LabWork");
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.initOwner(stage);
            addStage.setScene(new Scene(root));
            addStage.getScene().getStylesheets().add("css/style.css");
            addStage.setResizable(false);
            AddController controller = loader.getController();
            controller.setAddStage(addStage);
            controller.setCheckMax(checkMax);
            addStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File showFilePanel() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(stage);
    }

    public void showUpdatePanel(LabWork labWork, TableView<LabWork> table) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/updatePanel.fxml"));
            Stage updateStage = new Stage();
            updateStage.setTitle("Update LabWork");
            updateStage.initModality(Modality.WINDOW_MODAL);
            updateStage.initOwner(stage);
            updateStage.setScene(new Scene(root));
            updateStage.getScene().getStylesheets().add("css/style.css");
            updateStage.setResizable(false);
            UpdateController controller = loader.getController();
            controller.setUpdateStage(updateStage);
            controller.setLabWork(labWork);
            controller.setTable(table);
            updateStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSelectDifficultyPanel(TableView<LabWork> table) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/selectDifficultyPanel.fxml"));
            Stage selectStage = new Stage();
            selectStage.setTitle("Select difficulty");
            selectStage.initModality(Modality.WINDOW_MODAL);
            selectStage.initOwner(stage);
            selectStage.setScene(new Scene(root));
            selectStage.setResizable(false);
            SelectDifficultyController controller = loader.getController();
            controller.setSelectStage(selectStage);
            controller.setTable(table);
            selectStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEnterPqmPanelPanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(getResourceBundle());
            Parent root = loader.load(getClass().getResourceAsStream("/fxml/enterPqmPanel.fxml"));
            Stage enterStage = new Stage();
            enterStage.setTitle("Enter personal qualities minimum");
            enterStage.initModality(Modality.WINDOW_MODAL);
            enterStage.initOwner(stage);
            enterStage.setScene(new Scene(root));
            enterStage.getScene().getStylesheets().add("css/style.css");
            enterStage.setResizable(false);
            EnterValueController controller = loader.getController();
            controller.setEnterStage(enterStage);
            enterStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }


}
