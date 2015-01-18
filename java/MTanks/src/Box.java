

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Box implements WorldObject {

	BoxBody body;
	boolean alive;
	
	public Box(double i, double j, double k, double l, double m) {
		body = new BoxBody(i,j,k,l,m);
	}

	public Body getBody() {
		return body;
	}

	public List<PrintData> print() {
		List<PrintData> list = new ArrayList<PrintData>();
		list.add(new PrintData("box", body.getX(), body.getY(), body.getWidth(), body.getHeight(), body.getHeading(),0,0));
		return list;
	}

	public void collide(WorldObject o) {
		//Do nothing
	}

	public void loop() {
		//Do nothing
	}

	public void die() {
		alive = false;
	}

	public boolean alive() {
		return alive;
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}

}
