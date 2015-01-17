package com.albertlabs.mtanks;

public class BoxBody implements Body {

	private double x, y, heading, width, height, diag, rectAngle;
	private String image;
	
	public BoxBody(double x, double y, double width, double height,
			double heading, String image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.heading = heading;
		this.image = image;
		diag = Math.sqrt((width/2)*(width/2) + (height/2)*(height/2));
	    rectAngle = Math.atan2(height/2, width/2);
	}

	public boolean containsPoint(double x, double y) {
		if (this.getHeading() == 0) 
			return Math.abs(this.getX() - x) < this.width() / 2
					&& Math.abs(this.getY() - y) < this.height() / 2;

		double tx = Math.cos(Math.toRadians(this.getHeading())) * x
				- Math.sin(Math.toRadians(this.getHeading())) * y;
		double ty = Math.cos(Math.toRadians(this.getHeading())) * y
				+ Math.sin(Math.toRadians(this.getHeading())) * x;

		double cx = Math.cos(Math.toRadians(this.getHeading())) * this.getX()
				- Math.sin(Math.toRadians(this.getHeading())) * this.getY();
		double cy = Math.cos(Math.toRadians(this.getHeading())) * this.getY()
				+ Math.sin(Math.toRadians(this.getHeading())) * this.getX();

		return Math.abs(cx - tx) < this.width() / 2
				&& Math.abs(cy - ty) < this.height() / 2;
	}

	public double topLeftX() {
		return x - diag * Math.cos(-rectAngle + Math.toRadians(heading));
	}

	public double topLeftY() {
		return y - diag * Math.sin(-rectAngle + Math.toRadians(heading));
	}

	public double topRightX() {
		return x + diag * Math.cos(rectAngle + Math.toRadians(heading));
	}

	public double topRightY() {
		return y + diag * Math.sin(rectAngle + Math.toRadians(heading));
	}

	public double bottomLeftX() {
		return x + diag * Math.cos(-rectAngle + Math.toRadians(heading));
	}

	public double bottomLeftY() {
		return y + diag * Math.sin(-rectAngle + Math.toRadians(heading));
	}

	public double bottomRightX() {
		return x - diag * Math.cos(rectAngle + Math.toRadians(heading));
	}

	public double bottomRightY() {
		return y - diag * Math.sin(rectAngle + Math.toRadians(heading));
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
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
		this.heading = h;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean checkCollision(CircleBody o) {
		double tx, ty, cx, cy;

		if (this.getHeading() == 0) { 
			tx = o.getX();
			ty = o.getY();
			cx = this.getX();
			cy = this.getY();
		} else {
			tx = Math.cos(Math.toRadians(this.getHeading())) * o.getX()
					- Math.sin(Math.toRadians(this.getHeading())) * o.getY();
			ty = Math.cos(Math.toRadians(this.getHeading())) * o.getY()
					+ Math.sin(Math.toRadians(this.getHeading())) * o.getX();
			cx = Math.cos(Math.toRadians(this.getHeading())) * this.getX()
					- Math.sin(Math.toRadians(this.getHeading())) * this.getY();
			cy = Math.cos(Math.toRadians(this.getHeading())) * this.getY()
					+ Math.sin(Math.toRadians(this.getHeading())) * this.getX();
		}

		return containsPoint(o.getX(), o.getY())
				|| testCircleToSegment(tx, ty, o.getRadius(), cx - this.width()
						/ 2, cy + this.height() / 2, cx + this.width() / 2, cy
						+ this.height() / 2)
				|| testCircleToSegment(tx, ty, o.getRadius(), cx + this.width()
						/ 2, cy + this.height() / 2, cx + this.width() / 2, cy
						- this.height() / 2)
				|| testCircleToSegment(tx, ty, o.getRadius(), cx + this.width()
						/ 2, cy - this.height() / 2, cx - this.width() / 2, cy
						- this.height() / 2)
				|| testCircleToSegment(tx, ty, o.getRadius(), cx - this.width()
						/ 2, cy - this.height() / 2, cx - this.width() / 2, cy
						+ this.height() / 2);
	}

	private Point[] getPoints() {
		return new Point[] { new Point(this.topLeftX(), this.topLeftY()),
				new Point(this.topRightX(), this.topRightY()),
				new Point(this.bottomLeftX(), this.bottomLeftY()),
				new Point(this.bottomRightX(), this.bottomRightY()) };
	}

	public double[] getXs() {
		return new double[] { this.topLeftX(), this.topRightX(),
				this.bottomLeftX(), this.bottomRightX() };
	}

	public double[] getYs() {
		return new double[] { this.topLeftY(), this.topRightY(),
				this.bottomLeftY(), this.bottomRightY() };
	}

	private class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	public boolean checkCollision(Body o) {
		if (o instanceof CircleBody)
			return checkCollision((CircleBody) o);
		return checkCollision((BoxBody) o);	}

	public boolean checkCollision(BoxBody o) {
		for (int x = 0; x < 2; x++) {
			BoxBody polygon = (x == 0) ? this : o;

			for (int i1 = 0; i1 < polygon.getPoints().length; i1++) {
				int i2 = (i1 + 1) % polygon.getPoints().length;
				Point p1 = polygon.getPoints()[i1];
				Point p2 = polygon.getPoints()[i2];

				Point normal = new Point(p2.y - p1.y, p1.x - p2.x);

				double minA = Double.MAX_VALUE;
				double maxA = Double.MIN_VALUE;

				for (Point p : this.getPoints()) {
					double projected = normal.x * p.x + normal.y * p.y;

					if (projected < minA)
						minA = projected;
					if (projected > maxA)
						maxA = projected;
				}

				double minB = Double.MAX_VALUE;
				double maxB = Double.MIN_VALUE;

				for (Point p : o.getPoints()) {
					double projected = normal.x * p.x + normal.y * p.y;

					if (projected < minB)
						minB = projected;
					if (projected > maxB)
						maxB = projected;
				}

				if (maxA < minB || maxB < minA)
					return false;
			}
		}
		return true;
	}

	private boolean testCircleToSegment(double circleCenterX,
			double circleCenterY, double circleRadius, double lineAX,
			double lineAY, double lineBX, double lineBY) {
		double lineSize = Math.sqrt(Math.pow(lineAX - lineBX, 2)
				+ Math.pow(lineAY - lineBY, 2));
		double distance;

		if (lineSize == 0) {
			distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2)
					+ Math.pow(circleCenterY - lineAY, 2));
			return distance < circleRadius;
		}

		double u = ((circleCenterX - lineAX) * (lineBX - lineAX) + (circleCenterY - lineAY)
				* (lineBY - lineAY))
				/ (lineSize * lineSize);

		if (u < 0) {
			distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2)
					+ Math.pow(circleCenterY - lineAY, 2));
		} else if (u > 1) {
			distance = Math.sqrt(Math.pow(circleCenterX - lineBX, 2)
					+ Math.pow(circleCenterY - lineBY, 2));
		} else {
			double ix = lineAX + u * (lineBX - lineAX);
			double iy = lineAY + u * (lineBY - lineAY);
			distance = Math.sqrt(Math.pow(circleCenterX - ix, 2)
					+ Math.pow(circleCenterY - iy, 2));
		}
		return distance < circleRadius;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String img) {
		image = img;
	}

}
