package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class MapEditor extends Game{
	private boolean gridMode;
	
	private BufferedImage tileFrame, menu;
	
	private int selected = 0;
	
	int saveNum;
	
	public MapEditor(int w, int h) {
		super(w, h);
		gridMode = false;
		try {
			menu = ImageIO.read(this.getClass().getResource("res/mapEditor/tileMenu.png"));
			tileFrame = ImageIO.read(this.getClass().getResource("res/mapEditor/frame_malachite.png"));
		} catch (IOException e) {
			
		}
		saveNum = 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getX() >= this.getWidth() - 2*this.menu.getWidth() && 
				arg0.getY() <= 2*this.menu.getHeight()) {
			int x = arg0.getX() - (this.getWidth() - 2*this.menu.getWidth());
			int y = arg0.getY();
			
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					int xb = 18 + (i*66);
					int yb = 24 + (j*66);
					
					if (x >= xb && x <= xb + 64 && y >= yb && y <= yb + 64) {
						selected = i + 2*j;
					}
				}
			}
			
			
			
			
		} else {
			if (arg0.getButton() == 1) {
				int i = (arg0.getX() - (dx + odx)) / 32;
				int j = (arg0.getY() - (dy + ody)) / 32;
					if (i >= 0 && i < groundMap.length && j >= 0
							&& j < groundMap[0].length) {
						if (!keys[KeyEvent.VK_DELETE]) {
							switch (selected) {
							case 0:
								gameMap[i][j] = this.hematite;
								groundMap[i][j] = Material.stone;
								break;
							case 1:
								gameMap[i][j] = this.tree;
								groundMap[i][j] = Material.grass;
								break;
							case 2:
								groundMap[i][j] = Material.water;
								break;
							case 3:
								groundMap[i][j] = Material.sand;
								break;
							case 4:
								groundMap[i][j] = Material.grass;
								break;
							case 5:
								groundMap[i][j] = Material.stone;
								break;
							default:
							}
						} else {
							gameMap[i][j] = null;
						}
					this.readyPaint();
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(dx + odx, dy + ody);
		if (gridMode) {
			for (int i = 0; i < imageMap.length; i++) {
				for (int j = 0; j < imageMap[i].length; j++) {
					g.setColor(Color.white);
					g.drawRect(32 * i, 32 * j, 32, 32);
				}
			}
		}
		g.translate(-dx - odx, -dy - ody);
		int x = this.getWidth() - 2*this.menu.getWidth();
		int y = 0;
		
		g.drawImage(this.menu, x, y, 
				x + 2*this.menu.getWidth(), y + 2*this.menu.getHeight(), 
				0, 0, this.menu.getWidth(), this.menu.getHeight(), null);
		
		BufferedImage tr = this.tree.getImage();
		BufferedImage rr = this.hematite.getImage();
		
		g.drawImage(tr, x+16 + 66, (y-64)+22, 
				x+16 + 66 + 64, y+22 + 64, 
				0, 0, tr.getWidth(), tr.getHeight(), null);
		g.drawImage(rr, x+16, y+22, 
				x+16 + 64, y+22 + 64, 
				0, 0, rr.getWidth(), rr.getHeight(), null);
		
		Material[] in = Material.getList();
		for(int i = 0; i < 2; i++) {
			for(int j = 0+1; j < 2+1; j++) {
				BufferedImage draw = in[i + 2*(j-1)].getSprite();
				g.drawImage(draw, x+16 + 66*i, y+22 + 66*j, 
						x+16 + 66*i + 64, y+22 + 66*j + 64, 
						0, 0, draw.getWidth(), draw.getHeight(), null);
			}
		}
		
		
		
		
		int i = selected % 2;
		int j = selected / 2;
		g.drawImage(this.tileFrame, x+16 + 66*i - 10, y+22 + 66*j - 10, 
				x+16 + 66*i + 64 + 10, y+22 + 66*j + 64 + 10, 
				0, 0, this.tileFrame.getWidth(), this.tileFrame.getHeight(), null);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		super.mouseWheelMoved(arg0);
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		super.mouseDragged(arg0);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		super.mouseEntered(arg0);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		super.mouseExited(arg0);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		super.mousePressed(arg0);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		super.mouseReleased(arg0);
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		super.keyPressed(arg0);
		if(arg0.getKeyCode() == KeyEvent.VK_G) {
			gridMode = !gridMode;
		}
		//TODO: How does this work...
//		if(arg0.getKeyCode() == KeyEvent.VK_S) {
//			System.out.println("Saving...");
//			Level lv = new Level(30, 30, groundMap, gameMap);
//			try {
//				File ent = new File("C:\\levelEditor\\level" + 
//						this.saveNum++ + ".ser");
//				if(!ent.exists()) {
//					ent.createNewFile();
//				}
//				FileOutputStream fout = new FileOutputStream("C:\\levelEditor\\level" + 
//			this.saveNum++ + ".ser");
//				ObjectOutputStream oout = new ObjectOutputStream(fout);
//				
//				oout.writeObject(lv);
//				oout.close();
//				System.out.println("whoa");
//			} catch (FileNotFoundException e) {
//				System.err.println("Uh oh");
//			} catch (IOException e) {
//				System.err.println("Uh ohh");
//			}
//		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		super.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		super.keyTyped(arg0);
	}
}
