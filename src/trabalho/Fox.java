package trabalho;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Fox extends Animal {
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Individual characteristics (instance fields).
    // The fox's age.
    
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge, Field field, Location location) {
    	super(randomAge,field,location);
        setAlive(true);
        if (randomAge) {
            randomAge(randomAge);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Actor> newFoxes) {
        incrementAge();
        incrementHunger();
        if (isExists()) {
            giveBirth(newFoxes);
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, getLocation());
            
            if (newLocation == null) {  // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(getLocation());
            }
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
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setAlive(false);
        }
    }

    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     *
     * @param field The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field, Location location) {
    	List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it= adjacent.iterator();
        while (it.hasNext()) {
            Location where = (Location) it.next();
            Actor actors = field.getObjectAt(where);
            if (actors instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) actors;
                if (rabbit.isExists()) {
                    rabbit.setEaten();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */

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
        Fox fox = new Fox(false,getField(),getLocation());
        return fox;
    }
    
}


