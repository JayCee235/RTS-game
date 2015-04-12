package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedTransferQueue;

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
	
	protected int x, y;
	
	protected Material[][] ground;
	protected Entity[][] game;
	
	protected Pair goal, subGoal, sideGoal;
	
	protected Stack<Pair> path;
	
	public void draw(Graphics g, int x, int y) {
		draw(g);
	}
	
	public void draw(Graphics g) {
		BufferedImage[][] pull = attacking?attackSprite:sprite;
		while(animation >= 10 * sprite[0].length) {
			animation -= 10*sprite[0].length;
		}
		BufferedImage work = pull[facing][animation/10];
		g.drawImage(work, 32*this.x + px, 32*this.y + py, 32*(this.x+1) + px, 32*(this.y+1) + py, 
				0, 0, 32, 32, null);
		
	}
	
	public boolean isAt(Pair p) {
		return p == null || (this.x == p.getX() && this.y == p.getY());
	}
	
	public void tick() {
		if(this.path == null) {
			this.path = new Stack<Pair>();
		}
		if(!this.path.isEmpty()) {
			Pair g = this.path.peek();
			if(this.move(ground, game, g.getX() - this.x, g.getY() - this.y)) {
				this.path.pop();
			} else if(!this.moving) {
				this.findPath();
			}
			if(this.isAt(this.subGoal)) {
				this.subGoal = null;
			}
			
		} 
		{
			if(this.isAt(goal))
				this.goal = null;
			
			if(this.subGoal == null && this.goal != null) {
				this.findPath();
			}
		}
		
		
		
		
		if (moving) {
			this.animation++;
			this.px = this.px + (this.dx * this.moveSpeed);
			this.py = this.py + (this.dy * this.moveSpeed);
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
	
	public static boolean contains(Material[] walk, Material m) {
		for(int i = 0; i < walk.length; i++) {
			if(walk[i] == m)
				return true;
		}
		return false;
	}
	
	private class pathNode {
		int x, y;
		pathNode parent;
		
		public pathNode(int x, int y, pathNode parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
		
		public pathNode(Pair p, pathNode parent) {
			this.x = p.getX();
			this.y = p.getY();
			this.parent = parent;
		}
		
		public Pair toPair() {
			return new Pair(this.x, this.y);
		}
		
		public boolean equals(Pair p) {
			return this.x == p.getX() && this.y == p.getY();
		}
	}
	
	public boolean walkable(Material m) {
		return contains(this.walkable, m);
	}
	
	private boolean holds(ArrayList<pathNode> l, pathNode c) {
		for(pathNode d : l) {
			if(c.equals(d.toPair())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean inBounds(int in, int min, int max) {
		return in >= min && in <= max;
	}
	
	private boolean findPath() {
		if(this.goal != null) {
			int cx = Test.clamp(goal.getX(), x - vision, x + vision);
			int cy = Test.clamp(goal.getY(), y - vision, y + vision);
			cx = Test.clamp(cx, 0, game.length);
			cy = Test.clamp(cy, 0, game[0].length);
			
			if(this.path == null)
				this.path = new Stack<Pair>();
			
			if(true || this.walkable(ground[cx][cy])) {
				this.subGoal = new Pair(cx, cy);
				
				LinkedTransferQueue<pathNode> open = new LinkedTransferQueue<pathNode>();
				ArrayList<pathNode> closed = new ArrayList<pathNode>();
				
				pathNode start = new pathNode(this.x, this.y, null);
				open.add(start);
				
				boolean found = false;
				pathNode g = null;
				
				if(start.equals(this.subGoal)) {
					g = start;
					found = true;
				}
				
				while(!open.isEmpty() && !found) {
					pathNode work = open.poll();
					closed.add(work);
					for(Direction d : Direction.all()) {
						int dx = work.x + d.getX();
						int dy = work.y + d.getY();
						if(inBounds(dx, x - vision, x + vision) && inBounds(dy, y - vision, y + vision)
								&& inBounds(dx, 0, game.length) && inBounds(dy, 0, game[0].length)) {
							if(this.walkable(ground[dx][dy]) && game[dx][dy] == null) {
								pathNode n = new pathNode(dx, dy, work);
								if(!holds(closed, n)) {
									open.add(n);
									if(n.equals(this.subGoal)) {
										g = n;
										found = true;
										break;
									}
								}
							}
						}
						
					}
				}
				
				if(found) {
					this.path.clear();
					pathNode c = g;
					while(c != null) {
						path.push(c.toPair());
						c = c.parent;
					}
					if(!path.isEmpty()) {
						path.pop();
					}
				} else {
					this.vision--;
					this.findPath();
					this.vision++;
				}
				
				
				return true;
			}
		}
		return false;
	}
	
	public boolean moveTo(Material[][] board, Entity[][] map, int xx, int yy) {
		this.goal = new Pair(xx, yy);
		
		return this.findPath();
	}
	
	public boolean move(Material[][] board, Entity[][] map, int dx, int dy) {
		if(!moving) {
			this.dx = Test.clamp(dx, -1, 1);
			this.dy = Test.clamp(dy, -1, 1);
			this.facing = this.findDir(this.dx, this.dy);
			if (game[x + this.dx][y + this.dy] == null) {
				moving = true;
				this.px = -31 * this.dx;
				this.py = -31 * this.dy;
				game[x][y] = null;
				game[x + this.dx][y + this.dy] = this;
				this.x += this.dx;
				this.y += this.dy;
				return true;
			} else {
				
			}
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
	
	private int findDir(int dx, int dy) {
		int x = Test.clamp(dx, -1, 1);
		int y = Test.clamp(dy, -1, 1);
		
		if(y == -1) {
			return 2 + x;
		} else if(y == 1) {
			return 6 - x;
		} else {
			return 2 * (x + 1);
		}
	}
}
