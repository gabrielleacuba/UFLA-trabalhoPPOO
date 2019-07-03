package trabalho;

import java.util.List;


public interface Actor {

    abstract public void act (Field updatedField,List<Actor> actors);
    abstract public boolean isExists();
}
