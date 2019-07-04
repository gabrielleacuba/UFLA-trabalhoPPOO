package trabalho;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 *
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus Oliveira.
 * @version 2.0
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
     * @param field The main field where the actors are.
     * @param location The location that rabbit is going to born.
     */

    public Rabbit(boolean randomAge, Field field, Location location) {
    	super(randomAge,field,location);
        setAlive(true);
        randomAge(randomAge);
    }

    /**
     * This is what the rabbit does. Sometimes
     * it will breed or die of old age.
     *@param updatedField The new field that is going to overplace the main field.
     *@param newRabbits The list of newborn rabbits.
     */
    @Override
    public void act(Field updatedField, List<Actor> newRabbits) {
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

    /**
     * @return the Rabbit's breeding age.
     */
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return The Rabbit's max age.
     */
    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return The Rabbit's breeding probability.
     */
    @Override
    protected double getBreedingProb() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return The Rabbit's max litter.
     */
    @Override
    protected int getMaxLitter() {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * @return the animal itself.
     * @param exists If the rabbit exists or not.
     */
    @Override
    protected Animal getAnimal(boolean exists) {
        Rabbit rabbit = new Rabbit(false,getField(),getLocation());
        return rabbit;
    }

}
