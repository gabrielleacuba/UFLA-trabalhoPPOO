package trabalho;

import java.util.List;

/**
 * 
 * The class reponsable for the actor we see in the field.
 *
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus Oliveira.
 * @version 2.0
 */
public interface Actor {
    
    /**
     * Define the actions of actors in the field
     * @param updatedField The new field that is going to overplace the main field.
     * @param actors the list of actors.
     */
    abstract public void act (Field updatedField,List<Actor> actors);
    /**
     * 
     * @return True if the fox is still alive.
     */
    abstract public boolean isExists();
}
