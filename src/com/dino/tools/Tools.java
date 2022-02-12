package com.dino.tools;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tools {

	public static ImageIcon getImageIcon(String filePath) {
		return new ImageIcon(Tools.class.getResource(filePath));
	}
	
	public static Image getImage(String filePath) {
		return getImageIcon(filePath).getImage();
	}
	
}
