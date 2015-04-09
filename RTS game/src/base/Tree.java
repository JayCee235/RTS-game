package base;

import java.awt.Graphics;

public class Tree extends Entity{
	
	public Tree(Object o) {
		this.load("tree", o);
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

}
