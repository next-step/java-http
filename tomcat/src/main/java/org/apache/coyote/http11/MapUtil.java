package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapUtil {

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Map<String, String> parseToMap(String[] spilitedArray, Pattern delimiter){

        Map<String, String> account = Arrays.stream(spilitedArray)
            .filter(arr -> !arr.isEmpty())
            .map(line -> delimiter.split(line))
            .collect(Collectors.toMap(words -> words[KEY_INDEX].trim(), words -> words[VALUE_INDEX].trim()));


        return account;
    }
}
