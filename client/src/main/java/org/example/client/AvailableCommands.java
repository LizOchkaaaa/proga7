package org.example.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AvailableCommands {
    public final Set<String> noArgumentCommands = new HashSet<>();
    public final Set<String> numArgumentCommands = new HashSet<>();
    public final Set<String> stringArgumentCommands = new HashSet<>();
    public final Set<String> objectArgumentCommands = new HashSet<>();
    public final String scriptArgumentCommand;
    public final String objAndNumArgumentCommand;

    public AvailableCommands() {

        Collections.addAll(noArgumentCommands,
                "help",
                "info",
                "show",
                "clear",
                "sum_of_students_count",
                "print_field_ascending_students_count",
                "print_unique_form_of_education",
                "reorder",
                "exit");

        Collections.addAll(numArgumentCommands,
                "remove_by_id",
                "count_less_than_students_count");

        Collections.addAll(stringArgumentCommands,
                "filter_starts_with_name");

        Collections.addAll(objectArgumentCommands,
                "add",
                "add_if_max",
                "remove_greater");

        scriptArgumentCommand = "execute_script";

        objAndNumArgumentCommand = "update";
    }
}
