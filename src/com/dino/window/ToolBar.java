package com.dino.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.dino.Reference;
import com.dino.tools.Tools;

public class ToolBar {

	private static JToggleButton[] tools;
	
	public static JToolBar makeToolBar(Canvas canvas) {
		JToolBar tb = new JToolBar("tools", JToolBar.HORIZONTAL);
		
		tools = new JToggleButton[4];
		tools[0] = new JToggleButton(Tools.getImageIcon(Reference.TOOLBAR_PENCIL_ICON));	
		tools[1] = new JToggleButton(Tools.getImageIcon(Reference.TOOLBAR_ERASER_ICON));	
		tools[2] = new JToggleButton(Tools.getImageIcon(Reference.TOOLBAR_MOVE_ICON));	
		tools[3] = new JToggleButton(Tools.getImageIcon(Reference.TOOLBAR_ZOOM_ICON));
		
		tools[0].setToolTipText(Reference.TOOLBAR_PENCIL_TIP);
		tools[1].setToolTipText(Reference.TOOLBAR_ERASER_TIP);
		tools[2].setToolTipText(Reference.TOOLBAR_MOVE_TIP);
		tools[3].setToolTipText(Reference.TOOLBAR_ZOOM_TIP);
		
		addButtonShortcut(tools[0], "P", canvas);
		addButtonShortcut(tools[1], "E", canvas);
		addButtonShortcut(tools[2], "V", canvas);
		addButtonShortcut(tools[3], "Z", canvas);
		
		tools[0].setSelected(true);
		
		for (JToggleButton b : tools) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {chooseTool((JToggleButton) e.getSource(), canvas);}
			});
			
			tb.add(b);
		}
		
		return tb;
	}
	
	private static void chooseTool(JToggleButton b, Canvas canvas) {
		int tool = -1;
		for (int i = 0; i < tools.length; i++) {
			if (tools[i] != b) tools[i].setSelected(false);
			else tool = i;
		}
		
		b.setSelected(true);
		
		canvas.selectedTool = tool;
		canvas.changeCursor(tool);
	}
	
	public static void chooseTool(int tool, Canvas canvas) {
		for (int i = 0; i < tools.length; i++) {
			if (i != tool) tools[i].setSelected(false);
		}
		
		tools[tool].setSelected(true);
		
		canvas.selectedTool = tool;
		canvas.changeCursor(tool);
	}
	
	private static void addButtonShortcut(JToggleButton b, String keyStroke, Canvas canvas) {
		b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyStroke), "choose");
		b.getActionMap().put("choose", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {chooseTool(b, canvas);}
		});
	}
	
}
