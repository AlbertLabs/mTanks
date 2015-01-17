package com.albertlabs.mtanks;

import java.util.List;

public interface WorldObject {
	Body getBody();

	 List<PrintData> print();
	 
	 void collide(WorldObject o);

	void act();
	void die();
	public boolean alive();
}
