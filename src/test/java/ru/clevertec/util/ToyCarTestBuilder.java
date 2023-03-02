package ru.clevertec.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import ru.clevertec.model.ToyCar;

@NoArgsConstructor
@AllArgsConstructor(staticName = "toyCar")
@With
public class ToyCarTestBuilder implements TestBuilder<ToyCar> {

    private String title = "";
    private Double price = 0.0;

    @Override
    public ToyCar build() {
        final var toyCar = new ToyCar();

        toyCar.setTitle(title);
        toyCar.setPrice(price);

        return toyCar;
    }
}
