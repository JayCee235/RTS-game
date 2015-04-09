package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
	protected static BufferedImage img;
	protected static boolean loaded;
	
	public static void load(String file, Object o) {
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
}
