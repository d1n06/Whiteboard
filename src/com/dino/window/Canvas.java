package com.dino.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.dino.tools.Curve;

public class Canvas extends JPanel {

	public final static double MINDIST = 5;
	
	MouseInput mouse;
	ArrayList<Curve> drawing = new ArrayList<>();
	ArrayList<Curve> undoList = new ArrayList<>();
	Curve current = new Curve();
	boolean penDown = false;
	boolean moving = false;
	boolean erasing = false;
	
	double dx = 0;
	double dy = 0;

	Window window;
	
	int selectedTool = 0;
	
	public final static int PEN = 0;
	public final static int ERASER = 1;
	public final static int MOVE = 2;
	public final static int ZOOM = 3;
	
	public Canvas(Window window) {
		this.window = window;
		
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
			
			// The drawing has changed from the original drawing
			window.getFileManager().changed = true;
			window.updateTitle();
		}
		
		switch (selectedTool) {
		case PEN:
			if (mouse.isPressedOnly(MouseInput.MOUSE1)) startCurve(mouse.getX() - dx, mouse.getY() - dy);
			else if (mouse.isReleased(MouseInput.MOUSE1) && penDown) endCurve();
			break;
			
		case ERASER:
			if (mouse.isPressed(MouseInput.MOUSE1)) erasing = true;
			else if (mouse.isReleased(MouseInput.MOUSE1)) erasing = false;
			break;
			
		case MOVE:
			if (mouse.isPressedOnly(MouseInput.MOUSE1)) moving = true;
			else if (mouse.isReleased(MouseInput.MOUSE1)) moving = false;
			break;
		}
		
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
		
		// The drawing has changed from the original drawing
		window.getFileManager().changed = true;
		window.updateTitle();
	}

	public ArrayList<Curve> getDrawing() {
		return drawing;
	}

	public void setDrawing(ArrayList<Curve> drawing) {
		this.drawing = drawing;
	}
	
	public void clear() {
		drawing = new ArrayList<>();
	}
	
	public void undo() {
		if (drawing.size() == 0) return;
		undoList.add(drawing.get(drawing.size()-1));
		drawing.remove(drawing.size()-1);
		
		// The drawing has changed from the original drawing
		window.getFileManager().changed = true;
		window.updateTitle();
	}
	
	public void redo() {
		if (undoList.size() == 0) return;
		drawing.add(undoList.get(undoList.size()-1));
		undoList.remove(undoList.size()-1);
		
		// The drawing has changed from the original drawing
		window.getFileManager().changed = true;
		window.updateTitle();
	}
	
}
