package base;

import java.awt.image.BufferedImage;

public enum Material {
	grass, sand, water, stone, VOID;
	
	private static BufferedImage[] tilesets;
	
	public static int getCount() {
		return 4;
	}
	
	public static Material[] getList() {
		return new Material[]{grass, sand, water, stone};
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
}
