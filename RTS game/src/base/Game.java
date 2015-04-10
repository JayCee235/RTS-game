package base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener, 
MouseWheelListener, Runnable {
	private boolean running;
	protected boolean keys[];
	
	protected Material[][] groundMap;
	
	protected Entity[][] gameMap;
	
	protected int dx, dy, tdx, tdy, odx, ody;
	
	protected ArrayList<BufferedImage>[][] imageMap;
	
	protected boolean dragging;
	
	protected Tree tree;
	
	protected Ore hematite;
	
	public Game(int w, int h) {
		Dimension s = new Dimension(w, h);
		this.setMinimumSize(s);
		this.setPreferredSize(s);
		this.setMaximumSize(s);
		
		keys = new boolean[256];
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		//TODO: Don't hardcode 20???
		int size = 30;
		gameMap = new Entity[size][size];
		
		groundMap = new Material[size][size];
		
		for(int i = 0; i < groundMap.length; i++) {
			for(int j = 0; j < groundMap[i].length; j++) {
				groundMap[i][j] = Material.grass;
			}
		}
		
		tree = new Tree(this);
		hematite = new Ore("hematite", this);
	}
	
	public void start() {
		Thread t = new Thread(this);
		this.running = true;
		this.readyPaint();
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
	
	protected void readyPaint() {
		this.imageMap = new ArrayList[groundMap.length][groundMap[0].length];
		Material[] in = Material.getList();
		for(int i = 0; i < groundMap.length; i++) {
			for(int j = 0; j < groundMap[i].length; j++) {
				ArrayList<BufferedImage> work = new ArrayList<BufferedImage>();
				imageMap[i][j] = work;
				if (groundMap[i][j]!=Material.VOID) {
					work.add(groundMap[i][j].getSprite());
				}
				for(int k = groundMap[i][j].getLayer() + 1; k < in.length; k++) {
					int code = 0;
					for(int ii = 0; ii <= 2; ii++) {
						if(i+ii-1 < 0 || i+ii-1 >= groundMap.length)
							continue;
						for(int jj = 0; jj <= 2; jj++) {
							if(j+jj-1 < 0 || j+jj-1 >= groundMap[0].length)
								continue;
							if(groundMap[i+ii-1][j+jj-1] != in[k])
								continue;
							if(ii==1 && jj==1)
								continue;
							int c = ii + 3*jj;
							if(c >= 4)
								c--;
							
							int d = 1 << c;
							code += d;
							
						}
					}
					if (code!=0) {
						BufferedImage fringe = in[k].getFringe(code);
						work.add(fringe);
					}
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.translate(dx + odx, dy + ody);
		
		for(int i = 0; i < imageMap.length; i++) {
			for(int j = 0; j < imageMap[i].length; j++) {
				for(BufferedImage img : imageMap[i][j]) {
					g.drawImage(img, 32*i, 32*j, 32*i+32, 32*j+32, 0, 0, 32, 32, null);
				}
			}
		}
		
		for(int j = 0; j < gameMap[0].length; j++) {
			for(int i = 0; i < gameMap.length; i++) {
				Entity work = gameMap[i][j];
				if(work != null) {
					work.draw(g2, i, j);
					if((i != 0 && gameMap[i-1][j] == work) 
							&& (j != gameMap[i].length - 1 && gameMap[i][j+1] == work) 
							&& ((i != 0 && j != gameMap[i].length - 1) && gameMap[i-1][j+1] == work)) {
						work.drawOffset(g2, i, j);
					}
				}
			}
		}
		
		g.translate(-dx - odx, -dy - ody);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
			if (dragging) {
				odx = arg0.getX() - tdx;
				ody = arg0.getY() - tdy;
			}
		
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
		if (arg0.getButton() == 3) {
			tdx = arg0.getX();
			tdy = arg0.getY();
			dragging = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == 3) {
			dx += odx;
			dy += ody;
			odx = 0;
			ody = 0;
			dragging = false;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		if(code >= 0 && code < keys.length) {
			keys[code] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		if(code >= 0 && code < keys.length) {
			keys[code] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
