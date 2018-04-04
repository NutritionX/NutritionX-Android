package org.elsys.nutritionx.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Food {
    private String name;
    private Float calories;
    private Float protein;
    private Float carbs;
    private Float fats;

    public Food() {

    }

    public Food(String name, Float calories, Float protein, Float carbs, Float fats) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Float getFats() {
        return fats;
    }

    public void setFats(Float fats) {
        this.fats = fats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
                Objects.equals(calories, food.calories) &&
                Objects.equals(protein, food.protein) &&
                Objects.equals(carbs, food.carbs) &&
                Objects.equals(fats, food.fats);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, calories, protein, carbs, fats);
    }

    public static List<Food> findByName() {
        List<Food> found = new ArrayList<>();

        IntStream.range(1, 10).forEach(i -> {
            Log.d("generated", String.valueOf(i));
            found.add(new Food(
                    "Test " + i,
                    100f,
                    100f,
                    100f,
                    100f
            ));
        });

        Log.d("generated", found.toString());
        return found;
    }
}
