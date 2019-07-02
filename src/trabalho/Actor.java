package trabalho;

import java.util.List;


public interface Actor {

    abstract public void act (Field field, Field updatedField,List<Actor> actors);
    abstract public boolean isExists();
}
