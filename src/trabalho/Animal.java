package trabalho;

import java.util.List;
import java.util.Random;

/**
 *
 * The class reponsable for the animals we see in the field
 *
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus
 * Oliveira.
 * @version 2.0
 */
public abstract class Animal implements Actor {

    //Animal is alive or not.
    private boolean alive;
    //The main field  where the actor are
    private Field field;
    //The location of the animal
    private Location location;
    //The age that animal is die;
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     *
     * @param exists Animal is alive or not.
     * @param field The main field where the actor are
     * @param location The location of the animal
     */
    public Animal(boolean exists, Field field, Location location) {
        age = 0;
        this.alive = exists;
        this.field = field;
        this.location = location;
    }

    /**
     * Check whether the animal is alive or not.
     * @return True if the fox is still alive.
     */
    public boolean isExists() {
        return alive;
    }

    /**
     * Set location of the animal.
     * @param row vertical coordinate of the location.
     * @param col coordinate of the horizontal location.
     */
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Define a location of the animal
     *
     * @param location location of the animal.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return Location of the animal
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @return The field of the animal
     */
    public Field getField() {
        return field;
    }

    /**
     * Define if animal is alive or not
     *
     * @param alive Animal is alive or not
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    public boolean canBreed() {
        return age >= getBreedingAge();
    }

    /**
     * Increase the age. This could result in the rabbit's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setAlive(false);
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getBreedingProb()) {
            births = rand.nextInt(getMaxAge()) + 1;
        }
        return births;
    }

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public void randomAge(boolean randomAge) {
        age = 0;
        if (randomAge) {
            age = rand.nextInt(getMaxAge());
        }
    }
    
    /**
     * Reproduction of the animals
     * @param newAnimals List the new animals generate of reproduction 
     */
    public void giveBirth(List<Actor> newAnimals) {
        // New foxes are born into adjacent locations.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal newAnimal = getAnimal(false);
            newAnimals.add(newAnimal);

        }
    }

    /**
     *
     * @return The animal Bredding age.
     */
    abstract protected int getBreedingAge();

    /**
     *
     * @return The animal Max age .
     */
    abstract protected int getMaxAge();

    /**
     *
     * @return The animal Max Litter.
     */
    abstract protected int getMaxLitter();

    /**
     *
     * @return The animal Breeding Probality.
     */

    abstract protected double getBreedingProb();

    /**
     *
     * @param exists if the animal is alive or not.
     * @return The Animal it self.
     */

    abstract protected Animal getAnimal(boolean exists);
}
