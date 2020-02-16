import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling. Modified by David Dobervich
 *         2007-2013
 * @version 2006.03.30
 */
public class Rabbit extends Organism implements Serializable {

	public Rabbit(boolean randomAge) {
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

	
	public void act(Field currentField, Field updatedField, List<Organism> newRabbits) {
		incrementAge();
		if (alive) {
			int births = breed();
			for (int b = 0; b < births; b++) {
				Rabbit newRabbit = new Rabbit(false);
				newRabbits.add(newRabbit);
				Location loc = updatedField.randomAdjacentLocation(location);
				newRabbit.setLocation(loc);
				updatedField.put(newRabbit, loc);
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
