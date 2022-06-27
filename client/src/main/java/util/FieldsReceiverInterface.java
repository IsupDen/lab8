package util;

import data.Coordinates;
import data.Difficulty;
import data.Person;


public interface FieldsReceiverInterface {
    String getName();

    Coordinates getCoordinates();

    Double getMinimalPoint();

    Integer getPersonalQualitiesMinimum();

    Difficulty getDifficulty();

    Person getAuthor();
}
