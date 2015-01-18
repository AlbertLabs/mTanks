package com.albertlabs.mtanks;

public interface Body {
	public double getX();

	public double getY();

	public double getHeading();

	public void setHeading(double h);

	public void setX(double x);

	public void setY(double y);

	public boolean checkCollision(CircleBody o);

	public boolean checkCollision(BoxBody o);

	public boolean containsPoint(double x, double y);

	public boolean checkCollision(Body b);
	
	public double distance(Body b);
}
