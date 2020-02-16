import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Mouse extends Organism implements Serializable {

	public Mouse(boolean randomAge) {
		super();
		BREEDING_AGE = 5;
		MAX_AGE = 30;
		BREEDING_PROBABILITY = 0.99;
		MAX_LITTER_SIZE = 5;
		rand = new Random();

		age = 0;
		alive = true;
		if (randomAge) {
			age = rand.nextInt(MAX_AGE);
		}
	}

	public void act(Field currentField, Field updatedField, List<Organism> newMice) {
		incrementAge();
		if (alive) {
			int births = breed();
			for (int b = 0; b < births; b++) {
				Mouse newMouse = new Mouse(false);
				newMice.add(newMouse);
				Location loc = updatedField.randomAdjacentLocation(location);
				newMouse.setLocation(loc);
				updatedField.put(newMouse, loc);
			}
			Location newLocation = updatedField.freeAdjacentLocation(location);
			// Only transfer to the updated field if there was a free location
			if (newLocation != null) {
				setLocation(newLocation);
				updatedField.put(this, newLocation);
			} else {
				// can neither move nor stay - overcrowding - all locations taken
				alive = false;
			}
		}
	}

}
