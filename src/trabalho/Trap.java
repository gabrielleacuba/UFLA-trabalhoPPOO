/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.util.List;

/**
 *
 * @author gabri
 */
public class Trap implements Actor {

    private boolean alive;

    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean active){
        this.alive = active;
    }

    @Override
    public void act(Field field, Field updatedField,List<Actor> actors) {

    }

}
