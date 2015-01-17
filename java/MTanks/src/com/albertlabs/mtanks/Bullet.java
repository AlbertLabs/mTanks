package com.albertlabs.mtanks;

public class Bullet implements Printable {
	private CircleBody body;

	Bullet(double x, double y, double radius, double heading) {
		body = new CircleBody(x, y, radius, heading);
	}

	public PrintData print() {
		return new PrintData(body.getX(), body.getY(), body.getRadius(),
				body.getRadius(), body.getHeading());
	}

}
