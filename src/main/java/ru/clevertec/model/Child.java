package ru.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Child {

    private String name;
    private int age;
    private boolean isSchoolboy;
    private String[] toys;
    private double pocketMoney;
    private char firstLetterSurname;
    private ToyCar toyCar;

}
