package data;

import javafx.beans.property.*;
import javafx.collections.ObservableArray;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LabWork implements Comparable<LabWork>, Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Double minimalPoint;
    private Integer personalQualitiesMinimum;
    private Difficulty difficulty;
    private Person author;

    private String user;


    public LabWork(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Double minimalPoint,
                   Integer personalQualitiesMinimum, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.difficulty = difficulty;
        this.author = author;
        user = null;
    }

    public LabWork(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Double minimalPoint,
                   Integer personalQualitiesMinimum, Difficulty difficulty, Person author, String user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.difficulty = difficulty;
        this.author = author;
        this.user = user;
    }

    public LabWork(String[] fields) {
        id = Integer.parseInt(fields[0]);
        name = fields[1];
        coordinates = new Coordinates(Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
        creationDate = LocalDateTime.parse(fields[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        minimalPoint = Double.parseDouble(fields[5]);
        personalQualitiesMinimum = fields[6].equals("") ? null : Integer.parseInt(fields[6]);
        difficulty = fields[7].equals("") ? null : Difficulty.valueOf(fields[7]);
        author = new Person(fields[8], Double.parseDouble(fields[9]), HairColor.valueOf(fields[11]), EyeColor.valueOf(fields[10]), Country.valueOf(fields[12]));


    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


    public void setCreationDate(LocalDateTime date) {this.creationDate = date;}

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public StringProperty creationDateProperty() {
        return new SimpleStringProperty(creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void setMinimalPoint(Double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }
    public Double getMinimalPoint() {
        return minimalPoint;
    }

    public DoubleProperty minimalPointProperty() {
        return new SimpleDoubleProperty(minimalPoint);
    }

    public void setPersonalQualitiesMinimum(Integer personalQualitiesMinimum) {
        this.personalQualitiesMinimum = personalQualitiesMinimum;
    }

    public Integer getPersonalQualitiesMinimum() {
        return personalQualitiesMinimum;
    }

    public IntegerProperty personalQualitiesMinimumProperty() {
        if (personalQualitiesMinimum != null) return new SimpleIntegerProperty(personalQualitiesMinimum);
        return null;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public StringProperty difficultyProperty() {
        return new SimpleStringProperty(String.valueOf(difficulty));
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getAuthor() {
        return author;
    }

    public String getUser() {return user;}


    public StringProperty authorNameProperty() {
        return new SimpleStringProperty(author.getName());
    }

    public DoubleProperty authorHeightProperty() {
        return new SimpleDoubleProperty(author.getHeight());
    }

    public StringProperty authorEyeColorProperty() {
        return new SimpleStringProperty(author.getEyeColor().getStringValue());
    }

    public StringProperty authorHairColorProperty() {
        return new SimpleStringProperty(author.getHairColor().getStringValue());
    }

    public StringProperty authorNationalityProperty() {
        return new SimpleStringProperty(author.getNationality().getStringValue());
    }

    public void setUser(String user) {this.user = user;}

    @Override
    public String toString() {
        return "\n" + name + ":" + "\n" +
                "Id                        : " + id + "\n" +
                "Coordinates               : " + coordinates + "\n" +
                "Creation date             : " + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "Minimal point             : " + minimalPoint + "\n" +
                "Personal qualities minimum: " + personalQualitiesMinimum + "\n" +
                "Difficulty                : " + difficulty + "\n" +
                "Author                    : " + author;
    }

    public String toCsv() {
        return id + "," + name + "," + coordinates.toCsv() + ","
                + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ","
                + minimalPoint + "," + (personalQualitiesMinimum == null ? "" : personalQualitiesMinimum) + ","
                + (difficulty == null ? "" : difficulty) + "," + author.toCsv();
    }

    @Override
    public int compareTo(LabWork labWork) {
        if (labWork.getPersonalQualitiesMinimum() == null) return 1;
        if (personalQualitiesMinimum == null) return -1;
        return personalQualitiesMinimum.compareTo(labWork.getPersonalQualitiesMinimum());
    }

    public int comparePQM(Integer personalQualitiesMinimum) {
        if (personalQualitiesMinimum == null) return 1;
        if (this.personalQualitiesMinimum == null) return -1;
        return this.personalQualitiesMinimum.compareTo(personalQualitiesMinimum);
    }
}
