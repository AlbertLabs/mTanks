package com.albertlabs.mtanks;

import java.util.ArrayList;
import java.util.List;

import com.firebase.client.Firebase;

public class GameSim {

	 static World world;
	static Firebase ref;
static int lastSize = 0;
	static public void objectDeath(WorldObject o){
		dead.add(o);
	}
	
	static public void objectCreate(WorldObject o){
		world.add(o);
	}
	
	private static ArrayList<WorldObject> dead = new ArrayList<WorldObject>();

	//Parameters: gameURL, tankURL1, tankURL2, tankURL3...
	public static void main(String[] args) {
		String gameURL = args[0];
		ArrayList<String> tankURLs = new ArrayList<String>();
		for(int i = 1; i < args.length; i++){
			tankURLs.add(args[i]);
		}
		
		ref = new Firebase(gameURL);
		ref.setValue("");
		
		world = new World(500, 500);

		//Background List
		//Bullet List
		//
		
		//for each tank
		
	/*	for(int i = 0; i < tankURLs.size(); i++){
			world.add(TankLoader.loadTank(tankURLs.get(i)));
		}*/
		
		
		
		world.add(new Tank(100, 100, 50, 50, 0));
		world.add(new Tank(400, 100, 50, 50, 0));
		world.add(new Tank(100, 400, 50, 50, 0));
		world.add(new Tank(400, 400, 50, 50, 0));
		
		Box left = new Box(7, 250, 15, 500, 0);
		Box right = new Box(500, 250, 15, 500, 0);
		Box bottom = new Box(250, 500, 507, 15, 0);
		Box top = new Box(250, 7, 500, 15, 0);
		
		world.add(left);
		world.add(right);
		world.add(top);
		world.add(bottom);
		
		for(WorldObject a : (ArrayList<WorldObject>)world.getList().clone()){
			if(a instanceof Tank)
			((Tank)a).begin();
		}
		
		while (true) {
			for(WorldObject a : (ArrayList<WorldObject>)world.getList().clone()){ //only Tanks run
				if(a instanceof Tank)
				((Tank)a).act(); //public side
			}
			
			for(WorldObject a : (ArrayList<WorldObject>)world.getList().clone()){ //everybody acts
				a.loop(); //private side
			}
			
			for (WorldObject a : (ArrayList<WorldObject>) world.getList().clone()) {
				List<WorldObject> col = world.collidingWith(a);
				//System.out.print(a);
//System.out.println(col);
				for (WorldObject b : col){
					//System.out.println("coll" + a + "   " + b);
					if (!a.equals(b)) {
						if (a instanceof Tank)
							System.out.println(a + " hit " + b);
						a.collide(b);}
				}
			}
			
		/*	List <WorldObject> tempList = new ArrayList<WorldObject>();
			for(WorldObject a: world.getList()){
				if(a.alive()){
					tempList.add(a);
				}
			}
			for(WorldObject a: tempList){
				world.remove(a);
			}*/
			
			for(WorldObject a : dead){
				world.remove(a);
			}
			dead.clear();

			List<PrintData> printData = new ArrayList<PrintData>();
			for(WorldObject d: (ArrayList<WorldObject>)world.getList().clone()){
				printData.addAll(d.print());
			}
			int temp = printData.size();
			//System.out.println("TEST");
			for(int i = 0; i < printData.size(); i++){
				
				/*
				 * 		this.x = x;
		this.y = y;
		this.width=w;
		this.height=h;
		this.angle=angle;
		this.health=he;
		this.maxHealth=mhe;
		this.image = image;
				 */
				world.repaint();
				ref.child(Integer.toString(i)).child("y").setValue(printData.get(i).y);
				ref.child(Integer.toString(i)).child("x").setValue(printData.get(i).x);
				ref.child(Integer.toString(i)).child("width").setValue(printData.get(i).width);
				ref.child(Integer.toString(i)).child("height").setValue(printData.get(i).height);
				ref.child(Integer.toString(i)).child("angle").setValue(printData.get(i).angle);
				ref.child(Integer.toString(i)).child("health").setValue(printData.get(i).health);
				ref.child(Integer.toString(i)).child("maxHealth").setValue(printData.get(i).maxHealth);
				ref.child(Integer.toString(i)).child("image").setValue(printData.get(i).image);
				
			}
			for(int i = printData.size(); i <= lastSize; i++)
				ref.child(Integer.toString(i)).removeValue();
			//ref.setValue(printData);
			lastSize = temp;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
        
        
        
}
