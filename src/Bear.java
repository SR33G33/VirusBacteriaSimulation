import java.io.Serializable;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Bear extends Animal implements Serializable {

	private int RABBIT_FOOD_VALUE = 10;
	private int FOX_FOOD_VALUE = 10;
	private int foodLevel;

	public Bear(boolean randomAge) {

		BREEDING_AGE = 4;
		MAX_AGE = 15;
		BREEDING_PROBABILITY = 0.08;
		MAX_LITTER_SIZE = 2;
		RABBIT_FOOD_VALUE = 6;
		FOX_FOOD_VALUE = 6;
		rand = new Random();

		age = 0;
		alive = true;
		if (randomAge) {
			age = rand.nextInt(MAX_AGE);
			foodLevel = rand.nextInt(FOX_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = RABBIT_FOOD_VALUE;
		}
	}

	public void act(Field currentField, Field updatedField, List<Animal> newBears) {
		incrementAge();
		incrementHunger();
		if (alive) {
			int births = breed();
			for (int b = 0; b < births; b++) {
				Bear newBear = new Bear(false);
				newBear.setFoodLevel(this.foodLevel);
				newBears.add(newBear);
				Location loc = updatedField.randomAdjacentLocation(location);
				newBear.setLocation(loc);
				updatedField.put(newBear, loc);
			}
			// Move towards the source of food if found.
			Location newLocation = findFood(currentField, location);
			if (newLocation == null) { // no food found - move randomly
				newLocation = updatedField.freeAdjacentLocation(location);
			}
			if (newLocation != null) {
				setLocation(newLocation);
				updatedField.put(this, newLocation);
			} else {
				// can neither move nor stay - overcrowding - all locations
				// taken
				alive = false;
			}
		}
	}

	private void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			alive = false;
		}
	}

	private Location findFood(Field field, Location location) {
		List<Location> adjacentLocations = field.adjacentLocations(location);

		for (Location where : adjacentLocations) {
			Object animal = field.getObjectAt(where);
			if (animal instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) animal;
				if (rabbit.isAlive()) {
					rabbit.setEaten();
					foodLevel = RABBIT_FOOD_VALUE;
					return where;
				}
			}
			if (animal instanceof Fox) {
				Fox fox = (Fox) animal;
				if (fox.isAlive()) {
					fox.setEaten();
					foodLevel = FOX_FOOD_VALUE;
					return where;
				}
			}
			
		}

		return null;
	}

	public void setFoodLevel(int fl) {
		this.foodLevel = fl;
	}
}
