package com.albertlabs.mtanks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class World extends JPanel {

	private ArrayList<WorldObject> entity = new ArrayList<WorldObject>();

	public ArrayList<WorldObject> getList(){
		return entity;
	}
	
	public void add(WorldObject o) {
		if (!entity.contains(o))
			entity.add(o);
	}

	public void remove(WorldObject o) {
			entity.remove(o);
	}

	public void removeBody(WorldObject o) {
		entity.remove(o);
	}

	public List<WorldObject> collidingWith(WorldObject o) {
		List<WorldObject> list = new ArrayList<WorldObject>();
		for (WorldObject a : entity)
			if (!a.equals(o) && o.getBody().checkCollision(a.getBody()))
				list.add(a);
		return list;
	}

	private static final long serialVersionUID = -2733937760055038458L;

	public World() {
	}

	int x, y;

	long startTime;
	int frames = 0;
	private JLabel timer = new JLabel("", SwingConstants.CENTER);
	private JLabel title = new JLabel("", SwingConstants.CENTER);

	public World(int x1, int y1) {
		startTime = new Date().getTime();
		this.x = x1;
		this.y = y1;
		JFrame f = new JFrame("Simple Collide");
		f.getContentPane().setLayout(new BorderLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this, BorderLayout.CENTER);
		f.add(new TextAreaOutputStreamTest(), BorderLayout.LINE_END);
		f.add(title, BorderLayout.NORTH);
		f.add(timer, BorderLayout.SOUTH);

		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(new Color(122, 255, 122));

		f.pack();
		f.setVisible(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(x+10, y+40);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		frames++;
		timer.setText("Time elapsed: " + Math.round(((double)(new Date().getTime() - startTime))/1000.0) + "       Frames displayed: " + frames);
		title.setText("Displaying world with " + entity.size() + " objects");
		
		g.setColor(new Color(100, 0, 255));
		for (WorldObject a : entity) {
			if (a.getBody() instanceof CircleBody) {
				CircleBody b = (CircleBody) a.getBody();
				g.fillArc((int) b.getX() - (int) b.getRadius() / 2,
						(int) b.getY() - (int) b.getRadius() / 2,
						(int) b.getRadius() * 2, (int) b.getRadius() * 2, 0,
						360);
			} else {
				BoxBody b = (BoxBody) a.getBody();
				int[] intArray = new int[b.getXs().length];
				for (int i = 0; i < intArray.length; ++i)
					intArray[i] = (int) b.getXs()[i];
				int[] intArray2 = new int[b.getXs().length];
				for (int i = 0; i < intArray2.length; ++i)
					intArray2[i] = (int) b.getYs()[i];
				g.fillPolygon(intArray, intArray2, 4);
			} 
		}
	}
}
