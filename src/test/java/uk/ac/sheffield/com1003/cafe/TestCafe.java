package uk.ac.sheffield.com1003.cafe;

import org.junit.jupiter.api.Test;

import uk.ac.sheffield.com1003.cafe.Recipe.Size;
import uk.ac.sheffield.com1003.cafe.exceptions.CafeOutOfCapacityException;
import uk.ac.sheffield.com1003.cafe.exceptions.TooManyIngredientsException;
import uk.ac.sheffield.com1003.cafe.ingredients.Coffee;
import uk.ac.sheffield.com1003.cafe.ingredients.Milk;
import uk.ac.sheffield.com1003.cafe.ingredients.Water;
import uk.ac.sheffield.com1003.cafe.ingredients.Milk.Type;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

class TestCafe {

    private Recipe createEspressoRecipe() throws TooManyIngredientsException {
        Recipe espresso = new Recipe("Espresso", 1.5, Size.SMALL, 2);
        try {
            espresso.addIngredient(new Coffee());
            espresso.addIngredient(new Water());
        } catch (TooManyIngredientsException exc) {
            // This should not happen
            System.err.println("Too many ingredients");
        }
        
        return espresso;
    }
    
    @Test 
    void cafeGreeting() {
        Cafe cafe = new Cafe("Central Perk");
        System.out.println("Test being executed");
        assertEquals(cafe.greeting(), "Welcome to Central Perk");
    }

    @Test 
    void menuSize() throws TooManyIngredientsException {
        Cafe cafe = new Cafe("Central Perk");
        
        Recipe espresso = createEspressoRecipe();
        cafe.addRecipe(espresso);

        Recipe soyLatte = new Recipe("Large Soy Latte", 2.5, Size.LARGE, 3);
        soyLatte.addIngredient(new Coffee());
        soyLatte.addIngredient(new Water());
        soyLatte.addIngredient(new Milk(100, Type.SOY));
        cafe.addRecipe(soyLatte);

        assertEquals(2, cafe.getMenu().length);
        cafe.printMenu();
    }

    @Test
    void placeOrderOutOfCapacity() throws Exception {
        assertThrows(CafeOutOfCapacityException.class, () -> {
            Cafe cafe = new Cafe("Central Perk", 2, 0);
            cafe.addRecipe(createEspressoRecipe());
            cafe.placeOrder("Espresso", "Jose", 3);
        });
    }

    @Disabled
    @DisplayName("This is an example of a disabled test")
    @Test
    void failingTest() {
        fail("Forcing this test to fail");
    }
}
