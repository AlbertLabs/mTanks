package com.albertlabs.mtanks;

import java.util.ArrayList;
import java.util.List;


public class Tank implements WorldObject {

	private BoxBody body;
	private boolean alive;
	
	private double moveSpeed;
	private double moveTime;
	private double turnSpeed;
	private double turnTime;
	
	private double turretAngle = 0;
	private double sensorAngle = 0;

	public BoxBody getBody(){
		return body;
	}

	public void begin(){ //accessible to user
		
	}
	
	public void shoot(){
		GameSim.world.add(new Bullet(body.getX()+Math.cos(turretAngle)*15, body.getY()+Math.sin(turretAngle)*15, 7, turretAngle));
	}
	
	public double getHealth() {
		return health;
	}

	public final static double MAX_HEALTH = 100;
	private double health =  MAX_HEALTH;

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

		turn(Math.PI/4);

		turretAngle+=0.1;
		sensorAngle+=0.2;
		body.setHeading(body.getHeading()+0.05);
		body.setX(body.getX()+Math.cos(body.getHeading())*1);
		body.setY(body.getY()+Math.sin(body.getHeading())*1);
		health-=1;
		if(health == 0) { health = 100; shoot(); }
	}

	public void loop() { //used for all objects in world, not accessible to user
		if(moveTime > 0){
			moveTime--;
			body.setX(body.getX()+Math.cos(body.getHeading())*moveSpeed);
			body.setY(body.getY()+Math.sin(body.getHeading())*moveSpeed);
		}
		if(turnTime > 0){
			turnTime--;
			body.setHeading(body.getHeading()+turnSpeed);
		}
		
	}

	@Override
	public void collide(WorldObject o) {
		moveTime = 0;
		
		
	}

	@Override
	public void die() {
		alive = false; //CHANING HOW DIE WORKS I THINK
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

	public void turn (double speed, int time) { //turnSpeed is in radians
	
		if(speed > MAX_SPEED) turnSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) turnSpeed = MIN_SPEED;
		else turnSpeed = speed;
		if(time > 0)
				turnTime = time;
	}
	
	public void move(double speed, int time) { //negative moves backwards positive moves forwards
		
		if(speed > MAX_SPEED) moveSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) moveSpeed = MIN_SPEED;
		else moveSpeed = speed;
		if(time > 0)
				moveTime = time;
	}
	

}
