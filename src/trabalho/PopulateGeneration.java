package trabalho;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopulateGeneration {
	
	// The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.05;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.09;
    // The probability that a rabbit will be created in any given grid position.
    private static final double COYOTE_CREATION_PROBABILITY = 0.01;
    
	/**
     * Populate the field with foxes and rabbits.
     */
    public void populate(Field field,List<Actor> actor) {
        Random rand = new Random();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
            	Location loc = new Location(row, col);
            	
                if (rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true,field,loc);
                    actor.add(fox);
                    field.place(fox, row, col);
                } else if (rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true,field,loc);
                    actor.add(rabbit);
                    field.place(rabbit, row, col);
                }
	            else if (rand.nextDouble() <= COYOTE_CREATION_PROBABILITY) {
	                Coyote coyote = new Coyote(true,field,loc);
	                actor.add(coyote);
	                field.place(coyote, row, col);
	            }
                // else leave the location empty.
            }
        }
        Collections.shuffle(actor);
    }
}
