package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MapEditor extends Game{

	public MapEditor(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == 1) {
			int i = (arg0.getX() - this.getX()) / 32;
			int j = (arg0.getY() - this.getY()) / 32;
			if (i >= 0 && i < groundMap.length && j >= 0
					&& j < groundMap[0].length) {
				if (groundMap[i][j] == Material.VOID)
					groundMap[i][j] = Material.grass;
				else if (groundMap[i][j] == Material.grass)
					groundMap[i][j] = Material.VOID;
			}
			this.readyPaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < imageMap.length; i++) {
			for(int j = 0; j < imageMap[i].length; j++) {
				g.setColor(Color.white);
				g.drawRect(32*i, 32*j, 32, 32);
			}
		}
	}

}
