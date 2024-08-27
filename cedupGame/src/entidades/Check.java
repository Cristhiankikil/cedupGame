package entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;

public class Check extends Entity {

	public int maskx= 0, masky = 0, maskw = 16, maskh = 16;
	
	public Check(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void tick() {
		if(!colisao((int)x,(int)(y+1))) {
			y+=2;
		}
	}
	
	public boolean colisao(int nextx, int nexty) {
		Rectangle player = new Rectangle(nextx + maskx, nexty+masky,maskw,maskh);
		for(int i = 0; i < Game.entidades.size(); i++) {
			Entity entidade = Game.entidades.get(i);
			if(entidade instanceof Solido) {
				Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY()+masky,maskw,maskh);	
				if(player.intersects(solido)) {
					return true;
				}
			}
			
		}
		return false;
	}
	
}
