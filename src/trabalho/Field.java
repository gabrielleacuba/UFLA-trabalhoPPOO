package trabalho;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions. Each position is able to
 * store a single animal.
 *
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus Oliveira.
 * @version 2.0
 */
public class Field {

    private static final Random rand = new Random();

    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    private Actor[][] field;

    /**
     * Represent a field of the given dimensions.
     *
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
        field = new Actor[depth][width];
    }

    /**
     * Empty the field.
     */
    public void clear() {
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Place an animal at the given location. If there is already an animal at
     * the location it will be lost.
     *
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Actor animal, int row, int col) {
        place(animal, new Location(row, col));
    }

    /**
     * Place an animal at the given location. If there is already an animal at
     * the location it will be lost.
     *
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Actor animal, Location location) {
        field[location.getRow()][location.getCol()] = animal;
    }

    /**
     * Return the animal at the given location, if any.
     *
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Actor getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Return the animal at the given location, if any.
     *
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Actor getObjectAt(int row, int col) {
        return field[row][col];
    }

    /**
     * Generate a random location that is adjacent to the given location, or is
     * the same location. The returned location will be within the valid bounds
     * of the field.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area. This may be the same
     * object as the location parameter.
     */
    public Location randomAdjacentLocation(Location location) {
        int row = location.getRow();
        int col = location.getCol();
        // Generate an offset of -1, 0, or +1 for both the current row and col.
        int nextRow = row + rand.nextInt(3) - 1;
        int nextCol = col + rand.nextInt(3) - 1;
        // Check in case the new location is outside the bounds.
        if (nextRow < 0 || nextRow >= depth || nextCol < 0 || nextCol >= width) {
            return location;
        } else if (nextRow != row || nextCol != col) {
            return new Location(nextRow, nextCol);
        } else {
            return location;
        }
    }

    /**
     * Try to find a free location that is adjacent to the given location. If
     * there is none, then return the current location if it is free. If not,
     * return null. The returned location will be within the valid bounds of the
     * field.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area. This may be the same
     * object as the location parameter, or null if all locations around are
     * full.
     */

    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

 /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }


    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}
