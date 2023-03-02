package ru.clevertec.util;

import lombok.*;

import ru.clevertec.model.Car;
import ru.clevertec.model.Driver;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor(staticName = "driver")
@With
public class DriverTestBuilder implements TestBuilder<Driver> {

    private String name = "John Doe";
    private int age = 30;
    private String[] pets = {};
    private boolean isMarried = false;
    private double salary = 0.0;
    private String company = "Volkswagen AG";
    private List<Character> drivingCategories = Collections.emptyList();
    private Map<Integer, Car> cars = Map.of();

    @Override
    public Driver build() {
        final var driver = new Driver();

        driver.setName(name);
        driver.setAge(age);
        driver.setPets(pets);
        driver.setMarried(isMarried);
        driver.setSalary(salary);
        driver.setCompany(company);
        driver.setDrivingCategories(drivingCategories);
        driver.setCars(cars);

        return driver;
    }
}
