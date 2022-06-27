package util;

import static util.TextFormat.*;

public class AnswerFormatter {

    public static String formatAnswer(Response response) {
        StringBuilder sb = new StringBuilder();
        if (response.getStatus().equals(TypeOfAnswer.SUCCESSFUL)) {
            if (response.getInformation() != null) {
                sb.append(response.getInformation());
                }
            if (response.getHelp() != null) {
                response.getHelp().
                        forEach((key, value) -> sb
                                .append(successText(key.toUpperCase()))
                                .append(" : ")
                                .append(value)
                                .append("\n"));
            }
            if (response.getCollection() != null) {
                response.getCollection()
                        .forEach(sg -> sb.append(sg).append("\n\n"));
            }
            if (response.getLabWork() != null) {
                sb.append(response.getLabWork().toString())
                        .append("\n");
            }
            if (response.getCount() != null) {
                sb.append("Amount of elements: ")
                        .append(response.getCount());
            }
            if (sb.toString().equals("")) return successText("Action processed successful!");
        } else {
            switch (response.getStatus()) {
                case OBJECTNOTEXIST:
                    return errText("No object with such parameters was found!\n");
                case DUPLICATESDETECTED:
                    return errText("This element probably duplicates " +
                            "existing one and can't be added\n");
                case ISNTMAX:
                    return errText("LabWork isn't max!");
                case PERMISSIONDENIED:
                    return errText("Permission denied!");
                case SQLPROBLEM:
                    return errText("Some problem's with database on server!");
                case EMPTYCOLLECTION:
                    return helpText("Collection is empty!");
                case ALREADYREGISTERED:
                    return errText("This account already registered!");
                case NOTMATCH:
                    return errText("Account with this parameters doesn't exist!");
            }
        }
        return sb.toString();
    }
}
