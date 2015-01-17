package com.albertlabs.mtanks;

import java.util.ArrayList;
import java.util.List;

public class Bullet implements WorldObject {
	private CircleBody body;

	private double xspeed;
	private double yspeed;
	private boolean alive = true;

	public static final double BULLET_DISTANCE = 500;
	private WorldObject parent;
	private double distLeft = BULLET_DISTANCE;

	Bullet(double x, double y, double radius, double heading, WorldObject parent) {
		body = new CircleBody(x, y, radius, heading);
		xspeed = Math.asin(heading)*5;
		yspeed = Math.acos(heading)*5;
		this.parent = parent;
	}

	public List<PrintData> print() {
		List<PrintData> list = new ArrayList<PrintData>();
		list.add(new PrintData("bullet", body.getX(), body.getY(), 2 * body
				.getRadius(), 2 * body.getRadius(), body.getHeading(), 0, 0));
		return list;
	}

	public void loop() {
		body.setX(body.getX() + xspeed);
		body.setY(body.getY() + yspeed);
		distLeft -= Math.sqrt(xspeed * xspeed + yspeed * yspeed);
		if (distLeft <= 0) {
			die();
		}
	}

	public Body getBody() {
		return body;
	}

	public void collide(WorldObject o) {
		if( o != parent)
			die();
	}

	public boolean alive() {
		return alive;
	}

	public void die() {
		GameSim.world.remove(this);
		alive = false;
	}

}
