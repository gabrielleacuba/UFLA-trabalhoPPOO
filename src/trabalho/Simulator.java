package trabalho;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing rabbits and
 * foxes.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Simulator {

    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;
    

    // The list of animals in the field
    private List<Actor> animals;
    // The list of animals just born
    private List<Actor> newAnimals;
    // The current state of the field.
    private Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private AnimatedView view;
    //metodo a parte 
    private PopulateGeneration population;
    

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList<>();
        newAnimals = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);
        //lake = new GenerateLake(field);
        population = new PopulateGeneration();

        // Create a view of the state of each location in the field.
        view = new AnimatedView(depth, width);
        view.setColor(Fox.class, Color.green);
        view.setColor(Rabbit.class, Color.yellow);
        view.setColor(Coyote.class, Color.red);
        view.setColor(Lake.class, Color.blue);
        view.setColor(Trap.class, Color.black);

        // Setup a valid starting point.
        reset();
        runLongSimulation();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation() {
        simulate(100);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            try {Thread.sleep(200);}catch(Exception erro) {}
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        newAnimals.clear();

        // let all animals act
        for (Iterator<Actor> iter = animals.iterator(); iter.hasNext();) {
            Actor animal = iter.next();
            animal.act(updatedField,newAnimals);
            
            if(!animal.isExists() ){
                iter.remove();
              
                
            } else {
                //System.out.println("found unknown animal");
            }
        }
        // add new born animals to the list of animals
        animals.addAll(newAnimals);

        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();
        population.populate(field,animals);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

 }
