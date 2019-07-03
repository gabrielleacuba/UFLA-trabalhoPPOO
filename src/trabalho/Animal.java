
package trabalho;

import java.util.List;
import java.util.Random;


public abstract class Animal implements Actor{
    // Animal vivo ou não.
    private boolean alive;
    // Posição do animal
    private Field field;
    private Location location;
    private int age;
    
    private static final Random rand = new Random();

    public Animal (boolean exists, Field field, Location location) {
    	age = 0;
    	alive = exists;
    	this.field = field;
    	this.location = location;
    }
    /**
     * Verifica se o animal está vivo ou não     *
     * @return True se o animal estiver vivo.
     */
    public boolean isExists() {
        return alive;
    }

    /**
     * Set a localização de um animal.
     *
     * @param row coordenada vertical da localização.
     * @param col coordenada horizontal da localização.
     */
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     *Localização de um animal
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
    //Retorna a localização de um animal
    public Location getLocation(){
        return location;
    }
    
    public Field getField() {
    	return field;
    }
    //Determina se o animal está vivo ou não
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    
    public boolean canBreed(){
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
    
    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getBreedingProb()) {
            births = rand.nextInt(getMaxAge()) + 1;
        }
        return births;
    }
    
    public void randomAge(boolean randomAge){
        age = 0;
        if (randomAge) {
            age = rand.nextInt(getMaxAge());
        }
    }

    public void giveBirth(List<Actor> newAnimals) {
    	// New foxes are born into adjacent locations.
    	List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size()> 0; b++) {
        	Location loc = free.remove(0);
            Animal newAnimal =  getAnimal(false);
            newAnimals.add(newAnimal);

        }
    }
    abstract protected int getBreedingAge();
    abstract protected int getMaxAge();
    abstract protected int getMaxLitter();
    abstract protected double getBreedingProb();
    abstract protected Animal getAnimal(boolean exists);
}
