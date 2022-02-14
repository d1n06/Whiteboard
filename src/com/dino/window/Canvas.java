package com.dino.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.dino.Reference;
import com.dino.tools.Curve;
import com.dino.tools.Tools;

public class Canvas extends JPanel {

	public final static double MINDIST = 5;
	
	private MouseInput mouse;
	private ArrayList<Curve> drawing = new ArrayList<>();
	private ArrayList<Curve> undoList = new ArrayList<>();
	private Curve current = new Curve();
	private boolean penDown = false;
	private boolean moving = false;
	private boolean erasing = false;
	
	private double cx = 0;
	private double cy = 0;
	private double zoom = 1;

	private Window window;
	private JPopupMenu contextMenu;
	
	private Cursor[] cursors = {
			Toolkit.getDefaultToolkit().createCustomCursor(Tools.getImage(Reference.TOOLBAR_PENCIL_ICON), new Point(0,0), "pencil"),
			Toolkit.getDefaultToolkit().createCustomCursor(Tools.getImage(Reference.TOOLBAR_ERASER_ICON), new Point(0,0), "eraser"),
			Toolkit.getDefaultToolkit().createCustomCursor(Tools.getImage(Reference.TOOLBAR_MOVE_ICON), new Point(0,0), "move"),
			Toolkit.getDefaultToolkit().createCustomCursor(Tools.getImage(Reference.TOOLBAR_ZOOM_ICON), new Point(0,0), "zoom")
	};
	
	public int selectedTool = 0;
	
	public final static int PENCIL = 0;
	public final static int ERASER = 1;
	public final static int MOVE = 2;
	public final static int ZOOM = 3;
	
	public Canvas(Window window) {
		this.window = window;

		contextMenu = ContextMenu.makeContextMenu(this);
		add(contextMenu);
		
		mouse = new MouseInput(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		setCursor(cursors[PENCIL]);
	}
	
	public void paint(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (Curve c : drawing) c.draw(g, cx, cy, zoom);
		if (penDown) {
			current.draw(g, cx, cy, zoom);
			addPointToCurve(mouse.getX(), mouse.getY());
		} else if (moving) {
			cx -= mouse.getDX();
			cy -= mouse.getDY();
		} else if (erasing) {
			Point2D.Double p = new Point2D.Double((mouse.getX()+cx)/zoom, (mouse.getY()+cy)/zoom);
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
		case PENCIL:
			if (mouse.isPressedAlone(MouseInput.MOUSE1)) startCurve(mouse.getX(), mouse.getY());
			else if (mouse.isReleased(MouseInput.MOUSE1) && penDown) endCurve();
			
			break;
			
		case ERASER:
			if (mouse.isPressed(MouseInput.MOUSE1)) erasing = true;
			else if (mouse.isReleased(MouseInput.MOUSE1)) erasing = false;
			
			break;
			
		case MOVE:
			if (mouse.isPressedAlone(MouseInput.MOUSE1)) moving = true;
			else if (mouse.isReleased(MouseInput.MOUSE1)) moving = false;
			
			break;
		
		case ZOOM:
			double dz = 1;
			if (mouse.isPressedAlone(MouseInput.MOUSE1)) dz = 1.5;
			if (mouse.isPressedAlone(MouseInput.MOUSE3)) dz = 1/1.5;
			
			cx = cx*dz - mouse.getX()*(1-dz);
			cy = cy*dz - mouse.getY()*(1-dz);
			
			zoom *= dz;
			
			break;
		}
		
		if (mouse.isReleasedAlone(MouseInput.MOUSE2)) contextMenu.show(this, mouse.getX(), mouse.getY());
		
		mouse.reset();
	}
	
	public void startCurve(double x, double y) {
		current.addPoint((x+cx)/zoom, (y+cy)/zoom);
		penDown = true;
	}
	
	public void addPointToCurve(double x, double y) {
		x = (x+cx)/zoom;
		y = (y+cy)/zoom;
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
	
	public void changeCursor(int tool) {
		setCursor(cursors[tool]);
	}
	
}
