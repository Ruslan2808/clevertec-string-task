package ru.clevertec.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import ru.clevertec.model.Child;
import ru.clevertec.model.ToyCar;

@NoArgsConstructor
@AllArgsConstructor(staticName = "child")
@With
public class ChildTestBuilder implements TestBuilder<Child> {

    private String name;
    private int age;
    private boolean isSchoolboy;
    private String[] toys;
    private double pocketMoney;
    private char firstLetterSurname;
    private ToyCar toyCar;

    @Override
    public Child build() {
        final var child = new Child();

        child.setName(name);
        child.setAge(age);
        child.setSchoolboy(isSchoolboy);
        child.setToys(toys);
        child.setPocketMoney(pocketMoney);
        child.setFirstLetterSurname(firstLetterSurname);
        child.setToyCar(toyCar);

        return child;
    }
}
