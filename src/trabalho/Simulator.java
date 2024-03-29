package trabalho;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing rabbits and
 * foxes, coyotes, trees and a lake.
 *
<<<<<<< HEAD
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus
 * Oliveira.
=======
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus Oliveira.
>>>>>>> 6260ed721a746340d7dde6c4c7eb1240c5d6d98f
 * @version 2.0
 */
public class Simulator {

    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 200;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;

    // The list of actors in the field
    private List<Actor> actors;
    // The list of actors just born
    private List<Actor> newActors;
    // The current state of the field.
    private Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private AnimatedView view;
    //Initial population.
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
        actors = new ArrayList<>();
        newActors = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);
        population = new PopulateGeneration();

        // Create a view of the state of each location in the field.
        view = new AnimatedView(depth, width);
        view.setColor(Fox.class, Color.cyan);
        view.setColor(Rabbit.class, Color.YELLOW);
        view.setColor(Coyote.class, Color.MAGENTA);
        view.setColor(Lake.class, Color.blue);
        view.setColor(Tree.class, Color.DARK_GRAY);

        // Setup a valid starting point.
        reset();
        runLongSimulation();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 50 steps.
     */
    public void runLongSimulation() {
        simulate(50);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
<<<<<<< HEAD
     *
=======
>>>>>>> 6260ed721a746340d7dde6c4c7eb1240c5d6d98f
     * @param numsteps The number of steps the simulation will take.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            try {
                Thread.sleep(200);
            } catch (Exception erro) {
            }
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        newActors.clear();

        // let all animals act
        for (Iterator<Actor> iter = actors.iterator(); iter.hasNext();) {
            Actor animal = iter.next();
            animal.act(updatedField, newActors);

            if (!animal.isExists()) {
                iter.remove();

            } else {
                //System.out.println("found unknown animal");
            }
        }
        // add new born animals to the list of animals
        actors.addAll(newActors);

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
        actors.clear();
        field.clear();
        updatedField.clear();
        population.populate(field, actors);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

}
