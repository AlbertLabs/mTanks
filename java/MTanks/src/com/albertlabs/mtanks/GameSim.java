package com.albertlabs.mtanks;

import java.util.ArrayList;
import java.util.List;

import com.firebase.client.Firebase;

public class GameSim {

	static World world;
	
	static Firebase ref;

	//Parameters: gameURL, tankURL1, tankURL2, tankURL3...
	public static void main(String[] args) {
		String gameURL = args[0];
		List<String> tankURLs = new ArrayList<String>();
		for(int i = 1; i < args.length; i++){
			tankURLs.add(args[i]);
		}
		
		ref = new Firebase(gameURL);
		
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
		
		
		for(WorldObject a : world.getList()){
			if(a instanceof Tank)
			((Tank)a).begin();
		}
		
		while (true) {

			for(WorldObject a : world.getList()){ //only Tanks run
				if(a instanceof Tank)
				((Tank)a).act(); //public side
			}
			
			for(WorldObject a : world.getList()){ //everybody acts
				a.loop(); //private side
			}
			
			for(WorldObject a: world.getList()){
				List<WorldObject> col = world.collidingWith(a);
				for(WorldObject b : col){
					a.collide(b);
				}
			}
			/*
			List <WorldObject> tempList = new ArrayList<WorldObject>();
			for(WorldObject a: world.getList()){
				if(a.alive()){
					tempList.add(a);
				}
			}
			world.getList().retainAll(tempList);*/

			List<PrintData> printData = new ArrayList<PrintData>();
			for(WorldObject a: world.getList()){
				printData.addAll(a.print());
			}
			//System.out.println("TEST");
			//System.out.println(printData);
			ref.setValue(printData);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
        
        
        
}
