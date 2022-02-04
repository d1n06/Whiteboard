package com.dino.window;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.dino.Reference;

public class MenuBar {

	public static JMenuBar makeMenuBar(Canvas canvas, FileManager fm) {
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu(Reference.MENU_FILE);
		
		JMenuItem newItem = new JMenuItem(Reference.MENU_FILE_NEW, new ImageIcon("res/file.png"));
		JMenuItem saveItem = new JMenuItem(Reference.MENU_FILE_SAVE, new ImageIcon("res/save.png"));
		JMenuItem saveAsItem = new JMenuItem(Reference.MENU_FILE_SAVEAS);
		JMenuItem openItem = new JMenuItem(Reference.MENU_FILE_OPEN, new ImageIcon("res/open.png"));
		
		newItem.addActionListener(e -> {canvas.clear(); canvas.repaint();});
		saveItem.addActionListener(e -> {fm.save(); canvas.repaint();});
		saveAsItem.addActionListener(e -> {fm.saveAs(); canvas.repaint();});
		openItem.addActionListener(e -> {fm.open(); canvas.repaint();});
		
		newItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
		
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(openItem);
		
		mb.add(fileMenu);
		
		JMenu editMenu = new JMenu(Reference.MENU_EDIT);
		
		JMenuItem undoItem = new JMenuItem(Reference.MENU_EDIT_UNDO);
		JMenuItem redoItem = new JMenuItem(Reference.MENU_EDIT_REDO);
		
		undoItem.addActionListener(e -> {canvas.undo(); canvas.repaint();});
		redoItem.addActionListener(e -> {canvas.redo(); canvas.repaint();});
		
		undoItem.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		redoItem.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		
		mb.add(editMenu);
		
		return mb;
	}
	
}
