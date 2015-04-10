package base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {
	int width, height;
	Material[][] groundMap;
	Entity[][] gameMap;
	
	public Level(int width, int height, Material[][] groundMap,
			Entity[][] gameMap) {
		super();
		this.width = width;
		this.height = height;
		this.gameMap = gameMap;
		this.groundMap = groundMap;
	}

}
