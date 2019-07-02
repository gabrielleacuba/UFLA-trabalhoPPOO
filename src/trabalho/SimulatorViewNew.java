package trabalho;

import java.awt.Color;

public interface SimulatorViewNew {
	void setColor(Class cl, Color color);
	boolean isViable(Field field);
	void showStatus(int step,Field field);
}
