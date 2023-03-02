package ru.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {

    private String make;
    private String model;
    private int year;

}
