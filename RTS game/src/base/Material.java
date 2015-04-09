package base;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Material {
	grass, sand, water, stone, VOID;
	
	private static BufferedImage[] tilesets;
	private static BufferedImage[] tilesetsFringe;
	
	public static int getCount() {
		return 4;
	}
	
	public static Material[] getList() {
		return new Material[]{water, sand, grass, stone};
	}
	
	public int getLayer() {
		switch(this) {
		case stone:
			return 3;
		case grass:
			return 2;
		case sand:
			return 1;
		case water:
			return 0;
		default:
			return -10;
		}
	}
	
	public static boolean loadMaterial() {
		Material.tilesets = new BufferedImage[Material.getCount()];
		Material.tilesetsFringe = new BufferedImage[Material.getCount()];
		
		Material[] in = Material.getList();
		String pre = "res/";
		String tile = "_tile.png";
		String fringe = "_fringe.png";
		Test t = new Test();
		//TODO: proper loop for all materials. Currently only loads grass for debugging.
		for(int i = 2; i < 3; i++) {
			String name = in[i].toString();
			try {
				Material.tilesets[i] = ImageIO.read(t.getClass().getResource(pre+name+tile));
				Material.tilesetsFringe[i] = ImageIO.read(t.getClass().getResource(pre+name+fringe));
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	public BufferedImage getSprite() {
		return Material.tilesets[this.getLayer()];
	}
}
