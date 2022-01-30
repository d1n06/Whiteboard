package com.dino.window;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dino.Reference;
import com.dino.tools.Curve;

public class FileManager {

	public static String lastDir = "";
	
	public static String readFile(File f) {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			StringBuilder sb = new StringBuilder();
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}

			lastDir = f.getAbsolutePath();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readFile(String filePath) {
		return readFile(new File(filePath));
	}
	
	public static void writeFile(File f, String txt) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(txt);
			lastDir = f.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void parseFileToDrawing(String str) {
		String[] lines = str.split(System.lineSeparator());
		ArrayList<Curve> drawing = new ArrayList<>();
		for (String line : lines) {
			String[] args = line.split(" ");
			
			if (args.length < 2) continue;
			
			ArrayList<Point2D.Double> points = new ArrayList<>();
			for (int i = 0; i < args.length/2; i++) {
				double x = java.lang.Double.parseDouble(args[2*i]);
				double y = java.lang.Double.parseDouble(args[2*i+1]);
				points.add(new Point2D.Double(x, y));
			}
			
			drawing.add(Curve.createFromArray(points));
		}
		Canvas.setDrawing(drawing);
	}
	
	public static String parseDrawingToFiles(ArrayList<Curve> drawing) {
		String str = "";
		for (Curve c : drawing) {
			for (Point2D.Double p : c.getPoints()) str += p.getX() + " " + p.getY() + " ";
			str += "\n";
		}
		return str;
	}
	
	public static boolean acceptFile(File f) {
		return getExtension(f) == Reference.FILE_EXTENSION;
	}
	
	public static String getExtension(File f) {
        String ext = "";
        String str = f.getName();
        int i = str.lastIndexOf('.');

        if (i > 0 &&  i < str.length() - 1) {
            ext = str.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	public static void save() {
		if (!lastDir.equals("")) {
			File f = new File(FileManager.lastDir);
			FileManager.writeFile(f, FileManager.parseDrawingToFiles(Canvas.getDrawing()));
		} else saveAs();
	}
	
	public static void saveAs() {
		JFileChooser fc = new JFileChooser(lastDir);
		fc.setDialogTitle(Reference.SAVEDIALOG_TITLE);
		fc.setFileFilter(new FileNameExtensionFilter(Reference.FILE_EXTENSION_DESCRIPTION, Reference.FILE_EXTENSION));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.CANCEL_OPTION) return;
		
		String path = fc.getSelectedFile().getPath();
		File f = new File(path.endsWith("."+Reference.FILE_EXTENSION) ? path : path + "." + Reference.FILE_EXTENSION);
		writeFile(f, parseDrawingToFiles(Canvas.getDrawing()));
	}
	
	public static void open() {
		JFileChooser fc = new JFileChooser(lastDir);
		fc.setDialogTitle(Reference.OPENDIALOG_TITLE);
		fc.setFileFilter(new FileNameExtensionFilter(Reference.FILE_EXTENSION_DESCRIPTION, Reference.FILE_EXTENSION));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.CANCEL_OPTION) return;
		File f = fc.getSelectedFile();
		parseFileToDrawing(readFile(f));
	}
	
}
