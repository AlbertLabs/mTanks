

public class Tank implements Printable {

	private BoxBody body;
	private double health;
	private double maxHealth;
	
	Tank(double x, double y, double width, double height, double heading){
		 body = new BoxBody(x, y, width, height, heading);
	}
	
	public PrintData print() {
		return new PrintData(body.getX(), body.getY(), body.getWidth(), body.getHeight(), body.getHeading(), new PrintData.HealthBar(100,maxHealth));
	}
    
}