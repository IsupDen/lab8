package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AvailableCommands {

    public final Set<String> noArgCommands = new HashSet<>();
    public final Set<String> numArgCommands = new HashSet<>();
    public final Set<String> strArgCommands = new HashSet<>();
    public final Set<String> objArgCommands = new HashSet<>();
    public final String scriptArgCommand;
    public final String objAndNumArgCommand;

    public AvailableCommands() {
        Collections.addAll(noArgCommands,
                "help",
                "info",
                "show",
                "clear",
                "group_counting_by_coordinates",
                "remove_first",
                "reorder");

        Collections.addAll(numArgCommands,
                "count_less_than_pqm",
                "remove_by_id");

        Collections.addAll(strArgCommands,
                "remove_all_by_difficulty");

        Collections.addAll(objArgCommands,
                "add",
                "add_if_max");

        scriptArgCommand = "execute_script";

        objAndNumArgCommand = "update";
    }
}
