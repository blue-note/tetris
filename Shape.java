import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.util.Random;

public class Shape {

    String[] shapes = {"Z", "S","Line", "T", "Square", "L", "MirrorL"};
    String name;
    int[][] coords;
    int[][][] coordoptions;
    int index = 0;

    public Shape() {
	coords = new int[3][2];
	coordoptions = new int[][][]{
	    {{-1,0},{0,-1},{1,-1}},{{1,0},{0,-1},{-1,-1}},{{1,0},{2,0},{3,0}}, {{1,0},{0,-1},{-1,0}}, {{1,0},{1,-1},{0,-1}}, {{1,0},{0,1},{0,2}},{{-1,0},{0,1},{0,2}}
	};
	name = setRandomShape();
    }
	
    public String setRandomShape() {
	Random r = new Random();
	index = r.nextInt(7);
	String s = shapes[index];
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 2; j++) {
		coords[i][j] = coordoptions[index][i][j];
	    }
	}
	return s;
    }

    public String getName() {
	return name;
    }
    
    
    public int[][] getCoords() {
	return coords;
    }

    public void rotateLeft() {
	if (name.equals("Square")) 
	    return;
	
	for (int i = 0; i < 3; i++) {
	    int swap = coords[i][0];
	    coords[i][0] = coords[i][1]; 
	    coords[i][1] = swap;
	    if (coords[i][0] != 0) {
		int a = coords[i][0];
		coords[i][0] = -a; 
	    }
	}	

    }
    
    public int getIndex() {
	return index;

    }
}
