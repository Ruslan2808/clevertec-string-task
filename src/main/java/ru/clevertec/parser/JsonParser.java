package ru.clevertec.parser;

public interface JsonParser {
    <T> T fromJson(String json, Class<T> classType);
    String toJson(Object obj);
}
