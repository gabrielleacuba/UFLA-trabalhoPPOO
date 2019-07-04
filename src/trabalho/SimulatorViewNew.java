package trabalho;

import java.awt.Color;
/**
 * An interface that stablish the methods of what we see in the field.
 *
 * @author Gabrielle Almeida, Luís Felype Fioravanti Ferreira and Matheus Oliveira.
 * @version 2.0
 */
public interface SimulatorViewNew {
	/**
	 * 
	 * @param cl The class that will receive a color.
	 * @param color The color that will paint the class.
	 */
	void setColor(Class cl, Color color);
	/**
	 * 
	 * @param field
	 * @return Represents if its viable or not.
	 */
	boolean isViable(Field field);
	
	/**
	 * 
	 * @param step Represents the step of the simulation.
	 * @param field The main field where the actors are.
	 */
	void showStatus(int step,Field field);
}
