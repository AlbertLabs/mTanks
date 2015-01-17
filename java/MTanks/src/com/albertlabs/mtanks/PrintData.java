package com.albertlabs.mtanks;

public class PrintData {
	
	static class HealthBar{

		double health;
		double maxHealth;
		
		HealthBar(double h, double mh){
			health = h;
			maxHealth = mh;
		}
	}
	
	PrintData(String image, double x, double y, double w, double h, double angle){
		this.x = x;
		this.y = y;
		this.width=w;
		this.height=h;
		this.angle=angle;
		this.image = image;
	}
	
	PrintData(String image, double x, double y, double w, double h, double angle, HealthBar hb){
		this.x = x;
		this.y = y;
		this.width=w;
		this.height=h;
		this.angle=angle;
		this.healthBar = hb;
		this.image = image;
	}
	
	String image;
	
	double x;
	double y;
	double width;
	double height;
	double angle;
	HealthBar healthBar;
}
