package com.dino.window;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.dino.Reference;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5998579689983281817L;

	public Window() {
		super(Reference.PROGRAM_NAME + " - v" + Reference.PROGRAM_VERSION);

		//setSize(800, 800);
		setMinimumSize(new Dimension(300, 300));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon2.png"));
		
		Canvas canvas = new Canvas();
		getContentPane().add(canvas);
		
		setJMenuBar(MenuBar.makeMenuBar(canvas));
		
		setVisible(true);
		
		canvas.repaint();
	}
	
}
