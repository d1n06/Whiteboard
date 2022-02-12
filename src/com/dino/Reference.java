package com.dino;

public class Reference {

	// PROGRAM INFO
	public static final String PROGRAM_NAME = "Whiteboard";
	public static final String PROGRAM_VERSION = "0.0.1";
	
	// MENU BAR
	public static final String MENU_FILE = "File";
	public static final String MENU_EDIT = "Edit";
	
	public static final String MENU_FILE_NEW = "New";
	public static final String MENU_FILE_SAVE = "Save";
	public static final String MENU_FILE_SAVEAS = "Save As...";
	public static final String MENU_FILE_OPEN = "Open...";
	
	public static final String MENU_FILE_NEW_ICON = "/icons/document-new.png";
	public static final String MENU_FILE_SAVE_ICON = "/icons/document-save.png";
	public static final String MENU_FILE_SAVEAS_ICON = "/icons/document-save-as.png";
	public static final String MENU_FILE_OPEN_ICON = "/icons/document-open.png";
	
	public static final String MENU_EDIT_UNDO = "Undo";
	public static final String MENU_EDIT_REDO = "Redo";
	
	public static final String MENU_EDIT_UNDO_ICON = "/icons/edit-undo.png";
	public static final String MENU_EDIT_REDO_ICON = "/icons/edit-redo.png";
	
	// TOOL BAR
	public static final String TOOLBAR_PENCIL_TIP = "Pencil";
	public static final String TOOLBAR_ERASER_TIP = "Eraser";
	public static final String TOOLBAR_MOVE_TIP = "Move";
	public static final String TOOLBAR_ZOOM_TIP = "Zoom";
	
	public static final String TOOLBAR_PENCIL_ICON = "/icons/gimp-tool-pencil.png";
	public static final String TOOLBAR_ERASER_ICON = "/icons/gimp-tool-eraser.png";
	public static final String TOOLBAR_MOVE_ICON = "/icons/gimp-tool-move.png";
	public static final String TOOLBAR_ZOOM_ICON = "/icons/gimp-tool-zoom.png";
	
	// FILE DIALOGS
	public static final String SAVEDIALOG_TITLE = "Save";
	public static final String OPENDIALOG_TITLE = "Open";
	
	public static final String FILE_EXTENSION = "wht";
	public static final String FILE_EXTENSION_DESCRIPTION = ".wht - Whiteboard file";

	// UNSAVED WARNING DIALOG
	public static final String UNSAVED_EXIT_DIALOG_TITLE = "Unsaved changes";
	public static final String UNSAVED_EXIT_DIALOG_MESSAGE = "Your drawing has unsaved changes. Do you wish to save before exiting?";
	public static final Object[] UNSAVED_EXIT_DIALOG_OPTIONS = {
			"Yes, save and exit",
			"No, discard changes",
			"Cancel"
	};
	
}
