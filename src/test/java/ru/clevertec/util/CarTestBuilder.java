package ru.clevertec.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import ru.clevertec.model.Car;

@NoArgsConstructor
@AllArgsConstructor(staticName = "car")
@With
public class CarTestBuilder implements TestBuilder<Car> {

    private String make = "";
    private String model = "";
    private int year = 0;

    @Override
    public Car build() {
        final var car = new Car();

        car.setMake(make);
        car.setModel(model);
        car.setYear(year);

        return car;
    }
}
