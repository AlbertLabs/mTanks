

public class PrintData {
	

		double health;
		double maxHealth;
		

	
	PrintData(String image, double x, double y, double w, double h, double angle, double he, double mhe){
		this.x = x;
		this.y = y;
		this.width=w;
		this.height=h;
		this.angle=angle;
		this.health=he;
		this.maxHealth=mhe;
		this.image = image;
	}
	
	String image;
	
	double x;
	double y;
	double width;
	double height;
	double angle;
}
