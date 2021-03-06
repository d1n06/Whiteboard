package com.dino.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dino.Reference;
import com.dino.tools.Tools;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5998579689983281817L;

	FileManager fm;
	Canvas canvas;
	
	public Window() {
		super(Reference.PROGRAM_NAME + " - v" + Reference.PROGRAM_VERSION);

		setMinimumSize(new Dimension(300, 300));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Tools.getImage("/icon2.png"));

		fm = new FileManager(this);

		getContentPane().setLayout(new BorderLayout());

		canvas = new Canvas(this);
		getContentPane().add(ToolBar.makeToolBar(canvas), BorderLayout.PAGE_START);
		getContentPane().add(canvas, BorderLayout.CENTER);

		setJMenuBar(MenuBar.makeMenuBar(canvas, fm));
		
		setVisible(true);
		
		canvas.repaint();
	}
	
	@Override
	public void dispose() {
		if (!fm.changed) System.exit(0);
		
		int choice = JOptionPane.showOptionDialog(this, Reference.UNSAVED_EXIT_DIALOG_MESSAGE, Reference.UNSAVED_EXIT_DIALOG_TITLE,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
				Reference.UNSAVED_EXIT_DIALOG_OPTIONS, Reference.UNSAVED_EXIT_DIALOG_OPTIONS[2]);
		
		switch (choice) {
		case JOptionPane.YES_OPTION:
			fm.save();
			System.exit(0);
			break;
		case JOptionPane.NO_OPTION:
			System.exit(0);
			break;
		case JOptionPane.CANCEL_OPTION:
			break; // lol
		}
	}
	
	public void updateTitle() {
		String title = Reference.PROGRAM_NAME + " - v" + Reference.PROGRAM_VERSION;
		if (fm.lastDir != "") {
			String fileName = Paths.get(fm.lastDir).getFileName().toString();
			title += " - " + fileName + (fm.changed ? "*" : "");
		}
		
		setTitle(title);
	}

	public FileManager getFileManager() {
		return fm;
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
}
