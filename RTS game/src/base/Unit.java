package base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class Unit extends Entity {
	protected Player owner;
	protected int health, maxHealth,  moveSpeed, attackSpeed, attackDamage, vision;
	
	Material[] walkable;
	
	protected static BufferedImage[][] sprite;
	protected static BufferedImage[][] attackSprite;
	private boolean loaded;
	protected boolean attacking, moving;
	protected int animation, facing, px, py, dx, dy;
	
	protected Dimension goal, subGoal, sideGoal;
	
	public void draw(Graphics g, int x, int y) {
		BufferedImage[][] pull = attacking?attackSprite:sprite;
		BufferedImage work = pull[facing][animation/10];
		g.drawImage(work, 32*x + px, 32*y + py, 32*(x+1) + px, 32*(y+1) + py, 
				0, 0, 32, 32, null);
		while(animation >= 10 * sprite[0].length)
			animation -= 10*sprite[0].length;
	}
	
	public void tick() {
		if (moving) {
			this.animation++;
			System.out.println(px + " " + py);
			this.px = this.px + (this.dx * this.moveSpeed);
			this.py = this.py + (this.dy * this.moveSpeed);
			System.out.println(px + " " + py);
			if (this.px * this.dx >= 0 ) {
				this.px = 0;
			}
			if (this.py * this.dy >= 0) {
				this.py = 0;
			}
			if (moving && px == 0 && py == 0) {
				this.moving = false;
				this.dx = 0;
				this.dy = 0;
				this.animation = 0;
			}
		}
	}
	
	public boolean move(Material[][] board, Entity[][] map, int dx, int dy) {
		if(!moving) {
			moving = true;
			this.dx = dx;
			this.dy = dy;
			this.px = -31 * dx;
			this.py = -31 * dy;
			return true;
		}
		return false;
	}
	
	public void load(String name) {
		if (!loaded) {
			String path = "res/entities/units/";
			BufferedImage work = null;
			try {
				URL f = this.getClass().getResource(path + name + "_sprite.png");
				work = ImageIO.read(f);
			} catch (IllegalArgumentException e) {
				try {
					work = ImageIO.read(this.getClass().getResource(
							path + "placeholder" + "_sprite.png"));
				} catch (IOException e1) {
					
				}
			} catch (IOException e) {
				
			}
			this.sprite = new BufferedImage[8][4];
			this.attackSprite = new BufferedImage[8][4];
			
			for (int i = 0; i < sprite.length; i++) {
				for (int j = 0; j < sprite[i].length; j++) {
					sprite[i][j] = work.getSubimage(32 * i, 32 * j, 32, 32);
					attackSprite[i][j] = work.getSubimage(32 * i, 32 * (j + 4),
							32, 32);

					//				String file = path + name + i + "_" + j;
					//				try {
					//					sprite[i][j] = ImageIO.read(this.getClass().getResource(file + ".png"));
					//					attackSprite[i][j] = ImageIO.read(this.getClass().getResource(file + "_attacking" + ".png"));
					//				} catch (IOException e) {
					//					
					//				}

				}
			}
			loaded = true;
		}
	}
}
