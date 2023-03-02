package ru.clevertec.parser;

import ru.clevertec.exception.JsonParseException;
import ru.clevertec.io.JsonReader;
import ru.clevertec.io.JsonWriter;

public class JsonParserImpl implements JsonParser {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    public JsonParserImpl(JsonWriter jsonWriter, JsonReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    @Override
    public String toJson(Object obj) {
        try {
            return jsonWriter.objectToJson(obj);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> classType) {
        try {
            return jsonReader.objectFromJson(json, classType);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
