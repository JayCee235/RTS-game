package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public abstract class Entity {
	protected BufferedImage img;
	protected boolean loaded;
	
	protected int entityCode;
	
	public void load(String file, Object o) {
		if (!loaded) {
			try {
				img = ImageIO.read(o.getClass().getResource(
						"res/entities/" + file + ".png"));
				loaded = true;
			} catch (IOException e) {

			}
		}
	}
	
	public abstract void draw(Graphics g, int x, int y);
	public abstract void drawOffset(Graphics g, int x, int y);
	
	public abstract BufferedImage getImage();
	
	public abstract int getEntityCode();
}
