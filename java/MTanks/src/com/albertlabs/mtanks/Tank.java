package com.albertlabs.mtanks;

import java.util.ArrayList;
import java.util.List;


public class Tank implements WorldObject {

	private BoxBody body;
	private double health;
	private boolean alive;
	private double speed;
	//private int moveTime;
	
	private double turretAngle = 0;
	private double sensorAngle = 0;

	public BoxBody getBody(){
		return body;
	}

	public void begin(){ //accessible to user
		
	}
	
	public double getHealth() {
		return health;
	}

	public final static double MAX_HEALTH = 100;

	Tank(double x, double y, double width, double height, double heading) {
		body = new BoxBody(x, y, width, height, heading);
	}

	public List<PrintData> print() {

		List<PrintData> list = new ArrayList<PrintData>();
		list.add(new PrintData("tank", body.getX(), body.getY(), body.getWidth(),
				body.getHeight(), body.getHeading(), 
						health, MAX_HEALTH));
		list.add(new PrintData("turret", body.getX(), body.getY(), body.getWidth(),
				body.getHeight(), body.getHeading()+turretAngle, 0, 0));
		list.add(new PrintData("sensor", body.getX(), body.getY(), body.getWidth(),
				body.getHeight(), body.getHeading()+turretAngle+sensorAngle, 0, 0));
		return list;
	}

	public void act() { //used for only tank, accessible to user
		// TODO Auto-generated method stub
		move(.0000000000001);
	}

	public void loop() { //used for all objects in world, not accessible to user
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(WorldObject o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		alive = false;
	}

	@Override
	public boolean alive() {
		return alive;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public double getSpeed(){
		return speed;
	}

	public void turn (double turnSpeed) { //turnSpeed is in radians
		body.setHeading(turnSpeed);
	}
	
	public void move(double speed) { //negative moves backwards positive moves forwards
		double direction = body.getHeading();
		double xDir = Math.cos(direction);
		double yDir = Math.sin(direction);
		body.setX(xDir + speed);
		body.setY(yDir + speed);
	}
	

}