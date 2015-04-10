package base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {
	int width, height;
	int[][] groundMap;
	int[][] gameMap;
	
	public Level(int width, int height, Material[][] groundMap,
			Entity[][] gameMap) {
		super();
		this.width = width;
		this.height = height;
		this.groundMap = new int[width][height];
		this.gameMap = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				this.groundMap[i][j] = groundMap[i][j].getLayer();
				if(gameMap[i][j]!=null) {
					this.gameMap[i][j] = gameMap[i][j].getEntityCode();
				}
			}
		}
	}
	
	public void save(String name) {
			try {
				FileWriter fw = new FileWriter(name + ".BASElevel", false);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write("." + width + "." + height + ".");
				for(int i = 0; i < width; i++) {
					for(int j = 0; j < height; j++) {
						bw.write(this.groundMap[i][j]);
						bw.write(this.gameMap[i][j]);
					}
				}
				bw.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
