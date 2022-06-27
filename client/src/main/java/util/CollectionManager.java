package util;

import data.LabWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CollectionManager {
    ObservableList<LabWork> collection;

    private static CollectionManager instance;

    public CollectionManager() {
        collection = FXCollections.observableList(RequestHandler.getInstance().send(new UserCommand("show", "")).getCollection());
    }

    public static CollectionManager getInstance() {
        if (instance == null) instance = new CollectionManager();
        return instance;
    }

    public ObservableList<LabWork> getCollection() {
        return collection;
    }

    public void add(LabWork labWork) {
        collection.add(labWork);
    }

    public void setCollection(ObservableList<LabWork> collection) {
        this.collection.removeIf(labWork -> collection.stream().noneMatch(newLabWork -> Objects.equals(newLabWork.getId(), labWork.getId())));
        collection.removeIf(labWork -> this.collection.stream().anyMatch(newLabWork -> Objects.equals(newLabWork.getId(), labWork.getId())));
        if (!collection.isEmpty()) {
            System.out.println(this.collection.size());
            System.out.println(collection.size());
            System.out.println();
            this.collection.addAll(collection);
        }
    }
}
