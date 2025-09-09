package org.helal_anwar.quiz_time.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDAParser {
    public static void main(String[] args) {
        String input = "[[null,sddsds,dsdsd], [sdsds, dsdsd]]";
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(input);
        ArrayList<String[]> list = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group(1);
            String[] elements = Arrays.toString(group.split(",")).replace("[", "").replace("]", "").split(",");
            list.add(elements);
        }
        System.out.println(Arrays.deepToString(list.toArray(new String[0][0])));

    }
}
