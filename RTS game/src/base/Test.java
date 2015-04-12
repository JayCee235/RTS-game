package base;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test extends Game{
	Unit selected;
	BufferedImage f;
	
	int mouseX, mouseY;
	
	public Test(int w, int h, Entity[][] gam, Material[][] grd) {
		super(w, h);
		mouseX = this.getWidth() / 2;
		mouseY = this.getHeight() / 2;
		this.gameMap = gam;
		this.groundMap = grd;
		try {
			f = ImageIO.read(this.getClass().getResource("res/mapEditor/frame_malachite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(selected != null && selected instanceof Unit) {
			Unit work = (Unit) selected;
			int px = 32 * this.selected.x + work.px - 5;
			int py = 32 * this.selected.y + work.py - 5;
			g.translate(dx + odx, dy + ody);
			g.drawImage(f, px , py, px + f.getWidth(), py + f.getHeight(), 
					0, 0, f.getWidth(), f.getHeight(), null);
			g.translate(-dx - odx, -dy - ody);
		}
	}
	
	public static int clamp(int in, int min, int max) {
		int out = in;
		out = out > max ? max : out;
		out = out < min ? min : out;
		return out;
	}
	
	@Override
	public void tick() {
		super.tick();
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[i].length; j++) {
				if (gameMap[i][j] instanceof Unit) {
					((Unit) gameMap[i][j]).tick();
				}
			}
		}
		
		if (!dragging) {
			if (mouseX <= 64) {
				this.dx += 8;
			}
			if (mouseY <= 64) {
				this.dy += 8;
			}
			if (mouseX >= this.getWidth() - 64) {
				this.dx -= 8;
			}
			if (mouseY >= this.getHeight() - 64) {
				this.dy -= 8;
			}
		}
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() == 2) {
			tdx = arg0.getX();
			tdy = arg0.getY();
			dragging = true;
		}
		int i = (arg0.getX() - (dx + odx)) / 32;
		int j = (arg0.getY() - (dy + ody)) / 32;
		if (i >= 0 && i < groundMap.length && j >= 0
				&& j < groundMap[0].length) {
			Entity e = this.gameMap[i][j];
			if (e != null && e instanceof Unit) {
					selected = ((Unit) this.gameMap[i][j]);
					this.selected.x = i;
					this.selected.y = j;
			} else { 
				if(selected instanceof Unit) {
					if (selected.moveTo(groundMap, gameMap, i, j)) {
						
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Window window = new Window("TEST", 1200, 900);
		Material.loadMaterial(window);
		Game current = window.newMapEditor();
		window.startGame();
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == 2) {
			dx += odx;
			dy += ody;
			odx = 0;
			ody = 0;
			dragging = false;
		}
		
	}
	
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}

}
