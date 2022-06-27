package util;

import data.Coordinates;
import data.Difficulty;
import data.LabWork;
import data.Person;

public class LabFactory {

    private final FieldsReceiverInterface fieldsReceiver;

    public LabFactory() {
        this.fieldsReceiver = new FieldsReceiver(Console.getInstance());
    }

    public LabWork createLabWork() {
        String name = fieldsReceiver.getName();
        Coordinates coordinates = fieldsReceiver.getCoordinates();
        Double minimalPoint = fieldsReceiver.getMinimalPoint();
        Integer personalQualitiesMinimum = fieldsReceiver.getPersonalQualitiesMinimum();
        Difficulty difficulty = fieldsReceiver.getDifficulty();
        Person author = fieldsReceiver.getAuthor();
        if ((name != null) &&
                (coordinates != null) &&
                (minimalPoint != null) &&
                (author != null))
            return new LabWork(0, name, coordinates, null, minimalPoint, personalQualitiesMinimum, difficulty, author);
        else return null;
    }
}
