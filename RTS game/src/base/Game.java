package base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener, 
MouseWheelListener, Runnable {
	private boolean running;
	
	public Game(int w, int h) {
		Dimension s = new Dimension(w, h);
		this.setMinimumSize(s);
		this.setPreferredSize(s);
		this.setMaximumSize(s);
	}
	
	public void start() {
		Thread t = new Thread(this);
		this.running = true;
		t.start();
	}

	@Override
	public void run() {
		
		while(running) {
			long start = System.currentTimeMillis();
			this.tick();
			this.repaint();
			long dt = System.currentTimeMillis() - start;
			try {
				Thread.sleep((1000-dt)/60);
			} catch (InterruptedException e) {
				
			}
		}
		
		
	}
	
	public void tick() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
