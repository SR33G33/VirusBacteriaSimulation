import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Fox extends Organism implements Serializable {

	private int BEAR_FOOD_VALUE;

	private int RABBIT_FOOD_VALUE;
	private int foodLevel;

	public Fox(boolean randomAge) {
		super();
		BREEDING_AGE = 3;
		MAX_AGE = 15;
		BREEDING_PROBABILITY = 0.09;
		MAX_LITTER_SIZE = 3;
		RABBIT_FOOD_VALUE = 5;

		rand = new Random();

		age = 0;
		alive = true;
		if (randomAge) {
			age = rand.nextInt(MAX_AGE);
			foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = RABBIT_FOOD_VALUE;
		}
	}

	public void act(Field currentField, Field updatedField, List<Organism> newFoxes) {
		incrementAge();
		incrementHunger();
		if (alive) {
			// New foxes are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Fox newFox = new Fox(false);
				newFox.setFoodLevel(this.foodLevel);
				newFoxes.add(newFox);
				Location loc = updatedField.randomAdjacentLocation(location);
				newFox.setLocation(loc);
				updatedField.put(newFox, loc);
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
			
			if (animal instanceof Mouse) {
				Mouse mouse = (Mouse) animal;
				if (mouse.isAlive()) {
					mouse.setEaten();
					foodLevel = RABBIT_FOOD_VALUE;
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
