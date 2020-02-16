import java.util.List;
import java.util.Random;

public abstract class Animal {

	protected int age;
	protected boolean alive;
	protected Location location;
	protected int BREEDING_AGE;
	protected int MAX_AGE;
	protected double BREEDING_PROBABILITY;
	protected int MAX_LITTER_SIZE;
	protected Random rand = new Random();

	
	public Animal() {
		age = 0;
		alive = true;
	}

	public abstract void act(Field currentField, Field updatedField, List<Animal> babies);
		
	
	public void incrementAge() {
		age++;
		if (age > MAX_AGE) {
			alive = false;
		}
	}

	public int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
			births = rand.nextInt(MAX_LITTER_SIZE) + 1;
		}
		return births;
	}

	public boolean canBreed() {
		return age >= BREEDING_AGE;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setEaten() {
		alive = false;
	}

	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
