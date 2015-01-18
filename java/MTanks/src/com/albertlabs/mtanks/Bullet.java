package com.albertlabs.mtanks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Bullet implements WorldObject {
	private CircleBody body;

	private double xspeed;
	private double yspeed;
	private boolean safe = true;

	public static final double BULLET_DISTANCE = 500;
	public Tank parent;
	private double distLeft = BULLET_DISTANCE;

	Bullet(double x, double y, double radius, double heading, Tank parent) {
		body = new CircleBody(x, y, radius, heading);
		xspeed = Math.cos(heading)*5;
		yspeed = Math.sin(heading)*5;
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
		
		if(safe)
			safe = GameSim.world.collidingWith(this).size() != 0;
	}

	public Body getBody() {
		return body;
	}

	public void collide(WorldObject o) {

		if(!o.equals(this.parent)){
			die();
		}
	}

	public void die() {
		GameSim.objectDeath(this);
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}

}
