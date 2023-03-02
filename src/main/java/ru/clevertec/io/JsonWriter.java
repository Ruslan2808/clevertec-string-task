package ru.clevertec.io;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Collection;
import java.util.Map;

public class JsonWriter {

    public String objectToJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return valueToJson(null);
        }

        var json = new StringBuilder("{");

        Class<?> classType = obj.getClass();
        while(classType != null) {
            Field[] fields = classType.getDeclaredFields();

            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);

                Object fieldValue = field.get(obj);

                String jsonKey = field.getName();
                String jsonValue = valueToJson(fieldValue);
                var jsonObj = String.format("\"%s\":%s,", jsonKey, jsonValue);

                json.append(jsonObj);
            }

            classType = classType.getSuperclass();
        }

        deleteLastCommaInJson(json);
        json.append("}");

        return json.toString();
    }

    private String mapToJson(Object obj) throws IllegalAccessException {
        var json = new StringBuilder("{");

        var objEntries = ((Map<?, ?>) obj).entrySet();
        for (Map.Entry<?, ?> objEntry : objEntries) {
            String jsonKey = objEntry.getKey().toString();
            String jsonValue = valueToJson(objEntry.getValue());
            var jsonMap = String.format("\"%s\":%s,", jsonKey, jsonValue);

            json.append(jsonMap);
        }

        deleteLastCommaInJson(json);
        json.append("}");

        return json.toString();
    }

    private String arrayToJson(Object obj) throws IllegalAccessException {
        var json = new StringBuilder("[");

        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            var jsonValue = valueToJson(Array.get(obj, i));
            json.append(jsonValue).append(",");
        }

        deleteLastCommaInJson(json);
        json.append("]");

        return json.toString();
    }

    private String valueToJson(Object value) throws IllegalAccessException {
        if (value == null) {
            return "null";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        if (value.getClass().isArray()) {
            return arrayToJson(value);
        }
        if (value instanceof Collection) {
            return arrayToJson(((Collection<?>)value).toArray());
        }
        if (value instanceof Map) {
            return mapToJson(value);
        }
        if (value.getClass().getClassLoader() == ClassLoader.getSystemClassLoader()) {
            return objectToJson(value);
        }

        return String.format("\"%s\"", value);
    }

    private void deleteLastCommaInJson(StringBuilder json) {
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
    }
}
