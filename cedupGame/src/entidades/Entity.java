package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import mundo.Camera;


public class Entity {

	private int maskx, masky,mwidth,mheight;
	
	public static BufferedImage ceu = Game.ceu.getSprite(0, 0, 96, 96);
	public static BufferedImage chao = Game.sprite.getSprite(0, 0, 16, 16);
	public static BufferedImage chaoGRAMA = Game.sprite.getSprite(16, 0, 16, 16);
	public static BufferedImage empty = Game.sprite.getSprite(16, 32, 16, 16);
	
	public static BufferedImage grama = Game.sprite.getSprite(0, 32, 16, 16);
	public static BufferedImage inimigo = Game.sprite.getSprite(112, 0, 16, 16);
	public static BufferedImage rifinha = Game.sprite.getSprite(80, 32, 16, 16);
	

	public static BufferedImage save = Game.sprite.getSprite(0, 16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}

	public static boolean isColidding(Entity e1,Entity e2) {
		Rectangle e1mask = new Rectangle(e1.getX() + e1.maskx,e1.getY() + e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2mask = new Rectangle(e2.getX() + e2.maskx,e2.getY() + e2.masky,e2.mwidth,e2.mheight);
		return e1mask.intersects(e2mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
	}
	
}
