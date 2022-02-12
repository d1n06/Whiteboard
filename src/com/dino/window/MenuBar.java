package com.dino.window;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.dino.Reference;
import com.dino.tools.Tools;

public class MenuBar {

	public static JMenuBar makeMenuBar(Canvas canvas, FileManager fm) {
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu(Reference.MENU_FILE);
		
		JMenuItem newItem = new JMenuItem(Reference.MENU_FILE_NEW, Tools.getImageIcon(Reference.MENU_FILE_NEW_ICON));
		JMenuItem saveItem = new JMenuItem(Reference.MENU_FILE_SAVE, Tools.getImageIcon(Reference.MENU_FILE_SAVE_ICON));
		JMenuItem saveAsItem = new JMenuItem(Reference.MENU_FILE_SAVEAS, Tools.getImageIcon(Reference.MENU_FILE_SAVEAS_ICON));
		JMenuItem openItem = new JMenuItem(Reference.MENU_FILE_OPEN, Tools.getImageIcon(Reference.MENU_FILE_OPEN_ICON));
		
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
		
		JMenuItem undoItem = new JMenuItem(Reference.MENU_EDIT_UNDO, Tools.getImageIcon(Reference.MENU_EDIT_UNDO_ICON));
		JMenuItem redoItem = new JMenuItem(Reference.MENU_EDIT_REDO, Tools.getImageIcon(Reference.MENU_EDIT_REDO_ICON));
		
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
