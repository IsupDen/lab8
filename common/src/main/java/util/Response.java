package util;

import data.LabWork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Response implements Serializable {

    private String information = null;
    private ArrayList<LabWork> collection = null;
    private final TypeOfAnswer status;

    private Map<String, String> mapOfCommands = null;
    private LabWork labWork = null;
    private Long count = null;

    public Response(String information, TypeOfAnswer status) {
        this.information = information;
        this.status = status;
    }

    public Response(ArrayList<LabWork> collection, TypeOfAnswer status) {
        this.collection = collection;
        this.status = status;
    }

    public Response(LabWork labWork, TypeOfAnswer status) {
        this.labWork = labWork;
        this.status = status;
    }

    public Response(Long count, TypeOfAnswer status) {
        this.count = count;
        this.status = status;
    }

    public Response(TypeOfAnswer status) {
        this.status = status;
    }

    public Response(Map<String, String> mapOfCommands, TypeOfAnswer status) {
        this.mapOfCommands = mapOfCommands;
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public Map<String, String> getHelp() {return mapOfCommands;}

    public ArrayList<LabWork> getCollection() {
        return collection;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    public TypeOfAnswer getStatus() {
        return status;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return information + "\n" +
                collection + "\n" +
                status + "\n" +
                labWork + "\n" +
                count + "\n";
    }

}
