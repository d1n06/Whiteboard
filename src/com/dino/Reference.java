package com.dino;

public class Reference {

	public static final String PROGRAM_NAME = "Whiteboard";
	public static final String PROGRAM_VERSION = "0.0";
	
	public static final String MENU_FILE = "File";
	public static final String MENU_EDIT = "Edit";
	
	public static final String MENU_FILE_NEW = "New";
	public static final String MENU_FILE_SAVE = "Save";
	public static final String MENU_FILE_SAVEAS = "Save As...";
	public static final String MENU_FILE_OPEN = "Open...";
	
	public static final String MENU_EDIT_UNDO = "Undo";
	public static final String MENU_EDIT_REDO = "Redo";
	
	public static final String SAVEDIALOG_TITLE = "Save";
	public static final String OPENDIALOG_TITLE = "Open";
	
	public static final String FILE_EXTENSION = "wht";
	public static final String FILE_EXTENSION_DESCRIPTION = ".wht - Whiteboard file";

	public static final String UNSAVED_EXIT_DIALOG_TITLE = "Unsaved changes";
	public static final String UNSAVED_EXIT_DIALOG_MESSAGE = "Your drawing has unsaved changes. Do you wish to save before exiting?";
	public static final Object[] UNSAVED_EXIT_DIALOG_OPTIONS = {
			"Yes, save and exit",
			"No, discard changes",
			"Cancel"
	};
	
}
