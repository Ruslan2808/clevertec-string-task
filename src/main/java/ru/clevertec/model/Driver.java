package ru.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Driver {

    private String name;
    private int age;
    private String[] pets;
    private boolean isMarried;
    private double salary;
    private String company;
    private List<Character> drivingCategories;
    private Map<Integer, Car> cars;

}
