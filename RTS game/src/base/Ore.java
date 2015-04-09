package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ore extends Entity{
	BufferedImage tile;
	BufferedImage corner;
	
	public Ore(String name, Object o) {
		this.load(name, o);
		tile = img.getSubimage(0, 0, 32, 32);
		corner = img.getSubimage(32, 0, 32, 32);
	}
	
	

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(this.tile, 32*x, 32*y, 32*(x+1), 32*(y+1), 
				0, 0, 32, 32, null);
	}
	
	public void drawOffset(Graphics g, int x, int y) {
		g.drawImage(this.corner, 32*x - 16, 32*y + 16, 32*(x+1) - 16, 32*(y+1) + 16, 
				0, 0, 32, 32, null);
	}

}
