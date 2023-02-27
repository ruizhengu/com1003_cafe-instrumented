package uk.ac.sheffield.com1003.cafe;

import java.util.Arrays;

import uk.ac.sheffield.com1003.cafe.exceptions.TooManyIngredientsException;
import uk.ac.sheffield.com1003.cafe.ingredients.Ingredient;

public class Recipe {
    public enum Size { SMALL, REGULAR, LARGE };
    private String name;
    private double price;
    private Size size;
    private Ingredient[] ingredients;

    public Recipe(String name, double price) {
        this(name, price, Size.REGULAR, 3);
        
    }

    public Recipe(String name, double price, Size size, int numberOfIngredients) {
        this.name = name;
        this.price = price;
        this.size = size;
        ingredients = new Ingredient[numberOfIngredients];
    }

    /**
     * Add ingredient to recipe if it does not already exist.
     * If ingredient already exists, replace.
     * @param ingredient Ingredient to add to recipe.
     */
    public void addIngredient(Ingredient newIngredient) throws TooManyIngredientsException {
        int i = 0;
        while (i < ingredients.length) {
            if (ingredients[i] == null || ingredients[i].getName().equals(newIngredient.getName())) {
                ingredients[i] = newIngredient;
                return;
            } else
                i++;
        }
        if (i < ingredients.length)
            ingredients[i] = newIngredient;
        else
            throw new TooManyIngredientsException();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Checks whether recipe is ready to be used.
     * @return True if all ingredients of the recipe have been added and false otherwise
     */
    public boolean isReady() {
        for (Ingredient ingredient : ingredients) {
            if (ingredient == null)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipe [name=" + name + ", price=" + price + ", size=" + size + ", ingredients="
                + Arrays.toString(ingredients) + "]";
    }

    
}