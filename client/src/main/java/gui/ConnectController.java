package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.RequestHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ConnectController {

    @FXML
    private TextField host;
    @FXML
    private TextField port;

    @FXML private Button connectBtn;
    private App app;
    private List<Boolean> valid = Arrays.asList(new Boolean[2]);

    @FXML private void initialize() {
        Pattern remoteHostPortPattern = Pattern.compile("(\\d{1,5})");
        Pattern ipAddress = Pattern.compile("(\\d{1,3}.){3}\\d{1,3}");
        Pattern dndAddress = Pattern.compile("\\w+(.\\w+)*");
        host.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (ipAddress.matcher(newValue).matches() || dndAddress.matcher(newValue).matches()) {
                valid.set(0, true);
                host.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            } else {
                valid.set(0, false);
                host.getStyleClass().add("wrong-field");
            }
            connectBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
        port.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (remoteHostPortPattern.matcher(newValue).matches()) {
                valid.set(1, true);
                port.getStyleClass().removeIf(o -> o.equals("wrong-field"));
            } else {
                valid.set(1, false);
                port.getStyleClass().add("wrong-field");
            }
            connectBtn.setDisable(valid.contains(false) || valid.contains(null));
        }));
    }

    @FXML private void connect() {
        try {
            RequestHandler.getInstance().setRemoteHostSocketAddress(
                    new InetSocketAddress(InetAddress.getByName(host.getText()), Integer.parseInt(port.getText()))
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        app.switchToLoginPanel();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
