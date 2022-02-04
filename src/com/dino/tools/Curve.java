package com.dino.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.dino.window.Canvas;

public class Curve {

	private Path2D.Double path;
	private ArrayList<Point2D.Double> points;
	private int length;
	
	public Curve() {
		path = new Path2D.Double();
		points = new ArrayList<Point2D.Double>();
		length = 0;
	}
	
	public void addPoint(double x, double y) {
		if (isEmpty()) path.moveTo(x, y);
		else path.lineTo(x, y);
		points.add(new Point2D.Double(x,y));
		length++;
	}
	
	public Point2D currentPoint() {
		return path.getCurrentPoint();
	}
	
	public ArrayList<Point2D.Double> getPoints() {
		return points;
	}
	
	public boolean isEmpty() {
		return length == 0;
	}
	
	public int getLength() {
		return length;
	}
	
	public void reset() {
		path.reset();
		points = new ArrayList<>();
		length = 0;
	}
	
	public void draw(Graphics2D g, double dx, double dy) {
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if (length == 1) {
			Point2D p = currentPoint();
			g.draw((Shape) new Line2D.Double(p.getX() + dx, p.getY() + dy, p.getX() + dx, p.getY() + dy));
		} else if (length > 0) {
			Path2D.Double transformedPath = (Double) path.clone();
			transformedPath.transform(new AffineTransform(1,0,0,1,dx,dy));
			g.draw(transformedPath);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Curve clone() {
		Curve c = new Curve();
		c.path = (Double) path.clone();
		c.points = (ArrayList<java.awt.geom.Point2D.Double>) points.clone();
		c.length = length;
		return c;
	}
	
	public static Curve createFromArray(ArrayList<Point2D.Double> points) {
		Curve c = new Curve();
		c.path.moveTo(points.get(0).getX(), points.get(0).getY());
		for (int i = 1; i < points.size(); i++) c.path.lineTo(points.get(i).getX(), points.get(i).getY());
		c.points = points;
		c.length = points.size();
		return c;
	}
	
	public Rectangle getBounds() {
		Rectangle bounds = path.getBounds();
		Rectangle newBounds = new Rectangle();
		
		newBounds.setFrame(bounds.getX()-1, bounds.getY()-1, bounds.getWidth()+2, bounds.getHeight()+2);
		return newBounds;
	}
	
	public boolean containsPoint(Point2D.Double p) {
		if (length == 1) return points.get(0).distance(p) <= Canvas.MINDIST;
		return getBounds().contains(p);
	}
	
}
