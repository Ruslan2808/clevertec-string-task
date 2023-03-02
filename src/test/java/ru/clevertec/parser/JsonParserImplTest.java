package ru.clevertec.parser;

import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.clevertec.io.JsonReader;
import ru.clevertec.io.JsonWriter;
import ru.clevertec.mapper.JsonMapper;
import ru.clevertec.model.Car;
import ru.clevertec.model.Child;
import ru.clevertec.model.Driver;
import ru.clevertec.model.ToyCar;
import ru.clevertec.util.CarTestBuilder;
import ru.clevertec.util.ChildTestBuilder;
import ru.clevertec.util.DriverTestBuilder;
import ru.clevertec.util.ToyCarTestBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JsonParserImplTest {

    private JsonParserImpl jsonParser;
    private Gson gson;

    @BeforeEach
    void setUp() {
        JsonWriter jsonWriter = new JsonWriter();
        JsonReader jsonReader = new JsonReader(new JsonMapper());
        jsonParser = new JsonParserImpl(jsonWriter, jsonReader);
        gson = new Gson();
    }

    @Test
    void checkObjectToJsonShouldReturnNullJson() {
        String expectedJson = gson.toJson(null);

        String actualJson = jsonParser.toJson(null);

        assertThat(actualJson).isEqualTo(expectedJson);
    }

    @Test
    void checkObjectToJsonShouldReturnEmptyJson() {
        Object obj = new Object();
        String expectedJson = gson.toJson(obj);

        String actualJson = jsonParser.toJson(obj);

        assertThat(actualJson).isEqualTo(expectedJson);
    }

    @ParameterizedTest
    @MethodSource(value = "carArguments")
    void checkObjectToJsonShouldReturnCarJson(Car car) {
        String expectedJson = gson.toJson(car);

        String actualJson = jsonParser.toJson(car);

        assertThat(actualJson).isEqualTo(expectedJson);
    }

    @ParameterizedTest
    @MethodSource(value = "driverArguments")
    void checkObjectToJsonShouldReturnDriverJson(Driver driver) {
        String expectedJson = gson.toJson(driver);

        String actualJson = jsonParser.toJson(driver);

        assertThat(actualJson).isEqualTo(expectedJson);
    }

    private static Stream<Arguments> carArguments() {
        return Stream.of(
                arguments(new CarTestBuilder()
                        .withMake("Volkswagen AG")
                        .build()
                ),
                arguments(new CarTestBuilder()
                        .withMake("Volkswagen AG")
                        .withModel("Tiguan")
                        .build()
                ),
                arguments(new CarTestBuilder()
                        .withMake("Volkswagen AG")
                        .withModel("Tiguan")
                        .withYear(2019)
                        .build()
                )
        );
    }

    private static Stream<Arguments> driverArguments() {
        return Stream.of(
                arguments(new DriverTestBuilder()
                        .withPets(new String[]{"Dog", "Cat"})
                        .build()
                ),
                arguments(new DriverTestBuilder()
                        .withDrivingCategories(List.of('A', 'B', 'C'))
                        .build()
                ),
                arguments(new DriverTestBuilder()
                        .withCars(Map.of(
                                1, new Car("Volkswagen", "Tiguan", 2019),
                                2, new Car("Mercedes-Benz", "AMG", 2022)
                        ))
                        .build()
                )
        );
    }

    @Test
    void checkObjectFromJsonShouldReturnNullObject() {
        String json = "";
        Object expectedObj = gson.fromJson(json, Object.class);

        Object actualObj = jsonParser.fromJson(json, Object.class);

        assertThat(actualObj).isEqualTo(expectedObj);
    }

    @Test
    void checkObjectFromJsonShouldReturnCarObject() {
        Car car = new CarTestBuilder()
                .withMake("Volkswagen AG")
                .withModel("Tiguan")
                .withYear(2019)
                .build();
        String json = gson.toJson(car);
        Object expectedObj = gson.fromJson(json, Car.class);

        Object actualObj = jsonParser.fromJson(json, Car.class);

        assertThat(actualObj).isEqualTo(expectedObj);
    }

    @Test
    void checkObjectFromJsonShouldReturnChildObject() {
        ToyCar toyCar = new ToyCarTestBuilder()
                .withTitle("BMW")
                .withPrice(15.5)
                .build();
        Child child = new ChildTestBuilder()
                .withName("John")
                .withAge(12)
                .withSchoolboy(true)
                .withToys(new String[]{"Car", "Bear", "Lego"})
                .withPocketMoney(50.0)
                .withFirstLetterSurname('A')
                .withToyCar(toyCar)
                .build();
        String json = gson.toJson(child);
        Object expectedObj = gson.fromJson(json, Child.class);

        Object actualObj = jsonParser.fromJson(json, Child.class);

        assertThat(actualObj).isEqualTo(expectedObj);
    }
}
