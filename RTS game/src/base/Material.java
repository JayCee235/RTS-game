package base;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Material {
	grass, sand, water, stone, VOID;
	
	private static BufferedImage[] tilesets;
	private static BufferedImage[] tilesetsFringe;
	
	public static int getCount() {
		return Material.getList().length;
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
			return -1;
		}
	}
	
	public static boolean loadMaterial(Object o) {
		Material.tilesets = new BufferedImage[Material.getCount()];
		Material.tilesetsFringe = new BufferedImage[Material.getCount()];
		
		Material[] in = Material.getList();
		String pre = "res/tiles/";
		String tile = "_tile.png";
		String fringe = "_fringe.png";
		//TODO: proper loop for all materials. Currently only loads sand and grass for debugging.
		for(int i = 0; i < 3; i++) {
			String name = in[i].toString();
			try {
				Material.tilesets[i] = ImageIO.read(o.getClass().getResource(pre+name+tile));
				Material.tilesetsFringe[i] = ImageIO.read(o.getClass().getResource(pre+name+fringe));
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	public BufferedImage getSprite() {
		return Material.tilesets[this.getLayer()];
	}
	
	/**
	 * Returns the proper fringe for a material based on from which sides it 
	 * surrounds the tile.
	 * @param code
	 * @return
	 */
	public BufferedImage getFringe(int code) {
		//Starting at left, going clockwise. left, top-left, top, etc...
		int[] in = new int[8];
		for(int i = 0; i < 8; i++) {
			in[i] = (code & (1 << i)) >> i;
		}
		//The offset for the proper fringe from the fringe tilesheet.
		//Which are all made by hand in MSPaint.
		//God help me why did I decide to do that.
		int dx = in[3] + 2*in[1] + 4*in[0] + 8*in[7];
		int dy = in[4] + 2*in[6] + 4*in[2] + 8*in[5];
		
		BufferedImage out = Material.tilesetsFringe[this.getLayer()];
		out = out.getSubimage(33*dx, 33*dy, 32, 32);
		return out;
	}
}
