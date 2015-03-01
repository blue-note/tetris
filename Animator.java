import java.awt.*;

public class Animator implements Runnable {

    private static final int DELAY = 400;
    private static final int SHORTDELAY = 500;
    public boolean delay = false;

    private long clockTime1; 
    private long elapsedTime1;
    private boolean running = true;
    
    Component component;
    Component tc;
    long counter = 0;
    int counter2 = 0;

    public Animator (Component component) {
	this.component = component;
    }

    public synchronized void run() {
	
	try {
	    while (true) {
		if (running) {
		    wait(DELAY - counter);
		     component.repaint();
		}
		else {
		    wait();
		}
		if (counter2 %5 == 0){
		counter += 1;
		}
		counter2 += 1;
	    }
	}
	catch (InterruptedException e) {
	}
    }

    public synchronized void startClock() {
	clockTime1 = System.nanoTime();
	running = true;
	notify();
    }
    
    public synchronized void stopClock() {
		elapsedTime1 = elapsedTime();
	running = false;
    }
    
    public synchronized long elapsedTime() {
	if (!running) return elapsedTime1;
	else return elapsedTime1 + System.nanoTime() - clockTime1;
    }
    
        
}