package trabalho;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Coyote extends Animal{

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 7;
    // The age to which a fox can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.11;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single FOX. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 7;
    private static final int RABBIT_FOOD_VALUE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Individual characteristics (instance fields).
    // The fox's age.
    private int age;

    //Nivel alimentar da raposa que é aumentado comendo coelhos
    private int foodLevel;
    
    public Coyote(boolean randomAge, Field field, Location location) {
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
    public void act(Field updatedField, List<Actor> newCoyotes) {
        incrementAge();
        incrementHunger();
        Field field = getField();
        if (isExists()) {
            giveBirth(newCoyotes);
            // Move towards the source of food if found.
            Location newLocation = findFood(field, getLocation());
            
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
            else if (actors instanceof Fox) {
            	Fox fox = (Fox) actors;
                if (fox.isExists()) {
                    fox.setEaten();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
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
        Coyote coyote = new Coyote(false,getField(),getLocation());
        return coyote;
    }
    
    
}
