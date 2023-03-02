package ru.clevertec.mapper;

import ru.clevertec.exception.JsonFormatException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonMapper {

    private static final String JSON_PAIRS_REGEX =
            "\"(\\w+)\":((\\[.*])|(\".*?\")|(\\d+\\.\\d+)|(\\d+)|(true)|(false)|(null))";
    private static final String JSON_ARRAY_REGEX = "\\[(.*(?:,.*)*)?(?<!,)]";

    public Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = new HashMap<>();

        Pattern jsonPattern = Pattern.compile(JSON_PAIRS_REGEX);
        Matcher jsonMatcher = jsonPattern.matcher(json);

        while (jsonMatcher.find()) {
            String key = jsonMatcher.group(1);
            Object value = jsonToValue(jsonMatcher.group(2));
            map.put(key, value);
        }

        return map;
    }

    private List<Object> jsonToArray(String jsonArray) {
        List<Object> array = new ArrayList<>();

        if (!jsonArray.matches(JSON_ARRAY_REGEX)) {
            throw new JsonFormatException("Invalid array format");
        }

        jsonArray = jsonArray.substring(1, jsonArray.length() - 1);
        if (jsonArray.isEmpty()) {
            return array;
        }
        if (jsonArray.contains(",")) {
            String[] arrayValues = jsonArray.split(",");

            array = Arrays.stream(arrayValues)
                    .map(this::jsonToValue)
                    .toList();
        } else {
            Object parseArrayValue = jsonToValue(jsonArray);
            array.add(parseArrayValue);
        }

        return array;
    }

    private Object jsonToValue(String jsonValue) {
        if ("null".equals(jsonValue)) {
            return null;
        }
        if (jsonValue.startsWith("{") && jsonValue.endsWith("}")) {
            return jsonToMap(jsonValue);
        }
        if (jsonValue.startsWith("[") && jsonValue.endsWith("]")) {
            return jsonToArray(jsonValue);
        }
        if (jsonValue.startsWith("\"") && jsonValue.endsWith("\"")) {
            return jsonValue.substring(1, jsonValue.length() - 1);
        }
        if ("true".equals(jsonValue) || "false".equals(jsonValue)) {
            return Boolean.parseBoolean(jsonValue);
        }
        if (jsonValue.contains(".")) {
            return Double.parseDouble(jsonValue);
        }

        return Integer.parseInt(jsonValue);
    }
}
