package ru.clevertec.io;

import ru.clevertec.mapper.JsonMapper;

import java.beans.PropertyEditorManager;

import java.lang.reflect.*;

import java.util.*;

public class JsonReader {

    private final JsonMapper jsonMapper;

    public JsonReader(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public <T> T objectFromJson(String json, Class<T> classType) throws IllegalAccessException {
        if (json.trim().isEmpty()) {
            return null;
        }

        T obj = getInstance(classType);

        var fieldValues = jsonMapper.jsonToMap(json);
        setObjectFields(fieldValues, obj);

        return obj;
    }

    private <T> T objectFromJson(Map<String, Object> fieldValues, Class<T> classType) throws IllegalAccessException {
        if (fieldValues.isEmpty()) {
            return null;
        }

        T obj = getInstance(classType);
        setObjectFields(fieldValues, obj);

        return obj;
    }

    private Object primitiveFromJson(Object primitiveValue, Class<?> clazz) {
        if (clazz.isAssignableFrom(Character.class) ||
                clazz.isAssignableFrom(char.class)
        ) {
            return String.valueOf(primitiveValue).charAt(0);
        }

        return primitiveValue;
    }

    private Object arrayFromJson(Object arrayValue, Class<?> classType) {
        Object[] jsonArray = ((List<?>) arrayValue).toArray();
        Class<?> objClassType = classType.getComponentType();

        Object array = Array.newInstance(objClassType, jsonArray.length);
        if (objClassType.isAssignableFrom(char.class) ||
                objClassType.isAssignableFrom(Character.class)
        ) {
            for (int i = 0; i < jsonArray.length; i++) {
                Array.set(array, i, String.valueOf(jsonArray[i]).charAt(0));
            }

            return array;
        }

        var editor = PropertyEditorManager.findEditor(objClassType);
        for (int i = 0; i < jsonArray.length; i++) {
            editor.setValue(jsonArray[i]);
            Array.set(array, i, editor.getValue());
        }

        return array;
    }

    private Object objectValueFromJson(Object objValue, Class<?> classType) {
        if (classType.isPrimitive()) {
            return primitiveFromJson(objValue, classType);
        }
        if (classType.isArray()) {
            return arrayFromJson(objValue, classType);
        }

        return objValue;
    }

    private <T> T getInstance(Class<T> classType) {
        if (classType == null) {
            return null;
        }

        try {
            var constructor = classType.getDeclaredConstructor();
            constructor.setAccessible(true);

            return constructor.newInstance();
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e
        ) {
            throw new RuntimeException(e);
        }
    }

    private <T> void setObjectFields(Map<String, Object> fieldValues, T obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);

            System.out.println(fieldValues);

            Object objValue;
            if (field.getType().getClassLoader() == ClassLoader.getSystemClassLoader()) {
                objValue = objectFromJson(fieldValues, field.getType());
            } else {
                Object jsonValue = fieldValues.get(field.getName());
                objValue = objectValueFromJson(jsonValue, field.getType());
            }

            field.set(obj, objValue);
        }
    }
}
