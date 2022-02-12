package com.dino.window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseInput extends MouseAdapter {

	private int x, y;
	private int px, py;
	private Canvas canvas;

    private ArrayList<Integer> heldButtons = new ArrayList<Integer>();
    private ArrayList<Integer> pressedButtons = new ArrayList<Integer>();
    private ArrayList<Integer> releasedButtons = new ArrayList<Integer>();
    
    public static final int MOUSE1 = MouseEvent.BUTTON1;
    public static final int MOUSE2 = MouseEvent.BUTTON3; // el boton 2 siempre es el derecho bobo
    public static final int MOUSE3 = MouseEvent.BUTTON2;
    
	
	public MouseInput(Canvas canvas) {
		this.canvas = canvas;
	}
	
    public void mouseMoved(MouseEvent e) {
    	px = x;
    	py = y;
        x = e.getX();
        y = e.getY();
        canvas.repaint();
    }

    public void mouseDragged(MouseEvent e) {
    	px = x;
    	py = y;
        x = e.getX();
        y = e.getY();
        canvas.repaint();
    }

    public void mousePressed(MouseEvent e) {
    	px = x;
    	py = y;
        x = e.getX();
        y = e.getY();
        int b = e.getButton();
        pressedButtons.add(b);
        if (!heldButtons.contains(b)) heldButtons.add(b);
        canvas.repaint();
    }

    public void mouseReleased(MouseEvent e) {
    	px = x;
    	py = y;
    	x = e.getX();
    	y = e.getY();
        int b = e.getButton();
        releasedButtons.add(b);
        heldButtons.remove(heldButtons.indexOf(b));
        canvas.repaint();
    }

    public void reset() {
        pressedButtons = new ArrayList<Integer>();
        releasedButtons = new ArrayList<Integer>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getPX() {
    	return px;
    }
    
    public int getPY() {
    	return py;
    }
    
    public int getDX() {
    	return x - px;
    }
    
    public int getDY() {
    	return y - py;
    }

    public boolean isHeld(int b) {
        return heldButtons.contains(b);
    }

    public boolean isPressed(int b) {
        return pressedButtons.contains(b);
    }

    public boolean isReleased(int b) {
        return releasedButtons.contains(b);
    }

    public ArrayList<Integer> getHeldButtons() {
        return heldButtons;
    }

    public ArrayList<Integer> getPressedButtons() {
        return pressedButtons;
    }

    public ArrayList<Integer> getReleasedButtons() {
        return releasedButtons;
    }
    
    public boolean isHeldAlone(int b) {
    	return isHeld(b) && heldButtons.size() == 1;
    }
    
    public boolean isPressedAlone(int b) {
    	return isPressed(b) && heldButtons.size() == 1;
    }
    
    public boolean isReleasedAlone(int b) {
    	return isReleased(b) && heldButtons.size() == 0;
    }

	
}
