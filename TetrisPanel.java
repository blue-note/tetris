import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class TetrisPanel extends JPanel {

   JLabel status;
   public JLabel score;
   TetrisComponent tc;
    
    
    public TetrisPanel() {
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(score = new JLabel("Score:  "));
	score.setFont(new Font("Sans Serif", Font.BOLD, 45));
	score.setForeground(new Color(68,188,172));
	score.setAlignmentX(0.5f);
	tc = new TetrisComponent(this);
	add(tc);
	add (Box.createVerticalStrut(50));
	setBorder(new EmptyBorder(50, 50, 50, 50));
    }
   
       public JLabel getScorebar () {
	   return score;

     }
    }

    
