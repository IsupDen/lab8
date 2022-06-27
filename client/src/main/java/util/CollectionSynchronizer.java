package util;

import javafx.collections.FXCollections;

public class CollectionSynchronizer implements Runnable {

    @Override
    public void run() {
        while (true) {
            Response response = RequestHandler.getInstance().send(new UserCommand("show", ""));
            CollectionManager.getInstance().setCollection(FXCollections.observableList(response.getCollection()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
