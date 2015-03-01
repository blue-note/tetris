import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

public class Tetris extends JFrame {

   private Tetris() {
	super("Tetris");
	JOptionPane.showMessageDialog(this,"Down Arrow: FASTER   Right/Left Arrow: MOVE    Up Arrow: ROTATE   p: PAUSE  ", "CONTROLS", JOptionPane.INFORMATION_MESSAGE);
	setSize(200,400);
	setTitle("Tetris");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setContentPane(new TetrisPanel());
	pack();
	setVisible(true);
	
   }
        
    public static void main (String[] args) {
		new Tetris();

    }


}
















