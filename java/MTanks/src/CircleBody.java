

public class CircleBody implements Body {

	private double x, y, heading, radius;
	
	public CircleBody(double x, double y, double radius, double heading) {
		this.x = x;
		this.y = y;
		this.heading = heading;
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double h) {
		heading = h;
		while(heading > Math.PI*2) heading -= Math.PI*2;
		while(heading < 0) heading += Math.PI*2;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double r) {
		radius = r;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean checkCollision(CircleBody o) {
		return Math.sqrt((o.x - this.x) * (o.x - this.x) + (o.y - this.y)
				* (o.y - this.y)) < o.radius + this.radius;
	}

	public boolean checkCollision(BoxBody o) {
		return o.checkCollision(this);
	}

	public boolean containsPoint(double x, double y) {
		return Math.sqrt((x - this.x) * (x - this.x) + (y - this.y)
				* (y - this.y)) < this.radius;
	}

	public boolean checkCollision(Body o) {
		if (o instanceof CircleBody)
			return checkCollision((CircleBody) o);
		return checkCollision((BoxBody) o);
	}

	@Override
	public double distance(Body o) {
		return Math.sqrt((o.getX() - this.x) * (o.getX() - this.x) + (o.getY() - this.y)
				* (o.getY() - this.y));
	}

}
