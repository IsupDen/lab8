package util;

import data.LabWork;

import java.time.LocalDateTime;

public class AutoFieldsSetter {

    public static Request setFields(Request request) {
        LabWork labWork = request.getCommand().getLabWork();
        String user = request.getSession().getName();

        if (labWork != null) {
            labWork.setCreationDate(LocalDateTime.now());
            labWork.setUser(user);
        }
        return request;
    }

}
