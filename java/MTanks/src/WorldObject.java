

import java.awt.Color;
import java.util.List;

public interface WorldObject {
	Body getBody();

	 List<PrintData> print();
	 
	 void collide(WorldObject o);

	void loop();
	void die();
	
	Color getColor();
}
