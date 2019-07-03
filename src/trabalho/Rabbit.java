package trabalho;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.


    /**
     * Create a new rabbit. A rabbit may be created with age zero (a new born)
     * or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean randomAge, Field field, Location location) {
    	super(randomAge,field,location);
        setAlive(true);
        randomAge(randomAge);
    }

    /**
     * This is what the rabbit does most of the time - it runs around. Sometimes
     * it will breed or die of old age.
     */
    @Override
    public void act(Field field, Field updatedField, List<Actor> newRabbits) {
        incrementAge();

        if (isExists()) {
            giveBirth(newRabbits);
            Location newLocation = updatedField.freeAdjacentLocation(getLocation());

            // Only transfer to the updated field if there was a free location
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                setAlive(false);
            }
        }
    }

    /**
     * Tell the rabbit that it's dead now :(
     */
    public void setEaten() {
        setAlive(false);
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected double getBreedingProb() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitter() {
        return MAX_LITTER_SIZE;
    }
    
    @Override
    protected Animal getAnimal(boolean exists) {
        Rabbit rabbit = new Rabbit(false,getField(),getLocation());
        return rabbit;
    }

}
