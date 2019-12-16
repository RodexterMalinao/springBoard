package com.bomltsportal.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class SignatureToImage {

    private Color penColor;
    private Color background;
    private int height;
    private int width;
    private int penWidth;

    public SignatureToImage() {
        penColor = Color.BLACK;
        height = 300;
        width = 800;
        penWidth = 5;
    }

    public void saveSignature(String pSignatureString, OutputStream pOutputStream) {
    	BufferedImage image = this.getSignatureImage(pSignatureString);
    	try {
    	    ImageIO.write(image, "png", pOutputStream);
    	} catch (IOException e) {
    	}
    }
    
    public void saveSignature(String pSignatureString, String filename) {
    	BufferedImage image = this.getSignatureImage(pSignatureString);
    	try {
    	    File outputfile = new File(filename);
    	    ImageIO.write(image, "png", outputfile);
    	} catch (IOException e) {
    	}
    }
    
    public BufferedImage getSignatureImage(String pSignatureString) {
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);;
    	Graphics2D graphic = image.createGraphics();
    	Line2D line = new Line2D.Float();
    	
    	graphic.setPaint(penColor);
    	Stroke stroke = new BasicStroke(penWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    	graphic.setStroke(stroke);

        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		Gson gson = new Gson();
		SignatureLine[] lineValue = gson.fromJson( pSignatureString, SignatureLine[].class );
		
		for (int i = 0; i < lineValue.length; i++) {
	    	line.setLine(lineValue[i].lx, lineValue[i].ly, lineValue[i].mx, lineValue[i].my);
	    	graphic.draw(line);
		}
		return image;
    }
    
    public String ReadJson(String filename) {
	    try {
	        // Open the file that is the first 
	        // command line parameter
	    	String jsonString = "";
	        FileInputStream fstream = new FileInputStream(filename);
	        // Get the object of DataInputStream
	        DataInputStream in = new DataInputStream(fstream);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String strLine;
	        //Read File Line By Line
	        while ((strLine = br.readLine()) != null)   {
	          // Print the content on the console
	        	jsonString = strLine;
	        }
	        //Close the input stream
	        in.close();
	        
	        return jsonString;
	     } catch (Exception e){//Catch exception if any
	        return null;
	     }
    }

    private class SignatureLine {
        public int lx;
        public int ly;
        public int mx;
        public int my;
    }
}