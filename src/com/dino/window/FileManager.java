package com.dino.window;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dino.Reference;
import com.dino.tools.Curve;

public class FileManager {

	public String lastDir = "";
	
	Window window;
	boolean changed = false;
	
	public FileManager(Window window) {
		this.window = window;
	}
	
	public String readFile(File f) {
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

	public String readFile(String filePath) {
		return readFile(new File(filePath));
	}
	
	public void writeFile(File f, String txt) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(txt);
			lastDir = f.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseFileToDrawing(String str) {
		String[] lines = str.split(System.lineSeparator());
		ArrayList<Curve> drawing = new ArrayList<>();
		for (String line : lines) {
			String[] args = line.split(" ");
			String[] coords = args;
			
			Color col = Color.black;
			
			if (args[0].charAt(0) == 'c') {
				col = new Color(Integer.parseInt(args[0].substring(1)));
				coords = Arrays.copyOfRange(args, 1, args.length);
			}
			
			if (coords.length < 2) continue;
			
			ArrayList<Point2D.Double> points = new ArrayList<>();
			for (int i = 0; i < coords.length/2; i++) {
				double x = java.lang.Double.parseDouble(coords[2*i]);
				double y = java.lang.Double.parseDouble(coords[2*i+1]);
				points.add(new Point2D.Double(x, y));
			}
			
			Curve c = Curve.createFromArray(points);
			c.setColor(col);
			
			drawing.add(c);
		}
		window.getCanvas().setDrawing(drawing);
	}
	
	public String parseDrawingToFiles(ArrayList<Curve> drawing) {
		String str = "";
		for (Curve c : drawing) {
			str += c.getFileData();
			str += "\n";
		}
		return str;
	}
	
	public boolean acceptFile(File f) {
		return getExtension(f) == Reference.FILE_EXTENSION;
	}
	
	public String getExtension(File f) {
        String ext = "";
        String str = f.getName();
        int i = str.lastIndexOf('.');

        if (i > 0 &&  i < str.length() - 1) {
            ext = str.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	public void save() {
		if (!lastDir.equals("")) {
			File f = new File(lastDir);
			writeFile(f, parseDrawingToFiles(window.getCanvas().getDrawing()));

			changed = false;
			window.updateTitle();
		} else saveAs();
	}
	
	public void saveAs() {
		JFileChooser fc = new JFileChooser(lastDir);
		fc.setDialogTitle(Reference.SAVEDIALOG_TITLE);
		fc.setFileFilter(new FileNameExtensionFilter(Reference.FILE_EXTENSION_DESCRIPTION, Reference.FILE_EXTENSION));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.CANCEL_OPTION) return;
		
		String path = fc.getSelectedFile().getPath();
		File f = new File(path.endsWith("."+Reference.FILE_EXTENSION) ? path : path + "." + Reference.FILE_EXTENSION);
		writeFile(f, parseDrawingToFiles(window.getCanvas().getDrawing()));

		changed = false;
		window.updateTitle();
	}
	
	public void open() {
		JFileChooser fc = new JFileChooser(lastDir);
		fc.setDialogTitle(Reference.OPENDIALOG_TITLE);
		fc.setFileFilter(new FileNameExtensionFilter(Reference.FILE_EXTENSION_DESCRIPTION, Reference.FILE_EXTENSION));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.CANCEL_OPTION) return;
		
		File f = fc.getSelectedFile();
		parseFileToDrawing(readFile(f));

		changed = false;
		window.updateTitle();
	}
	
}
