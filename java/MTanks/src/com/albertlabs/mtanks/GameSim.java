package com.albertlabs.mtanks;

import com.firebase.client.Firebase;

public class GameSim {
	
	World world;
	
	Firebase ref = new Firebase("https://mtanks.firebaseio.com/");
	
    
	GameSim(){
		world = new World(500, 500);
        
		BoxBody left = new BoxBody(7, 250, 15, 500, 0, "Border");
		BoxBody right = new BoxBody(500, 250, 15, 500, 0,"Border");
		BoxBody bottom = new BoxBody(250, 500, 507, 15, 0,"Border");
		BoxBody top = new BoxBody(250, 7, 500, 15, 0,"Border");
		BoxBody ball = new BoxBody(50, 50, 20, 20, 30,"Bullet");
		CircleBody ball2 = new CircleBody(80, 50, 0, 10,"Bullet");

		double xspeed = 0.01;
		double yspeed = -0.2;
		double xspeed2 = 0.7;
		double yspeed2 = 1;
		
		double spinSpeed = 5;

		world.addBody(left);
		world.addBody(right);
		world.addBody(top);
		world.addBody(bottom);
		world.addBody(ball);
		world.addBody(ball2);

		int count = 0;
		
		while (true) {
			System.out.println(count++);
			if (ball.checkCollision(ball2)) {
				double temp1 = xspeed;
				double temp2 = yspeed;
				xspeed = xspeed2;
				yspeed = yspeed2;
				xspeed2 = temp1;
				yspeed2 = temp2;
			}

			if (left.checkCollision(ball) || ball.checkCollision(right)) {
				xspeed *= -1;
				System.out.println("Thing 1 hit thing");
			}

			if (ball.checkCollision(top) || ball.checkCollision(bottom)) {
				yspeed *= -1;
				System.out.println("Thing 1 hit thing");
			}

			ball.setX(ball.getX() + xspeed);
			ball.setY(ball.getY() + yspeed);
			ball.setHeading(ball.getHeading() + spinSpeed);

			if (left.checkCollision(ball2) || ball2.checkCollision(right)) {
				xspeed2 *= -1;
				System.out.println("Thing 2 hit thing");
			}

			if (ball2.checkCollision(top) || ball2.checkCollision(bottom)) {
				yspeed2 *= -1;
				System.out.println("Thing 2 hit thing");
			}

			ball2.setX(ball2.getX() + xspeed2);
			ball2.setY(ball2.getY() + yspeed2);
			ball2.setHeading(ball.getHeading() + spinSpeed);

			ref.setValue(world.getList());
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
        
        
        
		new GameSim();
	}
	
}
