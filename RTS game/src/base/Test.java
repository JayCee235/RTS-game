package base;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test extends Game{
	Entity selected;
	int sx, sy;
	BufferedImage f;
	
	public Test(int w, int h, Entity[][] gam, Material[][] grd) {
		super(w, h);
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
			int px = 32 * sx + work.px - 5;
			int py = 32 * sy + work.py - 5;
			g.drawImage(f, px, py, px + f.getWidth(), py + f.getHeight(), 
					0, 0, f.getWidth(), f.getHeight(), null);
		}
	}
	
	public int clamp(int in, int min, int max) {
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
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		int i = (arg0.getX() - (dx + odx)) / 32;
		int j = (arg0.getY() - (dy + ody)) / 32;
		if (i >= 0 && i < groundMap.length && j >= 0
				&& j < groundMap[0].length) {
			Entity e = this.gameMap[i][j];
			if (e != null && e instanceof Unit) {
					selected = this.gameMap[i][j];
					sx = i;
					sy = j;
			} else { 
				if(selected instanceof Unit) {
					dx = clamp(i - sx, -1, 1);
					dy = clamp(j - sy, -1, 1);
					if (((Unit) selected).move(groundMap, gameMap, dx, dy)) {
						gameMap[sx][sy] = null;
						gameMap[sx + dx][sy + dy] = selected;
						sx += dx;
						sy += dy;
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

}
