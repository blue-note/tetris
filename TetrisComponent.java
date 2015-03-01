import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import java.awt.Component;

public class TetrisComponent extends JPanel { 
    
    final static int LINE_WIDTH = 4;
    final static int ROWS = 3;
    final static int COLUMNS = 3;
    final static int boardwidth = 300;
    final static int boardheight = 540;
    final static int blockwidth = boardwidth/10;
    static boolean okay = true;


    boolean paused = false;
    boolean over = false;
    Shape curShape;
    int linesRemoved = 0;
    int xcurr = 0;
    int ycurr = 0;    
    LineBorder border;
    private Animator animator;
    boolean[][] board;
    int[][] colors;
    JLabel score;
    TetrisPanel parent;


    TetrisComponent(Component comp) {
	parent = (TetrisPanel) comp;
	score = parent.getScorebar();
	score.setText("Score: "+linesRemoved);
	board = new boolean[boardwidth/blockwidth][boardheight/blockwidth];
	for (int i = 0; i < boardwidth/blockwidth; i++){
	    for (int j = 0; j < boardheight/blockwidth; j++) {
		board[i][j] = false;
	    }
	}
	colors = new int[boardwidth/blockwidth][boardheight/blockwidth];
	setFocusable(true);
	addKeyListener(new MyKeyListener());
	animator = new Animator(this);
	newShape();
	(new Thread(animator)).start();
	setLayout(new GridLayout(ROWS, COLUMNS));
	border = new LineBorder(Color.WHITE, LINE_WIDTH);
	setBackground(Color.black);
	setPreferredSize(new Dimension(boardwidth, boardheight));
	setMaximumSize(new Dimension(boardwidth, boardheight));
	setMinimumSize (new Dimension(boardwidth, boardheight));
	repaint();
    }
       
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (over) {
	    score.setText("GAME OVER");
		    return;
	}
	hasFallen();
	gameOver();
	int x = 0;
	int y = 0;
	for (int i = 0; i < boardwidth/blockwidth; i++) {
	    for (int j = 0; j < boardheight/blockwidth; ++j) {
		if (board[i][j] == true) {
		    int color = colors[i][j];
		    x = i*blockwidth;
		    y = j*blockwidth;
		    drawBlock(g, x, y, color);
		}
	    }
	}
	drawShape(g,curShape);
	removeLines();
	hasFallen();
	ycurr += blockwidth;
	
    }
    public void drawShape(Graphics g, Shape shape) {
	int x = 0;
	int y = 0;
	int index = shape.getIndex();
	int[][] coords = shape.getCoords();	
	drawBlock(g,xcurr,ycurr,index); //this is the center of gravity block
	for (int i = 0; i < 3; i++) {
	    x = xcurr + (blockwidth*coords[i][0]);
	    y = ycurr - (blockwidth*coords[i][1]);
	    drawBlock(g, x, y, index);
	}
    }
    
    
    public void drawBlock(Graphics g, int x, int y, int c) {
	Color[] colors = {new Color(49, 185, 219), new Color(88, 219, 49), new Color(232, 90, 30), new Color(68, 188, 172),new Color(247,240,27), new Color(185,23,191), new Color(216,30,136)};
	
	Color color = colors[c];
	g.setColor(color);
	g.fillRect(x, y, blockwidth, blockwidth);
	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke(new BasicStroke(2));
	g.setColor(Color.white);
	g.drawRect(x, y, blockwidth, blockwidth);
	
    }
        
    public boolean tryBlock(int x, int y) {
	try {
	if (board[x][y] == true) {
	    return false;
    }
	} catch (Exception e) {
	    return false;
	}
	return true;

    }

    public void start() {
	animator.startClock();
    }

    public void stop() {
	animator.stopClock();
    }
    
    public void pause() {
	if (!paused) {
	    stop();
	    paused = !paused;
	}
	else if (paused) {
	   start();
	   paused = !paused;
		}
    }

    public void newShape() {
	curShape = new Shape();
	xcurr = boardwidth/2;
	ycurr = 0;

    }
    
    public void hasFallen() {
	int[][] coords = curShape.getCoords();
	boolean hasFallen = false;
	int index = curShape.getIndex();
	int c = xcurr/blockwidth;
	int d = ycurr/blockwidth;
	int a = 0;
	int b = 0;

	if (d == 17) {
	    hasFallen = true;	    
	} else if (board[c][d+1] == true) 
	 hasFallen = true;
	else {
	    for (int i = 0; i < 3; i++) { 
		a = (xcurr + (blockwidth*coords[i][0]))/blockwidth;
		b = (ycurr - (blockwidth*coords[i][1]))/blockwidth;
			try {
		if (b >= 17) {
		    hasFallen = true;
		} else if (b >= 0 && board[a][b+1] == true) 
		    hasFallen = true;
		
			} catch (Exception e) {
			    System.out.println("Keep playing! Woohoo!");
			}
	    }
	    
	}
    	
	if (hasFallen == true) {
	    board[c][d] = true;
	    colors[c][d] = index;
	    for (int j = 0; j < 3; j++) {
		    a = (xcurr + (blockwidth*coords[j][0]))/blockwidth;
		    b = (ycurr - (blockwidth*coords[j][1]))/blockwidth;
		    if (b >= 0) {
		    board[a][b] = true;
		    colors[a][b] = index;
		     }
	    }
	   
		newShape();
	}
    }
    
    public void removeLines() {
	int counter = 0;
	int k = 0;
	for (int i = 0; i < boardheight/blockwidth; i++) {
	    while (k < boardwidth/blockwidth) {
		if (board[k][i] == true) {
		    counter++; 
		}
		k++;
	    }
	    k = 0;
	    if (counter == 10) {
		remove(i);
		linesRemoved +=10;
	    }
	    counter = 0;
	}
	score.setText("Score: "+linesRemoved);
    }

    public void remove(int i) {
	for (int k = 0; k < boardwidth/blockwidth; ++k) {
	    board[k][i] = false;
	    colors[k][i] = 0;
	}
	settle(i);
    }
    
    public void settle(int i) {
	boolean[][] boardcopy = new boolean[boardwidth/blockwidth][boardheight/blockwidth];
	int[][] colorscopy = new int[boardwidth/blockwidth][boardheight/blockwidth];
	
	for (int s = 0; s < boardwidth/blockwidth; ++s) {
	    for (int t = 0; t < boardheight/blockwidth; ++t) {
		boardcopy[s][t] = board[s][t];
		colorscopy[s][t] = colors[s][t];

	    }
	   
	}
	
	for (int g = 0; g < boardwidth/blockwidth; ++g) {
	    for (int f = 1; f <= i; ++f) {
		board[g][f] = boardcopy[g][f-1];
		colors[g][f] = colorscopy[g][f-1];
		
	    }
	}    
    }
    
    public void gameOver() {
	int[][] coords = curShape.getCoords();
	int c = xcurr/blockwidth;
	int d = ycurr/blockwidth;

	 if (d == 0 && board[c][d+1] == true) {
		over = true;
	 }
	 else {	 for (int j = 0; j < 3; j++) {
	     int a = (xcurr + (blockwidth*coords[j][0]))/blockwidth;
	     int b = (ycurr - (blockwidth*coords[j][1]))/blockwidth;
	     if(b==0 && board[a][b+1] == true)
		 over = true;
	 }
	 }
	 
	 if (over) {
	     score.setText("GAME OVER.");
	     stop();
	     
	 }	 
    }
    
    public boolean tryLeft() {
	int[][] coords = curShape.getCoords();
	int c = 0;
	boolean b = true;
	for (int i = 0; i < 3; i++) { 
	    c = (xcurr + (blockwidth*coords[i][0]));
	    if (c < blockwidth || xcurr < blockwidth)                        
		b = false;
	}
	return b;
	
    }
    public boolean tryRight() {
	int[][] coords = curShape.getCoords();
	boolean a = true;
	int c = 0;
	for (int i = 0; i < 3; i++) { 
	    c = (xcurr + (blockwidth*coords[i][0]));
	    if (c >= boardwidth - blockwidth || xcurr >= boardwidth - blockwidth)                                 a = false;
	}
	
	return a;
    }

    class MyKeyListener extends KeyAdapter {
	
	public void keyPressed (KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    int[][] coords = curShape.getCoords();
	    int c = 0;
	    int d = 0;
	    if (keyCode == 'P' || keyCode == 'p') {
		animator.delay = true;
		pause();
		return;
	    }
	    if (keyCode == 'd' || keyCode == 'D') {
		settle(17); //cheat: press d and remove lines from the bottom
	    }
	    switch(keyCode) {
	    case KeyEvent.VK_RIGHT:
		if (tryRight())
		    xcurr += blockwidth;
		break;
	    case KeyEvent.VK_LEFT:
		if (tryLeft())
		    xcurr -= blockwidth;
		break;
	    case KeyEvent.VK_DOWN:
		repaint();
		boolean okay = true;
		if (tryBlock(xcurr/blockwidth, (ycurr/blockwidth)+1) == false)
		    okay = false;
		for (int i = 0; i < 3; i++) { 
		    c = (xcurr + (blockwidth*coords[i][0]))/blockwidth;
		    d = (ycurr - (blockwidth*coords[i][1]))/blockwidth;
		    if (tryBlock(c,d+1) ==false)                                           
			okay = false;
		}
		if (okay)
		    ycurr += blockwidth;
		break;
	    case KeyEvent.VK_UP:
		boolean go = true;
		if (tryBlock(xcurr/blockwidth, (ycurr/blockwidth)+1) == false)
		    go = false;
		for (int i = 0; i < 3; i++) { 
		    c = (xcurr + (blockwidth*coords[i][0]))/blockwidth;
		    d = (ycurr - (blockwidth*coords[i][1]))/blockwidth;
		    if (tryBlock(c,d+1) == false)                                           
			go = false;
		}
		if (go) {
		curShape.rotateLeft();
		if (tryLeft() && tryRight())
		repaint();
		else {
		    int x = 0;
		    while (x < 3){
			curShape.rotateLeft();
			x++;
		    }
		}
		}
		break;
		
	    case KeyEvent.VK_SPACE:
		repaint();
	       
		break;
	    default: 
		break;
		
	    }
	}
    }
		

}


