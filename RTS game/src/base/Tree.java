package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tree extends Entity{
	
	
	public Tree(Object o, int code) {
		this.load("tree", o);
		this.entityCode = code;
	}
	
	

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(this.img, 32*x, 32*(y-1), 32*(x+1), 32*(y+1), 
				0, 0, 32, 64, null);
	}
	
	public void drawOffset(Graphics g, int x, int y) {
		g.drawImage(this.img, 32*x - 16, 32*(y-1) + 16, 32*(x+1) - 16, 32*(y+1) + 16, 
				0, 0, 32, 64, null);
	}



	@Override
	public BufferedImage getImage() {
		return this.img;
	}



	@Override
	public int getEntityCode() {
		return entityCode;
	}

}
