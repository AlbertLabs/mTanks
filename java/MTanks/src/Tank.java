

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Tank implements WorldObject {

	private BoxBody body;
	private double moveSpeed;
	private double moveTime;
	private double turnSpeed;
	private double turnTime;
	
	private double turretAngle;
	private double turretSpeed;
	private double turretTime;
	private double radarAngle;
	private double radarSpeed;
	private double radarTime;
	
	private static final double MAX_SPEED = 3; //TODO set to non-arbitrary value
	private static final double MIN_SPEED = -3; //TODO set to non-arbitrary value

	public final static double MAX_HEALTH = 100;
	private double health =  MAX_HEALTH;

	Tank(double x, double y, double width, double height, double heading) {
		body = new BoxBody(x, y, width, height, heading);
		turretAngle = 0;
		radarAngle = 0;
	}
	
	public void begin(){ //accessible to user
		
	//	shoot();
	//	move(-5,500);
	//	turn(Math.PI/6, 4);
	}
	int counter = 50;
	public void act() { //used for only tank, accessible to user
	
	//	turnTurret(0.1, 1);
	//	turnRadar(-0.1, 1);

		if(getTargetColor().equals(Color.RED)){
			shoot();
		}
		
	/*	counter--;
		if(counter == 0){
			System.out.println("SHOOT");
			counter = 50;
			shoot();
		}*/
		
		if(getDistanceToObject() > 300)
			move(MAX_SPEED,1);
		else{
			turn(-0.5, 1);

			move(-MAX_SPEED,1);
		}
		
		/* turretAngle+=0.1;
		sensorAngle+=0.2;
		body.setHeading(body.getHeading()+0.05);
		body.setX(body.getX()+Math.cos(body.getHeading())*1);
		body.setY(body.getY()+Math.sin(body.getHeading())*1);
		health-=1;
		if(health == 0) { health = 100; shoot(); }
		*/

	}
	
	private void hit() {
		move(-MAX_SPEED,1);
	}
	
	public void bulletHit(){
		
	}
	
	public void takeDamage(double a) {
		this.health -= a;
		if (health <= 0)
			die();
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
		if (turretTime > 0) {
			turretTime--;
			turretAngle += turretSpeed;
		if (radarTime > 0) {
			radarTime--;
			radarAngle += radarSpeed;
		}
		}
		
	}
	
public void move(double speed, int time) { //negative moves backwards positive moves forwards
		
		if(speed > MAX_SPEED) moveSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) moveSpeed = MIN_SPEED;
		else moveSpeed = speed;
		if(time > 0) {
			moveTime = time;
		}
		
	}

	public void turn (double speed, int time) { //turnSpeed is in radians
	
		if(speed > MAX_SPEED) turnSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) turnSpeed = MIN_SPEED;
		else turnSpeed = speed;
		if(time > 0)
				turnTime = time;
	}
	
	public void shoot(){
//		GameSim.world.add(new Bullet(body.getX()+Math.cos(turretAngle)*5, body.getY()+Math.sin(turretAngle)*5, 7, turretAngle, this));
		GameSim.objectCreate(new Bullet(this.body.getX(), this.body.getY(), 7, turretAngle+this.body.getHeading(), this));
	}
	
	public void turnTurret(double speed, int time) {
		if(speed > MAX_SPEED) turretSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) turretSpeed = MIN_SPEED;
		else turretSpeed = speed;
		if(time > 0)
				turretTime = time;
	}
	
	public void turnRadar(double speed, int time) {
		if(speed > MAX_SPEED) radarSpeed = MAX_SPEED;
		else if(speed < MIN_SPEED) radarSpeed = MIN_SPEED;
		else radarSpeed = speed;
		if(time > 0)
				radarTime = time;
	}
	
	@Override
	public void collide(WorldObject obj) { //TODO collide stuff
		if(obj instanceof Bullet){
			if(!((Bullet)obj).parent.equals(this)){
				this.takeDamage(20);
				bulletHit();
			}
			
		}else{
		if(this.body.checkCollision(obj.getBody())){
			int dir = 1;
			if(moveSpeed < 0); dir = -1;
			body.setX(body.getX()+Math.cos(body.getHeading())*-dir);
			body.setY(body.getY()+Math.sin(body.getHeading())*-dir);
			hit();
		}
		moveSpeed = 0;
		moveTime = 0;
	//	this.takeDamage(10);
		}
	}

	/**
	 * TODO this will get the distance to an object
	 * @return distance
	 */
	public double getDistanceToObject() {
		return GameSim.world.firstInLine(this, radarAngle+turretAngle+this.getBody().getHeading()).dist;
	}
	
	public Color getTargetColor() {
		World.BodyEdgePoint a = GameSim.world.firstInLine(this, radarAngle+turretAngle+this.getBody().getHeading());
		if (a.dist < 150){
			return a.wo.getColor();
		}
		return Color.WHITE;
	}
	/**
	 * TODO This should return the color of the space directly in front of the tank
	 */
	public String scanColor() {
		return "why this";
	}

	@Override
	public void die() {
		GameSim.objectDeath(this);
	}

	public BoxBody getBody(){
		return body;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMoveSpeed(int speed) {
		moveSpeed = speed;
	}
	public double getMoveSpeed(){
		return moveSpeed;
	}
	
	
	public List<PrintData> print() {

		List<PrintData> list = new ArrayList<PrintData>();
		list.add(new PrintData("tank", body.getX(), body.getY(), body.getWidth(),
				body.getHeight(), body.getHeading()+Math.PI/2, 
						health, MAX_HEALTH));
		list.add(new PrintData("turret", body.getX(), body.getY(), body.getWidth(),
				body.getHeight(), body.getHeading()+turretAngle+Math.PI/2, 0, 0));
		list.add(new PrintData("sensor", body.getX(), body.getY(), body.getWidth()/2,
				body.getHeight()/2, body.getHeading()+turretAngle+radarAngle+Math.PI/2, 0, 0));
		return list;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}
	
}
