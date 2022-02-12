package com.dino.window;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.dino.Reference;
import com.dino.tools.Tools;

public class ContextMenu {

	public static JPopupMenu makeContextMenu(Canvas canvas) {
		JPopupMenu pm = new JPopupMenu();
		
		JMenuItem toolPencilItem = new JMenuItem(Reference.TOOLBAR_PENCIL_TIP, Tools.getImageIcon(Reference.TOOLBAR_PENCIL_ICON));
		JMenuItem toolEraserItem = new JMenuItem(Reference.TOOLBAR_ERASER_TIP, Tools.getImageIcon(Reference.TOOLBAR_ERASER_ICON));
		JMenuItem toolMoveItem = new JMenuItem(Reference.TOOLBAR_MOVE_TIP, Tools.getImageIcon(Reference.TOOLBAR_MOVE_ICON));
		JMenuItem toolZoomItem = new JMenuItem(Reference.TOOLBAR_ZOOM_TIP, Tools.getImageIcon(Reference.TOOLBAR_ZOOM_ICON));

		toolPencilItem.addActionListener(e -> {ToolBar.chooseTool(Canvas.PENCIL, canvas);});
		toolEraserItem.addActionListener(e -> {ToolBar.chooseTool(Canvas.ERASER, canvas);});
		toolMoveItem.addActionListener(e -> {ToolBar.chooseTool(Canvas.MOVE, canvas);});
		toolZoomItem.addActionListener(e -> {ToolBar.chooseTool(Canvas.ZOOM, canvas);});
		
		pm.add(toolPencilItem);
		pm.add(toolEraserItem);
		pm.add(toolMoveItem);
		pm.add(toolZoomItem);
		
		pm.addSeparator();
		
		JMenuItem undoItem = new JMenuItem(Reference.MENU_EDIT_UNDO, Tools.getImageIcon(Reference.MENU_EDIT_UNDO_ICON));
		JMenuItem redoItem = new JMenuItem(Reference.MENU_EDIT_REDO, Tools.getImageIcon(Reference.MENU_EDIT_REDO_ICON));
		
		undoItem.addActionListener(e -> {canvas.undo(); canvas.repaint();});
		redoItem.addActionListener(e -> {canvas.redo(); canvas.repaint();});
		
		pm.add(undoItem);
		pm.add(redoItem);
		
		return pm;
	}
	
}
