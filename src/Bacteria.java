import java.io.Serializable;
import java.util.List;
import java.util.Random;


public class Bacteria extends Organism implements Serializable {

    public boolean isResistant = false;

    public Bacteria(boolean randomAge, boolean isResistant) {
        super();
        BREEDING_AGE = 0;
        MAX_AGE = 87600;
        BREEDING_PROBABILITY = 1;
        LITTER_SIZE = 3;
        rand = new Random();
        this.isResistant = isResistant;

        age = 0;
        alive = true;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }


    public void act(Field currentField, Field updatedField, List<Organism> newBacterium) {
        incrementAge();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                if (Main.hoursPassed > 2000 && (int) (Math.random() * 100) < 1) {
                    Bacteria newBacteria = new Bacteria(false, false);
                }
                Bacteria newBacteria = new Bacteria(false, false);
                newBacterium.add(newBacteria);
                Location loc = updatedField.randomAdjacentLocation(location);
                newBacteria.setLocation(loc);
                updatedField.put(newBacteria, loc);
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