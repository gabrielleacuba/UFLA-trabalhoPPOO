package trabalho;

import java.util.List;
import java.util.Random;

public abstract class Influencer implements Actor {

    private static final Random rand = new Random();

    private int row;
    private int col;
    private Field field;

    public Influencer(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void act(Field updatedField, List<Actor> actors) {
        updatedField.place(this, new Location(row, col));
    }

    @Override
    public boolean isExists() {
        return true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Field getField() {
        return field;
    }
}
