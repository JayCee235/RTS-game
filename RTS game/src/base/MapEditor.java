package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class MapEditor extends Game{
	private boolean gridMode;
	
	public MapEditor(int w, int h) {
		super(w, h);
		gridMode = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == 1) {
			int i = (arg0.getX() - (dx+odx)) / 32;
			int j = (arg0.getY() - (dy+ody)) / 32;
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
		g.translate(dx + odx, dy + ody);
		if (gridMode) {
			for (int i = 0; i < imageMap.length; i++) {
				for (int j = 0; j < imageMap[i].length; j++) {
					g.setColor(Color.white);
					g.drawRect(32 * i, 32 * j, 32, 32);
				}
			}
		}
		g.translate(-dx - odx, -dy - ody);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		super.mouseWheelMoved(arg0);
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		super.mouseDragged(arg0);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		super.mouseEntered(arg0);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		super.mouseExited(arg0);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		super.mousePressed(arg0);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		super.mouseReleased(arg0);
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		super.keyPressed(arg0);
		if(arg0.getKeyCode() == KeyEvent.VK_G) {
			gridMode = !gridMode;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		super.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		super.keyTyped(arg0);
	}
}
