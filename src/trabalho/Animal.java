
package trabalho;

import java.util.List;
import java.util.Random;


public abstract class Animal implements Actor{
    // Animal vido ou não.
    private boolean alive;
    // Posição do animal
    private Location location;
    private int age;
    
    private static final Random rand = new Random();

    public Animal (boolean exists) {
    	age = 0;
    	alive = exists;
    }
    /**
     * Verifica se o animal está vivo ou não     *
     * @return True se o animal estiver vivo.
     */
    public boolean isAlive() {
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

    public void giveBirth(List<Actor> newAnimals, Field updatedField) {
    	// New foxes are born into adjacent locations.
        int births = breed();
        for (int b = 0; b < births; b++) {
            Animal newAnimal =  getAnimal(false);
            
            newAnimals.add(newAnimal);
            Location loc = updatedField.randomAdjacentLocation(getLocation());
            newAnimal.setLocation(loc);
            updatedField.place(newAnimal, loc);
        }
    }
    abstract protected int getBreedingAge();
    abstract protected int getMaxAge();
    abstract protected int getMaxLitter();
    abstract protected double getBreedingProb();
    abstract protected Animal getAnimal(boolean exists);
}
