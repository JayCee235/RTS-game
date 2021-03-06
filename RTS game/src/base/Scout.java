package base;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Scout extends Unit{
	
	public Scout(Material[][] m, Entity[][] e, int x, int y) {
		this.load("Scout");
		this.moveSpeed = 2;
		this.maxHealth = 30;
		this.health = maxHealth;
		
		this.vision = 3;
		
		this.attackSpeed = 20;
		this.attackDamage = 2;
		
		this.x = x;
		this.y = y;
		
		this.walkable = new Material[] {Material.sand, Material.grass, Material.stone};
		
		this.game = e;
		this.ground = m;
	}

	@Override
	public void drawOffset(Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getImage() {
		return this.attacking
				?this.attackSprite[this.facing][this.animation/10]
				:this.sprite[this.facing][this.animation/10];
	}

	@Override
	public int getEntityCode() {
		return 101;
	}

}
