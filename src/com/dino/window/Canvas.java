package com.dino.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.dino.tools.Curve;

public class Canvas extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3808105883119338437L;
	
	static MouseInput mouse;
	static ArrayList<Curve> drawing = new ArrayList<>();
	static ArrayList<Curve> undoList = new ArrayList<>();
	static Curve current = new Curve();
	static boolean penDown = false;
	static boolean moving = false;
	static boolean erasing = false;
	
	double dx = 0;
	double dy = 0;
	
	public static final double MINDIST = 5;
	
	public Canvas() {
		mouse = new MouseInput(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void paint(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (Curve c : drawing) c.draw(g, dx, dy);
		if (penDown) {
			current.draw(g, dx, dy);
			addPointToCurve(mouse.getX() - dx, mouse.getY() - dy);
		} else if (moving) {
			dx += mouse.getDX();
			dy += mouse.getDY();
		} else if (erasing) {
			Point2D.Double p = new Point2D.Double(mouse.getX() - dx, mouse.getY() - dy);
			ArrayList<Curve> toErase = new ArrayList<>();
			for (Curve c : drawing) {
				if (c.containsPoint(p)) toErase.add(c);
			}
			
			for (Curve c : toErase) drawing.remove(c);
		}
		
		if (mouse.isPressedOnly(MouseInput.MOUSE1)) startCurve(mouse.getX() - dx, mouse.getY() - dy);
		else if (mouse.isReleased(MouseInput.MOUSE1) && penDown) endCurve();
		
		if (mouse.isPressed(MouseInput.MOUSE1) && mouse.isHeld(MouseInput.MOUSE2)) moving = true;
		else if (mouse.isReleased(MouseInput.MOUSE1) || mouse.isReleased(MouseInput.MOUSE2)) moving = false;
		
		if (mouse.isPressed(MouseInput.MOUSE1) && mouse.isHeld(MouseInput.MOUSE3)) erasing = true;
		else if (mouse.isReleased(MouseInput.MOUSE1) || mouse.isReleased(MouseInput.MOUSE3)) erasing = false;
		
		mouse.reset();
	}
	
	public void startCurve(double x, double y) {
		current.addPoint(x, y);
		penDown = true;
	}
	
	public void addPointToCurve(double x, double y) {
		if (current.currentPoint().distance(x, y) >= MINDIST) current.addPoint(x,y);
	}
	
	public void endCurve() {
		drawing.add(current.clone());
		current.reset();
		penDown = false;
	}

	public static ArrayList<Curve> getDrawing() {
		return drawing;
	}

	public static void setDrawing(ArrayList<Curve> drawing) {
		Canvas.drawing = drawing;
	}
	
	public static void clear() {
		Canvas.drawing = new ArrayList<>();
	}
	
	public static void undo() {
		if (drawing.size() == 0) return;
		undoList.add(drawing.get(drawing.size()-1));
		drawing.remove(drawing.size()-1);
	}
	
	public static void redo() {
		if (undoList.size() == 0) return;
		drawing.add(undoList.get(undoList.size()-1));
		undoList.remove(undoList.size()-1);
	}
	
}
